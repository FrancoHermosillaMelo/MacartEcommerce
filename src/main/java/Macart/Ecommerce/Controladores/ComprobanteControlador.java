package Macart.Ecommerce.Controladores;

import Macart.Ecommerce.DTO.ComprobanteDTO;
import Macart.Ecommerce.DTO.PagarConTarjetaDTO;
import Macart.Ecommerce.Modelos.Cliente;
import Macart.Ecommerce.Modelos.Comprobante;
import Macart.Ecommerce.Modelos.Pedido;
import Macart.Ecommerce.Modelos.PedidoProducto;
import Macart.Ecommerce.Repositorio.ClienteRepositorio;
import Macart.Ecommerce.Repositorio.ComprobanteRepositorio;
import Macart.Ecommerce.Repositorio.PedidoRepositorio;
import Macart.Ecommerce.Servicios.ClienteServicio;
import Macart.Ecommerce.Servicios.ComprobanteServicio;
import Macart.Ecommerce.Servicios.Implementacion.EnviarCorreoImplementacion;
import Macart.Ecommerce.Servicios.PedidoServicio;
import Macart.Ecommerce.Servicios.ProductoTiendaServicio;
import Macart.Ecommerce.Utilidades.ComprobanteUtilidades;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@CrossOrigin(origins = {"*"})
@RestController
public class ComprobanteControlador {
    @Autowired
    private ComprobanteServicio comprobanteServicio;
    @Autowired
     private ClienteServicio clienteServicio;
    @Autowired
    private PedidoServicio pedidoServicio;
    @Autowired
    private EnviarCorreoImplementacion enviarCorreoImplementacion;
    @Autowired
    private ProductoTiendaServicio productoTiendaServicio;


    @GetMapping("/api/comprobantes") /* CHECK */
    public ResponseEntity<Object> obtenerComprobantes(){
        return (new ResponseEntity<>(comprobanteServicio.obtenerTodosLosComprobantes(), HttpStatus.ACCEPTED));
    }
    @GetMapping("/api/clientes/comprobantes") /* CHECK */
    public List<ComprobanteDTO> obtenerComprobantesCliente(@RequestParam long idCliente){
        return clienteServicio.obtenerClientePorId(idCliente).getComprobantes()
                .stream()
                .map(comprobante -> new ComprobanteDTO(comprobante))
                .collect(Collectors.toList());
    }
    @GetMapping("/api/clientes/comprobantes/fechas") /* CHECK */
    public ResponseEntity<Object> obtenerComprobantesClientePorFecha(@RequestParam String inicioFecha, @RequestParam String finFecha, @RequestParam long idCliente) throws ParseException {
        Cliente cliente = clienteServicio.obtenerClientePorId(idCliente);

        if(cliente == null){
            return new ResponseEntity<>("El cliente no existe", HttpStatus.NOT_FOUND);
        }
        if(inicioFecha.isBlank()){
            return new ResponseEntity<>("La fecha de inicio no puede estar en blanco", HttpStatus.FORBIDDEN);
        }
        if(finFecha.isBlank()){
            return new ResponseEntity<>("La fecha fin no puede estar en blanco",HttpStatus.FORBIDDEN);
        }

        Date inicioFechaDate = ComprobanteUtilidades.stringtoDate(inicioFecha);
        Date finFechaDate = ComprobanteUtilidades.stringtoDate(finFecha);

        LocalDateTime inicioFechaLocalDateTime = ComprobanteUtilidades.dateToLocalDateTime(inicioFechaDate);
        LocalDateTime finFechaLocalDateTime = ComprobanteUtilidades.dateToLocalDateTime(finFechaDate).plusDays(1).minusSeconds(1);

        List<ComprobanteDTO> comprobantesCliente = comprobanteServicio.obtenerComprobantesClienteFecha(cliente, inicioFechaLocalDateTime, finFechaLocalDateTime).stream().map(comprobante -> new ComprobanteDTO(comprobante)).collect(Collectors.toList());

        return new ResponseEntity<>(comprobantesCliente,HttpStatus.ACCEPTED);
    }

    @GetMapping("/api/comprobantes/fechas")
    public ResponseEntity<Object> obtenerComprobantesTodosPorFecha(@RequestParam String inicioFecha, @RequestParam String finFecha) throws ParseException {

        if (inicioFecha.isBlank()){
            return new ResponseEntity<>("La fecha de inicio no puede estar en blanco", HttpStatus.FORBIDDEN);
        }
        if(finFecha.isBlank()){
            return new ResponseEntity<>("La fecha de fin no puede estar en blanco", HttpStatus.FORBIDDEN);
        }

        Date inicioFechaDate = ComprobanteUtilidades.stringtoDate(inicioFecha);
        Date finFechaDate = ComprobanteUtilidades.stringtoDate(finFecha);

        LocalDateTime inicioFechaLocalDateTime = ComprobanteUtilidades.dateToLocalDateTime(inicioFechaDate);
        LocalDateTime finFechaLocalDateTime = ComprobanteUtilidades.dateToLocalDateTime(finFechaDate).plusDays(1).minusSeconds(1);

        List<ComprobanteDTO> comprobantesTodos = comprobanteServicio.obtenerComprobantesTodosFecha(inicioFechaLocalDateTime, finFechaLocalDateTime).stream().map(comprobante -> new ComprobanteDTO(comprobante)).collect(Collectors.toList());

        return new ResponseEntity<>(comprobantesTodos, HttpStatus.ACCEPTED);
    }

    @Transactional
    @PostMapping("/api/comprobantes/pdf") /* CHECK */
    public ResponseEntity<Object> crearComprobantePDF(@RequestBody PagarConTarjetaDTO pagarConTarjetaDTO, Authentication authentication) throws IOException, DocumentException, MessagingException {
        Cliente clienteDelPedido = clienteServicio.obtenerClienteAutenticado(authentication);
        Pedido pedidoSolicitado = pedidoServicio.ObtenerPedidoPorId(pagarConTarjetaDTO.getPedidoId());
        Set<PedidoProducto> pedidoProductos = pedidoSolicitado.getPedidoProductos();
        List<Pedido> pedidosPagados = clienteDelPedido.getPedidos().stream().filter(pedidoPagada -> !pedidoPagada.isPagado()).collect(toList());
        Pedido pedido = pedidosPagados.get(0);

        if (clienteDelPedido.getPedidos().stream().noneMatch(pedidos -> pedido.getId() == pedidoSolicitado.getId())) {
            return new ResponseEntity<>("Este pedido no le pertenece a su cuenta", HttpStatus.FORBIDDEN);
        }
        if (pedidoSolicitado.isPagado()) {
            return new ResponseEntity<>("No se puede pagar un pedido ya pago", HttpStatus.FORBIDDEN);
        }
        if (pedidoSolicitado.isEliminado()) {
            return new ResponseEntity<>("Este pedido está eliminado", HttpStatus.FORBIDDEN);
        }

        try {
            URL url = new URL("https://mindhubbankcowboy.up.railway.app/api/clients/current/cards/postnet");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String solicitudPost = "{\"type\": \"" + pagarConTarjetaDTO.getType() + "\", " +
                    "\"color\": \"" + pagarConTarjetaDTO.getColor() + "\", " +
                    "\"number\": \"" + pagarConTarjetaDTO.getNumber() + "\", " +
                    "\"cvv\": \"" + pagarConTarjetaDTO.getCvv() + "\", " +
                    "\"email\": \"" + pagarConTarjetaDTO.getEmail() + "\", " +
                    "\"amount\": \"" + pagarConTarjetaDTO.getAmount() + "\"}";

            connection.getOutputStream().write(solicitudPost.getBytes());
            int codigoDeRespuesta = connection.getResponseCode();
            if (codigoDeRespuesta == HttpURLConnection.HTTP_CREATED) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = reader.readLine();
                System.out.println("Respuesta del servidor: " + response);

                connection.getInputStream().close();
                connection.disconnect();

                pedidoSolicitado.setPagado(true);

                Comprobante comprobante = new Comprobante(LocalDateTime.now(), pedidoSolicitado.getMontoTotal(), pagarConTarjetaDTO.getType(), pagarConTarjetaDTO.getColor());
                comprobanteServicio.guardarComprobante(comprobante);
                clienteDelPedido.agregarComprobantes(comprobante);
                clienteServicio.guardarCliente(clienteDelPedido);


                for (PedidoProducto productosPagos : pedidoProductos) {
                    Map<String, Integer> tallasTienda = productosPagos.getProductoTienda().getTallas();
                    Map<String, Integer> tallas = productosPagos.getTallas();
                    for (Map.Entry<String, Integer> entry : tallas.entrySet()) {
                        String talla = entry.getKey();
                        int cantidadComprada = entry.getValue();
                        if (tallasTienda.containsKey(talla)) {
                            int stockDisponible = tallasTienda.get(talla);
                            int nuevoStock = stockDisponible - cantidadComprada;
                            tallasTienda.put(talla, nuevoStock);
                        }
                    }
                    productoTiendaServicio.guardarProducto(productosPagos.getProductoTienda());
                }


                // Generar el PDF del comprobante
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                Document document = new Document();
                PdfWriter.getInstance(document, outputStream);
                document.open();


                Image logo = Image.getInstance("https://res.cloudinary.com/dtis6pqyq/image/upload/v1685839439/Black_Logo_bowo2z.png");
                logo.scaleToFit(120, 120);
                document.add(logo);

                Font fontTitle = new Font(Font.FontFamily.HELVETICA, 25, Font.BOLD);

                String nombreCompleto = clienteDelPedido.getPrimerNombre() + " " +
                        (clienteDelPedido.getSegundoNombre() != null ? clienteDelPedido.getSegundoNombre() + " " : "") +
                        clienteDelPedido.getPrimerApellido() + " " +
                        (clienteDelPedido.getSegundoApellido() != null ? clienteDelPedido.getSegundoApellido() : "");
                Paragraph title = new Paragraph("Señor/a: " + nombreCompleto);
                title.setAlignment(Paragraph.ALIGN_CENTER);
                title.setSpacingBefore(20);
                title.setSpacingAfter(40);
                document.add(title);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String fechaActual = LocalDateTime.now().format(formatter);

                Paragraph mensaje = new Paragraph("\n" + "¡Aquí tienes tus productos seleccionados!   " + fechaActual);
                mensaje.setAlignment(Element.ALIGN_CENTER);
                mensaje.setSpacingAfter(20f);
                document.add(mensaje);

                Paragraph espacio = new Paragraph(" ");
                espacio.setSpacingAfter(40f);
                document.add(espacio);

                PdfPTable tablaPedidos = new PdfPTable(3);
                tablaPedidos.setWidthPercentage(100);
                tablaPedidos.setHorizontalAlignment(Element.ALIGN_CENTER);
                tablaPedidos.getDefaultCell().setBorder(Rectangle.NO_BORDER);

                Font fontNegrita = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

                PdfPCell celdaProducto = new PdfPCell(new Phrase("Producto", fontNegrita));
                celdaProducto.setBorder(Rectangle.BOX);
                celdaProducto.setHorizontalAlignment(Element.ALIGN_CENTER);
                celdaProducto.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celdaProducto.setFixedHeight(60);
                tablaPedidos.addCell(celdaProducto);

                PdfPCell celdaCantidad = new PdfPCell(new Phrase("Cantidad", fontNegrita));
                celdaCantidad.setBorder(Rectangle.BOX);
                celdaCantidad.setHorizontalAlignment(Element.ALIGN_CENTER);
                celdaCantidad.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celdaCantidad.setFixedHeight(60);
                tablaPedidos.addCell(celdaCantidad);

                PdfPCell celdaSubtotal = new PdfPCell(new Phrase("PrecioTotal", fontNegrita));
                celdaSubtotal.setBorder(Rectangle.BOX);
                celdaSubtotal.setHorizontalAlignment(Element.ALIGN_CENTER);
                celdaSubtotal.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celdaSubtotal.setFixedHeight(60);
                tablaPedidos.addCell(celdaSubtotal);

                tablaPedidos.setHeaderRows(1);

                for (PedidoProducto pedidoProducto : pedido.getPedidoProductos()) {
                    PdfPCell cellProducto = new PdfPCell(new Paragraph(pedidoProducto.getProductoTienda().getNombre()));
                    cellProducto.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellProducto.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cellProducto.setFixedHeight(50);
                    cellProducto.setBorder(Rectangle.BOX);
                    tablaPedidos.addCell(cellProducto);

                    Map<String, Integer> tallas = pedidoProducto.getTallas();
                    PdfPCell cellCantidad = new PdfPCell();
                    cellCantidad.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellCantidad.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cellCantidad.setFixedHeight(50);
                    cellCantidad.setBorder(Rectangle.BOX); // Cambiado de Rectangle.NO_BORDER a Rectangle.BOX

                    Paragraph paragraph = new Paragraph();
                    int totalCantidad = 0;
                    float totalPrecio = 0;

                    for (Map.Entry<String, Integer> entry : tallas.entrySet()) {
                        String talla = entry.getKey();
                        int cantidad = entry.getValue();

                        paragraph.add(new Chunk(talla + " x " + cantidad + " | "));
                        totalCantidad += cantidad;
                        totalPrecio += pedidoProducto.getProductoTienda().getPrecio() * cantidad;
                    }

                    paragraph.add(new Chunk("Total: " + totalCantidad));

                    cellCantidad.addElement(paragraph);
                    tablaPedidos.addCell(cellCantidad);

                    PdfPCell cellSubtotal = new PdfPCell();
                    cellSubtotal.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellSubtotal.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cellSubtotal.setFixedHeight(50);
                    cellSubtotal.setBorder(Rectangle.BOX);

                    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                    symbols.setGroupingSeparator(',');
                    DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", symbols);
                    String formattedSubtotal = "$ " + decimalFormat.format(totalPrecio);

                    cellSubtotal.addElement(new Paragraph(formattedSubtotal));
                    cellSubtotal.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellSubtotal.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    tablaPedidos.addCell(cellSubtotal);
                }

                document.add(tablaPedidos);


                Paragraph pago = new Paragraph("Monto total  pagado:  $" + pedido.getMontoTotal() + ".");
                pago.setAlignment(Element.ALIGN_CENTER);
                pago.setSpacingAfter(20f);
                document.add(pago);


                Paragraph espacio2 = new Paragraph(" ");
                espacio2.setSpacingAfter(40f);
                document.add(espacio2);

                Paragraph envio = new Paragraph("Queremos informarte que estamos procesando tu pedido con entusiasmo. Estimamos que tu pedido llegará a tu domicilio en un plazo de 5 días hábiles.");
                envio.setAlignment(Element.ALIGN_CENTER);
                envio.setSpacingAfter(20f);
                document.add(envio);

                Paragraph agradecimiento = new Paragraph("¡Gracias por tu compra! Valoramos tu apoyo y confianza.");
                agradecimiento.setAlignment(Element.ALIGN_CENTER);
                agradecimiento.setSpacingAfter(20f);
                document.add(agradecimiento);

                document.close();


                String subject = "Comprobante de compra";
                String body = "Adjunto encontrarás el comprobante de tu compra.";

                String clienteEmail = clienteDelPedido.getCorreo();
                String adminEmail = "carlosandresgoo@gmail.com"; // Reemplaza con el correo del administrador

                enviarCorreoImplementacion.enviarCorreoConPDF(clienteEmail, subject, body, outputStream.toByteArray());
                enviarCorreoImplementacion.enviarCorreoConPDF(adminEmail, subject, body, outputStream.toByteArray());


                return new ResponseEntity<>("Pago aceptado", HttpStatus.CREATED);


            } else {
                connection.getInputStream().close();
                connection.disconnect();

                return new ResponseEntity<>("Pago rechazado", HttpStatus.FORBIDDEN);
            }

        } catch (Exception err) {
            err.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al realizar el pago");
        }
    }
}




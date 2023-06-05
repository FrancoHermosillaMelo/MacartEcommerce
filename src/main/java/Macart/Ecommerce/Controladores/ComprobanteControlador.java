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
import java.text.ParseException;
import java.time.LocalDateTime;
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

        if(clienteDelPedido.getPedidos().stream().noneMatch(pedido -> pedido.getId() == pedidoSolicitado.getId())){
            return new ResponseEntity<>("Este pedido no le pertenece a su cuenta", HttpStatus.FORBIDDEN);
        }
        if(pedidoSolicitado.isPagado()){
            return new ResponseEntity<>("No se puede pagar un pedido ya pago", HttpStatus.FORBIDDEN);
        }
        if(pedidoSolicitado.isEliminado()){
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

                Comprobante comprobante = new Comprobante(LocalDateTime.now(),pedidoSolicitado.getMontoTotal(),pagarConTarjetaDTO.getType(),pagarConTarjetaDTO.getColor());
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
                    Paragraph title = new Paragraph("Señor/a: " + clienteDelPedido.getPrimerNombre() + " " +
                            clienteDelPedido.getSegundoNombre() + " " +
                            clienteDelPedido.getPrimerApellido() + " " +
                            clienteDelPedido.getSegundoApellido());
                    title.setAlignment(Paragraph.ALIGN_CENTER);
                    title.setSpacingBefore(20);
                    title.setSpacingAfter(20);
                    document.add(title);
                    document.close();

                    // Enviar el PDF por correo electrónico al cliente y al administrador
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




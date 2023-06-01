package Macart.Ecommerce.Controladores;

import Macart.Ecommerce.DTO.ComprobanteDTO;
import Macart.Ecommerce.Modelos.Cliente;
import Macart.Ecommerce.Modelos.Comprobante;
import Macart.Ecommerce.Modelos.Pedido;
import Macart.Ecommerce.Repositorio.ClienteRepositorio;
import Macart.Ecommerce.Repositorio.ComprobanteRepositorio;
import Macart.Ecommerce.Repositorio.PedidoRepositorio;
import Macart.Ecommerce.Servicios.ClienteServicio;
import Macart.Ecommerce.Servicios.ComprobanteServicio;
import Macart.Ecommerce.Servicios.Implementacion.EnviarCorreoImplementacion;
import Macart.Ecommerce.Servicios.PedidoServicio;
import Macart.Ecommerce.Utilidades.ComprobanteUtilidades;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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


    @GetMapping("/api/comprobantes")
    public ResponseEntity<Object> obtenerComprobantes(){
        return (new ResponseEntity<>(comprobanteServicio.obtenerComprobantes(), HttpStatus.ACCEPTED));
    }
    @GetMapping("/api/clientes/comprobantes")
    public List<ComprobanteDTO> obtenerComprobantesCliente(@RequestParam long idCliente){
        return clienteServicio.obtenerClientePorId(idCliente).getComprobantes()
                .stream()
                .map(comprobante -> new ComprobanteDTO(comprobante))
                .collect(Collectors.toList());
    }
    @GetMapping("/api/clientes/comprobantes/fechas")
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
    @PostMapping("/api/comprobantes/pdf")
    public ResponseEntity<Object> crearComprobantePDF(@RequestParam long idComprobante, @RequestParam long idPedido) throws IOException, DocumentException, MessagingException {
        Comprobante comprobanteSolicitado = comprobanteServicio.obtenerComprobantesId(idComprobante);
        Pedido pedidoSolicitado = pedidoServicio.ObtenerPedidoPorId(idPedido);

        if (comprobanteSolicitado == null) {
            return new ResponseEntity<>("El comprobante no existe", HttpStatus.NOT_FOUND);
        }
        if (pedidoSolicitado == null) {
            return new ResponseEntity<>("El pedido no existe", HttpStatus.NOT_FOUND);
        }
        Cliente clienteDelPedido = pedidoSolicitado.getCliente();

        // Generar el PDF del comprobante
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();


        Image logo = Image.getInstance("C:\\Tareas mind hub\\Tercera parte del mind hub\\MacartEcommerce\\src\\main\\resources\\static\\img\\Black_Logo.png");
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

        return new ResponseEntity<>("Pdf Creado correctamente y enviado por correo electrónico", HttpStatus.CREATED);
    }





}

package Macart.Ecommerce.Servicios.Implementacion;

import Macart.Ecommerce.Modelos.Cliente;
import Macart.Ecommerce.Repositorio.ComprobanteRepositorio;
import Macart.Ecommerce.Servicios.ComprobanteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Service
public class EnviarCorreoImplementacion {
    @Autowired
    private ComprobanteRepositorio comprobanteRepositorio;
    @Autowired
    private ComprobanteServicio comprobanteServicio;
    @Autowired
    private JavaMailSender emailSender;

    public void enviarCorreoConPDF(String toEmail, String subject, String body, byte[] pdfBytes) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("carlosandresgoo@gmail.com");
        helper.setTo(toEmail);
        helper.setText(body);
        helper.setSubject(subject);

        // Adjuntar el PDF al correo electrónico
        ByteArrayResource pdfAttachment = new ByteArrayResource(pdfBytes);
        helper.addAttachment("Comprobante.pdf", pdfAttachment);

        emailSender.send(message);
        System.out.println("Correo enviado con el PDF adjunto");
    }


    public void enviarCorreoAutenticacion(String destinatario, String nombreCliente, String token) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(destinatario);
        helper.setSubject("Autenticación de cuenta");
        String mensaje = "Estimado " + nombreCliente + ",\n\n";
        mensaje += "Por favor  copia este código y pegalo en la página para autenticar tu cuenta , gracias :):\n";
        mensaje += "token=" + token + "\n\n";
        // Reemplaza [Enlace de autenticación] con el enlace real que permita al cliente autenticar su cuenta.

        helper.setText(mensaje);
        emailSender.send(message);
    }


}

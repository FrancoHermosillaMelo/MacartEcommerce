package Macart.Ecommerce.Servicios.Implementacion;

import Macart.Ecommerce.Repositorio.ComprobanteRepositorio;
import Macart.Ecommerce.Servicios.ComprobanteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

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



    public void enviarCorreo(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("carlosandresgoo@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        emailSender.send(message);
        System.out.println("Correo enviado");
    }

}

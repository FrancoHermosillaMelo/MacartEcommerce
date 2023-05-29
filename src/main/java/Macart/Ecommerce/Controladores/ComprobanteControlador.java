package Macart.Ecommerce.Controladores;

import Macart.Ecommerce.DTO.ComprobanteDTO;
import Macart.Ecommerce.Modelos.Cliente;
import Macart.Ecommerce.Modelos.Comprobante;
import Macart.Ecommerce.Repositorio.ClienteRepositorio;
import Macart.Ecommerce.Repositorio.ComprobanteRepositorio;
import Macart.Ecommerce.Utilidades.ComprobanteUtilidades;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ComprobanteControlador {
    @Autowired
    private ComprobanteRepositorio comprobanteRepositorio;
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @GetMapping("/api/comprobantes")
    public List<ComprobanteDTO> obtenerComprobantes(){
        return comprobanteRepositorio.findAll()
                .stream()
                .map(comprobante -> new ComprobanteDTO(comprobante))
                .collect(Collectors.toList());
    }
    @GetMapping("/api/clientes/comprobantes")
    public List<ComprobanteDTO> obtenerComprobantesCliente(@RequestParam long idCliente){
        return clienteRepositorio.findById(idCliente).getComprobantes()
                .stream()
                .map(comprobante -> new ComprobanteDTO(comprobante))
                .collect(Collectors.toList());
    }
    @GetMapping("/api/clientes/comprobantes/fechas")
    public ResponseEntity<Object> obtenerComprobantesClientePorFecha(@RequestParam String inicioFecha, @RequestParam String finFecha, @RequestParam long idCliente) throws ParseException {
        Cliente cliente = clienteRepositorio.findById(idCliente);

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

        List<ComprobanteDTO> comprobantesCliente = comprobanteRepositorio.findByComprobantesClienteFecha(cliente, inicioFechaLocalDateTime, finFechaLocalDateTime).stream().map(comprobante -> new ComprobanteDTO(comprobante)).collect(Collectors.toList());

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

        List<ComprobanteDTO> comprobantesTodos = comprobanteRepositorio.findByComprobantesTodosFecha(inicioFechaLocalDateTime, finFechaLocalDateTime).stream().map(comprobante -> new ComprobanteDTO(comprobante)).collect(Collectors.toList());

        return new ResponseEntity<>(comprobantesTodos, HttpStatus.ACCEPTED);
    }
    @PostMapping("/api/comprobantes/pdf")
    public ResponseEntity<Object> crearComprobantePDF (@RequestParam long idComprobante, @RequestParam String inicioFecha, @RequestParam String finFecha) throws ParseException {
        Comprobante comprobanteSolicitado = comprobanteRepositorio.findById(idComprobante).orElse(null);

        if(comprobanteSolicitado == null){
            return new ResponseEntity<>("El comprobante no existe", HttpStatus.NOT_FOUND);
        }
        if(inicioFecha.isBlank()){
            return new ResponseEntity<>("La fecha de inicio no puede estar en blanco", HttpStatus.FORBIDDEN);
        }
        if(finFecha.isBlank()){
            return new ResponseEntity<>("La fecha fin no puede estar en blanco", HttpStatus.FORBIDDEN);
        }

        Date inicioFechaDate = ComprobanteUtilidades.stringtoDate(inicioFecha);
        Date finFechaDate = ComprobanteUtilidades.stringtoDate(finFecha);

        LocalDateTime inicioFechaLocalDateTime = ComprobanteUtilidades.dateToLocalDateTime(inicioFechaDate);
        LocalDateTime finFechaLocalDateTime = ComprobanteUtilidades.dateToLocalDateTime(finFechaDate).plusDays(1).minusSeconds(1);


        return null;
    }

}

package Macart.Ecommerce.Servicios;

import Macart.Ecommerce.DTO.ComprobanteDTO;
import Macart.Ecommerce.Modelos.Cliente;
import Macart.Ecommerce.Modelos.Comprobante;

import java.time.LocalDateTime;
import java.util.List;

public interface ComprobanteServicio {
    List<ComprobanteDTO> obtenerComprobantes();
    List<Comprobante> findByComprobantesClienteFecha(Cliente cliente, LocalDateTime inicioFecha, LocalDateTime finFecha);
    List<Comprobante> findByComprobantesTodosFecha(LocalDateTime inicioFecha, LocalDateTime finFecha);
    Comprobante findByComprobantesId (long id);
}

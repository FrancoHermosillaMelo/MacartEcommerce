package Macart.Ecommerce.Servicios;

import Macart.Ecommerce.DTO.ComprobanteDTO;
import Macart.Ecommerce.Modelos.Cliente;
import Macart.Ecommerce.Modelos.Comprobante;

import java.time.LocalDateTime;
import java.util.List;

public interface ComprobanteServicio {
    List<ComprobanteDTO> obtenerTodosLosComprobantes();
    List<Comprobante> obtenerComprobantesClienteFecha(Cliente cliente, LocalDateTime inicioFecha, LocalDateTime finFecha);
    List<Comprobante> obtenerComprobantesTodosFecha(LocalDateTime inicioFecha, LocalDateTime finFecha);
    Comprobante obtenerComprobantesId (long id);
}

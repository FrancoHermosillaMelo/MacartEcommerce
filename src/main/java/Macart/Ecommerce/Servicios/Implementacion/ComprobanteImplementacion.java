package Macart.Ecommerce.Servicios.Implementacion;

import Macart.Ecommerce.DTO.ComprobanteDTO;
import Macart.Ecommerce.Modelos.Cliente;
import Macart.Ecommerce.Modelos.Comprobante;
import Macart.Ecommerce.Repositorio.ComprobanteRepositorio;
import Macart.Ecommerce.Servicios.ClienteServicio;
import Macart.Ecommerce.Servicios.ComprobanteServicio;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComprobanteImplementacion implements ComprobanteServicio {
    private ClienteServicio clienteServicio;
    private ComprobanteRepositorio comprobanteRepositorio;

    @Override
    public List<ComprobanteDTO> obtenerComprobantes() {
        return comprobanteRepositorio.findAll()
                .stream()
                .map(comprobante -> new ComprobanteDTO(comprobante))
                .collect(Collectors.toList());
    }

    @Override
    public List<Comprobante> findByComprobantesClienteFecha(Cliente cliente, LocalDateTime inicioFecha, LocalDateTime finFecha) {
        return comprobanteRepositorio.findByComprobantesClienteFecha(cliente,  inicioFecha,  finFecha);
    }

    @Override
    public List<Comprobante> findByComprobantesTodosFecha(LocalDateTime inicioFecha, LocalDateTime finFecha) {
        return comprobanteRepositorio.findByComprobantesTodosFecha(inicioFecha,  finFecha);
    }

    @Override
    public Comprobante findByComprobantesId(long id) {return comprobanteRepositorio.ObtenerComprobantePorId(id);}
}

package Macart.Ecommerce.Servicios.Implementacion;

import Macart.Ecommerce.DTO.PedidoProductoDTO;
import Macart.Ecommerce.Modelos.Cliente;
import Macart.Ecommerce.Modelos.PedidoProducto;
import Macart.Ecommerce.Repositorio.PedidoProductoRepositorio;
import Macart.Ecommerce.Servicios.PedidoProductoServicio;
import Macart.Ecommerce.Servicios.PedidoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class PedidoProductoImplementacion implements PedidoProductoServicio {
    @Autowired
    private PedidoServicio pedidoServicio;
    @Autowired
    private PedidoProductoRepositorio pedidoProductoRepositorio;

    @Override
    public List<PedidoProductoDTO> obtenerPedidoProducto() {
        return pedidoProductoRepositorio.findAll()
                .stream().map(pedidoProducto ->
                        new PedidoProductoDTO(pedidoProducto)).collect(toList());
    }

    @Override
    public boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities()
                .stream()
                .anyMatch(role -> role.getAuthority().equals("ADMIN"));
    }

    @Override
    public PedidoProducto ObtenerPedidoProductoPorId(long id) { return pedidoProductoRepositorio.ObtenerPedidoProductoPorId(id);}

}

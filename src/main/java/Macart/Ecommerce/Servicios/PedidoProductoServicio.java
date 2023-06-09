package Macart.Ecommerce.Servicios;

import Macart.Ecommerce.DTO.PedidoProductoDTO;
import Macart.Ecommerce.Modelos.PedidoProducto;
import Macart.Ecommerce.Modelos.ProductoTienda;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface PedidoProductoServicio {
    List<PedidoProductoDTO> obtenerPedidoProducto();
    boolean isAdmin(Authentication authentication);
    PedidoProducto ObtenerPedidoProductoPorId(long id);
    void guardarPedidoProducto(PedidoProducto pedidoProducto);
}

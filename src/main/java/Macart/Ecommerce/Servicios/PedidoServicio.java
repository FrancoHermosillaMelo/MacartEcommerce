package Macart.Ecommerce.Servicios;

import Macart.Ecommerce.DTO.PedidoDTO;
import Macart.Ecommerce.Modelos.Cliente;
import Macart.Ecommerce.Modelos.Pedido;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface PedidoServicio {
    List<PedidoDTO> obtenerPedidos();
    boolean isAdmin(Authentication authentication);
    void guardarPedido(Pedido pedido);
    List<Pedido> findByCliente(Cliente cliente);
}

package Macart.Ecommerce.Servicios;

import Macart.Ecommerce.DTO.PedidoDTO;
import Macart.Ecommerce.Modelos.Cliente;
import Macart.Ecommerce.Modelos.Pedido;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Set;

public interface PedidoServicio {
    List<PedidoDTO> obtenerPedidos();
    boolean isAdmin(Authentication authentication);
    void guardarPedido(Pedido pedido);
    List<Pedido> findByCliente(Cliente cliente);
    Pedido ObtenerPedidoPorId (long id);
    Set<Pedido> obtenerPedidosActivadosCliente(Authentication authentication);
}

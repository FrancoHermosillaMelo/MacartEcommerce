package Macart.Ecommerce.Servicios.Implementacion;

import Macart.Ecommerce.DTO.PedidoDTO;
import Macart.Ecommerce.Modelos.Cliente;
import Macart.Ecommerce.Modelos.Pedido;
import Macart.Ecommerce.Repositorio.PedidoRepositorio;
import Macart.Ecommerce.Servicios.ClienteServicio;
import Macart.Ecommerce.Servicios.PedidoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class PedidoImplementacion implements PedidoServicio {
    @Autowired
    private PedidoRepositorio pedidoRepositorio;
    @Autowired
    private ClienteServicio clienteServicio;


    @Override
    public List<PedidoDTO> obtenerPedidos() {
        return pedidoRepositorio.findAll()
                .stream().map(pedido ->
                        new PedidoDTO(pedido)).collect(toList());
    }

    @Override
    public boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities()
                .stream()
                .anyMatch(role -> role.getAuthority().equals("ADMIN"));
    }

    @Override
    public void guardarPedido(Pedido pedido) {pedidoRepositorio.save(pedido);
    }

    @Override
    public List<Pedido> findByCliente(Cliente cliente) {return pedidoRepositorio.findByCliente(cliente);}
}

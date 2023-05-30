package Macart.Ecommerce.Repositorio;

import Macart.Ecommerce.Modelos.Cliente;
import Macart.Ecommerce.Modelos.Pedido;
import Macart.Ecommerce.Modelos.PedidoProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface PedidoRepositorio extends JpaRepository<Pedido, Long> {

    List<Pedido> findByCliente(Cliente cliente);
    Pedido ObtenerPedidoPorId(long id);
}

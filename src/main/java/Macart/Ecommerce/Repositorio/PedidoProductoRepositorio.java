package Macart.Ecommerce.Repositorio;

import Macart.Ecommerce.Modelos.PedidoProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PedidoProductoRepositorio extends JpaRepository<PedidoProducto, Long> {
    PedidoProducto ObtenerPedidoProductoPorId(long id);

}

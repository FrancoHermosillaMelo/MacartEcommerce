package Macart.Ecommerce.Repositorio;

import Macart.Ecommerce.Modelos.PedidoProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface Pedido_ProductoRepositorio extends JpaRepository<PedidoProducto, Long> {

}

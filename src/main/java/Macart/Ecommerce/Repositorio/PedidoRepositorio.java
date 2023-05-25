package Macart.Ecommerce.Repositorio;

import Macart.Ecommerce.Modelos.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PedidoRepositorio extends JpaRepository<Pedido, Long> {

}

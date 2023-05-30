package Macart.Ecommerce.Repositorio;

import Macart.Ecommerce.Modelos.Cliente;
import Macart.Ecommerce.Modelos.ProductoTienda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProductoTiendaRepositorio extends JpaRepository<ProductoTienda, Long> {
    ProductoTienda findByNombre(String nombre);
}

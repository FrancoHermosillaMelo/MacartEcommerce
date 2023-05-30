package Macart.Ecommerce.Repositorio;

import Macart.Ecommerce.Modelos.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface DireccionRepositorio extends JpaRepository<Direccion, Long> {
    Direccion findById(long id);
}

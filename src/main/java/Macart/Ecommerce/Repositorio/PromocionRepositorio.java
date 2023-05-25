package Macart.Ecommerce.Repositorio;

import Macart.Ecommerce.Modelos.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PromocionRepositorio extends JpaRepository<Promocion, Long> {
}

package Macart.Ecommerce.Repositorio;

import Macart.Ecommerce.Modelos.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ClienteRepositorio extends JpaRepository <Cliente, Long> {

}

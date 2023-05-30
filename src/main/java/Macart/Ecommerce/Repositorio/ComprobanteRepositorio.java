package Macart.Ecommerce.Repositorio;

import Macart.Ecommerce.Modelos.Cliente;
import Macart.Ecommerce.Modelos.Comprobante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDateTime;
import java.util.List;

@RepositoryRestResource
public interface ComprobanteRepositorio extends JpaRepository<Comprobante, Long> {
    @Query("SELECT c FROM Comprobante c WHERE c.cliente =:cliente AND c.fechaDeComprobante >=:inicioFecha AND c.fechaDeComprobante <=:finFecha")
    List<Comprobante> findByComprobantesClienteFecha(Cliente cliente, LocalDateTime inicioFecha, LocalDateTime finFecha);

    @Query("SELECT c FROM Comprobante c WHERE c.fechaDeComprobante >=:inicioFecha AND c.fechaDeComprobante <=:finFecha")
    List<Comprobante> findByComprobantesTodosFecha(LocalDateTime inicioFecha, LocalDateTime finFecha);
}

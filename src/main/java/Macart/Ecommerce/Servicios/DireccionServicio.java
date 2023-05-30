package Macart.Ecommerce.Servicios;

import Macart.Ecommerce.DTO.DireccionDTO;
import Macart.Ecommerce.Modelos.Cliente;
import Macart.Ecommerce.Modelos.Direccion;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface DireccionServicio {
    List<DireccionDTO> obtenerDireccionesClientes(Authentication authentication);
    boolean isAdmin(Authentication authentication);
    void guardarDireccion(Direccion direccion);
    Direccion obtenerDireccionPorId(long id);
}

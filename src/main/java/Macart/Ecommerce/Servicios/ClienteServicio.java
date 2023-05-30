package Macart.Ecommerce.Servicios;

import Macart.Ecommerce.DTO.ClienteDTO;
import Macart.Ecommerce.Modelos.Cliente;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ClienteServicio {
    List<ClienteDTO> obtenerTodosLosClientes(Authentication authentication);
    Cliente obtenerClientePorId(long id);
    Cliente obtenerClientePorEmail(String email);
    Cliente obtenerClienteAutenticado(Authentication authentication);
    boolean isAdmin(Authentication authentication);

    void guardarCliente(Cliente cliente);
}

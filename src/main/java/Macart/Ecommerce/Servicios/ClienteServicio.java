package Macart.Ecommerce.Servicios;

import Macart.Ecommerce.DTO.ClienteDTO;
import Macart.Ecommerce.Modelos.Cliente;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public interface ClienteServicio {
    List<ClienteDTO> obtenerTodosLosClientes();
    Cliente obtenerClientePorId(long id);
    Cliente obtenerClientePorEmail(String email);
    Cliente obtenerClienteAutenticado(Authentication authentication);
    boolean isAdmin(Authentication authentication);
    GrantedAuthority obtenerRolCliente(Authentication authentication);
    void guardarCliente(Cliente cliente);
}

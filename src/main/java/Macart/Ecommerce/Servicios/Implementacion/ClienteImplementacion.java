package Macart.Ecommerce.Servicios.Implementacion;

import Macart.Ecommerce.DTO.ClienteDTO;
import Macart.Ecommerce.Modelos.Cliente;
import Macart.Ecommerce.Repositorio.ClienteRepositorio;
import Macart.Ecommerce.Servicios.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class ClienteImplementacion implements ClienteServicio {
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Override
    public List<ClienteDTO> obtenerTodosLosClientes() {
        return clienteRepositorio.findAll()
                .stream()
                .map(cliente -> new ClienteDTO(cliente))
                .collect(Collectors.toList());
    }

    @Override
    public Cliente obtenerClientePorId(long id) {
        return clienteRepositorio.findById(id);
    }

    @Override
    public Cliente obtenerClientePorEmail(String email) {
        return clienteRepositorio.findByCorreo(email);
    }

    @Override
    public Cliente obtenerClienteAutenticado(Authentication authentication) {
        return clienteRepositorio.findByCorreo(authentication.getName());
    }

    @Override
    public boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities()
                .stream()
                .anyMatch(role -> role.getAuthority().equals("ADMIN"));
    }

    @Override
    public GrantedAuthority obtenerRolCliente(Authentication authentication) {
        return authentication.getAuthorities().stream().collect(toList()).get(0);
    }

    @Override
    public void guardarCliente(Cliente cliente) {
        clienteRepositorio.save(cliente);
    }
}

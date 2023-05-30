package Macart.Ecommerce.Servicios.Implementacion;

import Macart.Ecommerce.DTO.DireccionDTO;
import Macart.Ecommerce.Modelos.Direccion;
import Macart.Ecommerce.Repositorio.ClienteRepositorio;
import Macart.Ecommerce.Repositorio.DireccionRepositorio;
import Macart.Ecommerce.Servicios.ClienteServicio;
import Macart.Ecommerce.Servicios.DireccionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class DireccionImplementacion implements DireccionServicio {
    @Autowired
    private DireccionRepositorio direccionRepositorio;
    @Autowired
    private ClienteServicio clienteServicio;

    @Override
    public List<DireccionDTO> obtenerDireccionesClientes(Authentication authentication) {
        return direccionRepositorio.findAll()
                .stream().map(direccion ->
                        new DireccionDTO(direccion)).collect(toList());
    }

    @Override
    public boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities()
                .stream()
                .anyMatch(role -> role.getAuthority().equals("ADMIN"));
    }

    @Override
    public void guardarDireccion(Direccion direccion) {direccionRepositorio.save(direccion);}

    @Override
    public Direccion obtenerDireccionPorId(long id) {return direccionRepositorio.findById(id);}


}

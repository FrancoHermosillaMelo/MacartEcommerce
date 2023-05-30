package Macart.Ecommerce.Controladores;

import Macart.Ecommerce.DTO.ClienteDTO;
import Macart.Ecommerce.Modelos.Cliente;
import Macart.Ecommerce.Repositorio.ClienteRepositorio;
import Macart.Ecommerce.Servicios.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class ClienteControlador {
    @Autowired
    private ClienteServicio clienteServicio;


    @GetMapping("/api/clientes")
    public ResponseEntity<Object> obtenerClientes(Authentication authentication){
        if(clienteServicio.isAdmin(authentication)){
            return new ResponseEntity<>(clienteServicio.obtenerTodosLosClientes(),HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("No tiene los permisos para solicitar estos datos", HttpStatus.FORBIDDEN);
    }
   @GetMapping("/api/clientes/actual")
    public  ResponseEntity<Object> obtenerClienteActual(Authentication authentication){
        Cliente clienteAutenticado = clienteServicio.obtenerClientePorEmail(authentication.getName());
        return new ResponseEntity<>(new ClienteDTO(clienteAutenticado), HttpStatus.ACCEPTED);
   }
    @PostMapping("/api/clientes")
    public ResponseEntity<Object> registrarCliente(
            @RequestParam String primerNombre,
            @RequestParam(required = false) String segundoNombre,
            @RequestParam String primerApellido,
            @RequestParam(required = false) String segundoApellido,
            @RequestParam String correo,
            @RequestParam String telefono,
            @RequestParam String contraseña) {

        if (clienteServicio.obtenerClientePorEmail(correo) !=  null) {
            return new ResponseEntity<>("El correo electrónico ya está en uso", HttpStatus.FORBIDDEN);
        }
        Cliente nuevoClient = new Cliente(primerNombre, segundoNombre, primerApellido, segundoApellido,correo,telefono,contraseña);
        clienteServicio.guardarCliente(nuevoClient);

        return new ResponseEntity<>("Se ha registrado exitosamente",HttpStatus.CREATED);

    }
}

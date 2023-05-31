package Macart.Ecommerce.Controladores;

import Macart.Ecommerce.DTO.ClienteDTO;
import Macart.Ecommerce.Modelos.Cliente;
import Macart.Ecommerce.Servicios.ClienteServicio;
import Macart.Ecommerce.Utilidades.DireccionUtilidades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

@RestController
public class ClienteControlador {
    @Autowired
    private ClienteServicio clienteServicio;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/api/clientes")
    public ResponseEntity<Object> obtenerClientes(){
        return new ResponseEntity<>(clienteServicio.obtenerTodosLosClientes(),HttpStatus.ACCEPTED);
    }
    @GetMapping("/api/clientes/actual/rol")
    public String getClientRol(Authentication authentication){
        if(authentication != null){
            return clienteServicio.obtenerRolCliente(authentication).toString();
        }
        return "VISITANTE";
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

        if (primerNombre.isBlank()) {
            return new ResponseEntity<>("El primer nombre no puede estar en blanco.", HttpStatus.FORBIDDEN);
        }

        if (primerApellido.isBlank()) {
            return new ResponseEntity<>("El primer apellido no puede estar en blanco.", HttpStatus.FORBIDDEN);
        }

        if (!Pattern.matches("^[a-zA-Z]+$", primerNombre)) {
            return new ResponseEntity<>("El primer nombre solo puede contener letras.", HttpStatus.FORBIDDEN);
        }

        if (!Pattern.matches("^[a-zA-Z]+$", primerApellido)) {
            return new ResponseEntity<>("El primer apellido solo puede contener letras.", HttpStatus.FORBIDDEN);
        }
        if (!segundoNombre.isEmpty() && !Pattern.matches("^[a-zA-Z]+$", segundoNombre)) {
            return new ResponseEntity<>("El segundo nombre solo puede contener letras.", HttpStatus.FORBIDDEN);
        }

        if (!segundoApellido.isEmpty() && !Pattern.matches("^[a-zA-Z]+$", segundoApellido)) {
            return new ResponseEntity<>("El segundo apellido solo puede contener letras.", HttpStatus.FORBIDDEN);
        }

        if (correo.isBlank()) {
            return new ResponseEntity<>("El correo no puede estar en blanco.", HttpStatus.FORBIDDEN);
        } else if (!correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return new ResponseEntity<>("Ingrese una dirección de correo electrónico válida.", HttpStatus.FORBIDDEN);
        }

        if (telefono.isBlank()) {
            return new ResponseEntity<>("El teléfono no puede estar en blanco.", HttpStatus.FORBIDDEN);
        } else if (!telefono.matches("\\d+")) {
            return new ResponseEntity<>("El teléfono debe contener solo números.", HttpStatus.FORBIDDEN);
        }

        if (contraseña.isBlank()) {
            return new ResponseEntity<>("La contraseña no puede estar en blanco.", HttpStatus.FORBIDDEN);
        } else if (!DireccionUtilidades.esContraseñaValida(contraseña)) {
            return new ResponseEntity<>("La contraseña debe tener al menos 8 caracteres, incluyendo al menos un número y una letra mayúscula.", HttpStatus.FORBIDDEN);
        }

        if (clienteServicio.obtenerClientePorEmail(correo) !=  null) {
            return new ResponseEntity<>("El correo electrónico ya está en uso.", HttpStatus.FORBIDDEN);
        }
        Cliente nuevoClient = new Cliente(primerNombre, segundoNombre, primerApellido, segundoApellido,correo,telefono, passwordEncoder.encode(contraseña));
        clienteServicio.guardarCliente(nuevoClient);

        return new ResponseEntity<>("Se ha registrado exitosamente.",HttpStatus.CREATED);

    }



}

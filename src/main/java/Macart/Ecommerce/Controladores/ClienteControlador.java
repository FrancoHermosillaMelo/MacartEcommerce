package Macart.Ecommerce.Controladores;

import Macart.Ecommerce.DTO.ClienteDTO;
import Macart.Ecommerce.Modelos.Cliente;
import Macart.Ecommerce.Servicios.ClienteServicio;
import Macart.Ecommerce.Servicios.Implementacion.EnviarCorreoImplementacion;
import Macart.Ecommerce.Utilidades.DireccionUtilidades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

@RestController
public class ClienteControlador {
    @Autowired
    private ClienteServicio clienteServicio;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EnviarCorreoImplementacion enviarCorreoImplementacion;

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
    @GetMapping("/api/clientes/id")
    public ResponseEntity<Object> obtenerClientePorId(Authentication authentication, @RequestParam long id){
        Cliente clientePedido = clienteServicio.obtenerClientePorId(id);
        Cliente clienteAutenticado = clienteServicio.obtenerClienteAutenticado(authentication);
        if(clientePedido == null){
            return new ResponseEntity<>("El cliente no existe", HttpStatus.FORBIDDEN);
        }
        if(!clienteServicio.isAdmin(authentication)){
            if(clienteAutenticado.getCorreo().equalsIgnoreCase(clientePedido.getCorreo())){
                return new ResponseEntity<>(new ClienteDTO(clientePedido), HttpStatus.ACCEPTED);
            }else{
                return new ResponseEntity<>("No tiene permisos para ver informacion de otros clientes", HttpStatus.FORBIDDEN);
            }
        }else{
            return new ResponseEntity<>(new ClienteDTO(clientePedido), HttpStatus.ACCEPTED);
        }
    }

    @PostMapping("/api/clientes")
    public ResponseEntity<Object> registrarCliente(
            @RequestParam String primerNombre,
            @RequestParam(required = false) String segundoNombre,
            @RequestParam String primerApellido,
            @RequestParam(required = false) String segundoApellido,
            @RequestParam String correo,
            @RequestParam String telefono,
            @RequestParam String contraseña) throws MessagingException {

        // Validar primer nombre
        if (primerNombre.isBlank()) {
            return new ResponseEntity<>("El primer nombre no puede estar en blanco.", HttpStatus.FORBIDDEN);
        }
        if (!Pattern.matches("^[a-zA-Z]+$", primerNombre)) {
            return new ResponseEntity<>("El primer nombre solo puede contener letras.", HttpStatus.FORBIDDEN);
        }

        // Validar primer apellido
        if (primerApellido.isBlank()) {
            return new ResponseEntity<>("El primer apellido no puede estar en blanco.", HttpStatus.FORBIDDEN);
        }
        if (!Pattern.matches("^[a-zA-Z]+$", primerApellido)) {
            return new ResponseEntity<>("El primer apellido solo puede contener letras.", HttpStatus.FORBIDDEN);
        }

        // Validar segundo nombre
        if (!segundoNombre.isBlank() && !Pattern.matches("^[a-zA-Z]+$", segundoNombre)) {
            return new ResponseEntity<>("El segundo nombre solo puede contener letras.", HttpStatus.FORBIDDEN);
        }

        // Validar segundo apellido
        if (!segundoApellido.isBlank() && !Pattern.matches("^[a-zA-Z]+$", segundoApellido)) {
            return new ResponseEntity<>("El segundo apellido solo puede contener letras.", HttpStatus.FORBIDDEN);
        }

        // Validar correo
        if (correo.isBlank()) {
            return new ResponseEntity<>("El correo no puede estar en blanco.", HttpStatus.FORBIDDEN);
        } else if (!correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return new ResponseEntity<>("Ingrese una dirección de correo electrónico válida.", HttpStatus.FORBIDDEN);
        }

        // Validar teléfono
        if (telefono.isBlank()) {
            return new ResponseEntity<>("El teléfono no puede estar en blanco.", HttpStatus.FORBIDDEN);
        } else if (!telefono.matches("\\d+")) {
            return new ResponseEntity<>("El teléfono debe contener solo números.", HttpStatus.FORBIDDEN);
        }

        // Validar contraseña
        if (contraseña.isBlank()) {
            return new ResponseEntity<>("La contraseña no puede estar en blanco.", HttpStatus.FORBIDDEN);
        } else if (!DireccionUtilidades.esContraseñaValida(contraseña)) {
            return new ResponseEntity<>("La contraseña debe tener al menos 8 caracteres, incluyendo al menos un número y una letra mayúscula.", HttpStatus.FORBIDDEN);
        }

        // Verificar si el correo ya está en uso
        if (clienteServicio.obtenerClientePorEmail(correo) != null) {
            return new ResponseEntity<>("El correo electrónico ya está en uso.", HttpStatus.FORBIDDEN);
        }

        // Generar un nuevo token de autenticación
        String tokenAutenticacion = generarTokenAutenticacion();

        // Crear un nuevo cliente
        Cliente nuevoCliente = new Cliente(primerNombre, segundoNombre, primerApellido, segundoApellido, correo, telefono, passwordEncoder.encode(contraseña), false, tokenAutenticacion);
        clienteServicio.guardarCliente(nuevoCliente);

        // Enviar el correo de autenticación
        enviarCorreoImplementacion.enviarCorreoAutenticacion(correo, nuevoCliente.getPrimerNombre(), tokenAutenticacion);

        return new ResponseEntity<>("Se ha registrado exitosamente. Se ha enviado un correo de verificación.", HttpStatus.CREATED);
    }

    @PostMapping("/api/clientes/autenticar")
    public ResponseEntity<Object> autenticarCliente(@RequestParam String token) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Cliente cliente = clienteServicio.obtenerClientePorEmail(authentication.getName());

        if (cliente == null || !cliente.getTokenAutenticacion().equals(token)) {
            return new ResponseEntity<>("La autenticación no es válida.", HttpStatus.FORBIDDEN);
        }

        cliente.setVerificado(true);
        clienteServicio.guardarCliente(cliente);

        return new ResponseEntity<>("Se ha verificado la cuenta exitosamente.", HttpStatus.OK);
    }

    @PutMapping("/api/clientes/{id}")
    public ResponseEntity<Object> editarCliente(
            @PathVariable("id") Long id,
            @RequestParam String contraseña,
            @RequestParam String telefono) {
        Cliente cliente = clienteServicio.obtenerClientePorId(id);

        if (cliente == null) {
            return new ResponseEntity<>("No se encontró el cliente.", HttpStatus.NOT_FOUND);
        }

        if (contraseña != null && !contraseña.isEmpty()) {
            if (!DireccionUtilidades.esContraseñaValida(contraseña)) {
                return new ResponseEntity<>("La contraseña debe tener al menos 8 caracteres, incluyendo al menos un número y una letra mayúscula.", HttpStatus.FORBIDDEN);
            }
            cliente.setContraseña(passwordEncoder.encode(contraseña));
        }

        if (telefono != null && !telefono.isEmpty()) {
            if (!telefono.matches("\\d+")) {
                return new ResponseEntity<>("El teléfono debe contener solo números.", HttpStatus.FORBIDDEN);
            }
            cliente.setTelefono(telefono);
        }

        clienteServicio.guardarCliente(cliente);

        return new ResponseEntity<>("Los datos del cliente se han actualizado exitosamente.", HttpStatus.OK);
    }

    private String generarTokenAutenticacion() {
        String token = UUID.randomUUID().toString().substring(0, 6);
        return token.toUpperCase();
    }

}

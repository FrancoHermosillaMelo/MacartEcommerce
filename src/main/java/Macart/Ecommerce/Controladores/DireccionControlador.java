package Macart.Ecommerce.Controladores;


import Macart.Ecommerce.DTO.DireccionDTO;
import Macart.Ecommerce.Modelos.Cliente;
import Macart.Ecommerce.Modelos.Direccion;
import Macart.Ecommerce.Servicios.ClienteServicio;
import Macart.Ecommerce.Servicios.DireccionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;


@RestController
public class DireccionControlador {
    @Autowired
    private ClienteServicio clienteServicio;
    @Autowired
    private DireccionServicio direccionServicio;



    @GetMapping("/api/clientes/direcciones") /* CHECK */
    public ResponseEntity<Object> obtenerDireccionesClientes() {
            return (new ResponseEntity<>(direccionServicio.obtenerDireccionesClientes(), HttpStatus.ACCEPTED));
    }
    @GetMapping("/api/clientes/{id}/direccion") /* CHECK */
    public ResponseEntity<DireccionDTO> obtenerDireccionClienteId(@PathVariable Long id, Authentication authentication) {
        String authenticatedUsername = authentication.getName();
        Cliente cliente = clienteServicio.obtenerClientePorEmail(authenticatedUsername);

        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        Direccion direccion = direccionServicio.obtenerDireccionPorId(id);

        if (direccion == null) {
            return ResponseEntity.notFound().build();
        }
        if (direccion.getCliente().getId() != cliente.getId()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        DireccionDTO direccionDTO = new DireccionDTO(direccion);
        return ResponseEntity.ok(direccionDTO);
    }


    @PostMapping("/api/direcciones") /* CHECK */
    public ResponseEntity<Object> crearDirecciones(Authentication authentication,
            @RequestParam Long clienteId,
            @RequestParam String calle,
            @RequestParam String numeroDomicilio,
            @RequestParam String barrio,
            @RequestParam String ciudad,
            @RequestParam String departamento,
            @RequestParam String codigoPostal) {

        Cliente cliente = clienteServicio.obtenerClientePorId(clienteId);

        if (cliente == null) {
            return new ResponseEntity<>("No puedes agregar una nueva dirección ya que no eres cliente.", HttpStatus.NOT_FOUND);
        }

        if (calle.isBlank()) {
            return new ResponseEntity<>("La calle no puede estar en blanco.", HttpStatus.FORBIDDEN);
        }
        if (numeroDomicilio.isBlank()) {
            return new ResponseEntity<>("El número de domicilio no puede estar en blanco.", HttpStatus.FORBIDDEN);
        }
        if (!Pattern.matches("^[0-9\\p{Punct}]+$", numeroDomicilio)) {
            return new ResponseEntity<>("El número de domicilio solo puede contener números y caracteres especiales.", HttpStatus.FORBIDDEN);
        }
        if (barrio.isBlank()) {
            return new ResponseEntity<>("El barrio no puede estar en blanco.", HttpStatus.FORBIDDEN);
        }
        if (departamento.isBlank()) {
            return new ResponseEntity<>("El departamento no puede estar en blanco.", HttpStatus.FORBIDDEN);
        }
        if (codigoPostal.isBlank()) {
            return new ResponseEntity<>("El código postal no puede estar en blanco.", HttpStatus.FORBIDDEN);
        }
        if (!Pattern.matches("^[0-9]+$", codigoPostal)) {
            return new ResponseEntity<>("El código postal solo puede contener números.", HttpStatus.FORBIDDEN);
        }


        Direccion nuevaDireccion = new Direccion(calle, numeroDomicilio, barrio, ciudad, departamento, codigoPostal);
        cliente.agregarDirecciones(nuevaDireccion);
        direccionServicio.guardarDireccion(nuevaDireccion);


        return ResponseEntity.status(HttpStatus.CREATED).body("Dirección guardada correctamente");
    }

    @PutMapping("/api/direcciones") /* CHECK */
    public ResponseEntity<Object> modificarDirecciones(Authentication authentication,
            @RequestParam Long id,
            @RequestParam String calle,
            @RequestParam String numeroDomicilio,
            @RequestParam String barrio,
            @RequestParam String ciudad,
            @RequestParam String departamento,
            @RequestParam String codigoPostal) {

        Direccion direccion = direccionServicio.obtenerDireccionPorId(id);
        Cliente cliente = clienteServicio.obtenerClientePorEmail(authentication.getName());

        if (cliente == null) {
            return new ResponseEntity<>("No puedes agregar una nueva dirección ya que no eres cliente.", HttpStatus.NOT_FOUND);
        }
        if (calle.isEmpty()) {
            return new ResponseEntity<>("La calle no puede estar en blanco.", HttpStatus.FORBIDDEN);
        }
        if (numeroDomicilio.isEmpty()) {
            return new ResponseEntity<>("El número de domicilio no puede estar en blanco.", HttpStatus.FORBIDDEN);
        }
        if (!Pattern.matches("^[0-9\\p{Punct}]+$", numeroDomicilio)) {
            return new ResponseEntity<>("El número de domicilio solo puede contener números y caracteres especiales.", HttpStatus.FORBIDDEN);
        }
        if (barrio.isEmpty()) {
            return new ResponseEntity<>("El barrio no puede estar en blanco.", HttpStatus.FORBIDDEN);
        }
        if (departamento.isEmpty()) {
            return new ResponseEntity<>("El departamento no puede estar en blanco.", HttpStatus.FORBIDDEN);
        }
        if (codigoPostal.isEmpty()) {
            return new ResponseEntity<>("El código postal no puede estar en blanco.", HttpStatus.FORBIDDEN);
        }
        if (!Pattern.matches("^[0-9]+$", codigoPostal)) {
            return new ResponseEntity<>("El código postal solo puede contener números.", HttpStatus.FORBIDDEN);
        }

        if (direccion != null) {
            direccion.setCalle(calle);
            direccion.setNumeroDomicilio(numeroDomicilio);
            direccion.setBarrio(barrio);
            direccion.setCiudad(ciudad);
            direccion.setDepartamento(departamento);
            direccion.setCodigoPostal(codigoPostal);

            direccionServicio.guardarDireccion(direccion);


            return ResponseEntity.status(HttpStatus.CREATED).body("Direccion modificada correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La direccion con ID" + id + "no fue encontrada");
        }
    }
    @DeleteMapping("/api/direcciones/{id}") /* CHECK */
    public ResponseEntity<String> deleteDireccion(@PathVariable Long id, Authentication authentication) {
        String authenticatedUsername = authentication.getName();
        Cliente cliente = clienteServicio.obtenerClientePorEmail(authenticatedUsername);

        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No se ha autenticado correctamente");
        }

        Direccion direccion = direccionServicio.obtenerDireccionPorId(id);

        if (direccion == null) {
            return ResponseEntity.notFound().build();
        }

        if (direccion.getCliente().getId() != cliente.getId()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tiene permiso para eliminar esta dirección");
        }

        direccionServicio.borrarDireccion(direccion);
        return ResponseEntity.ok("La dirección ha sido eliminada exitosamente");
    }


}


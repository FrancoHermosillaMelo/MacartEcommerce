package Macart.Ecommerce.Controladores;


import Macart.Ecommerce.Modelos.Cliente;
import Macart.Ecommerce.Modelos.Direccion;
import Macart.Ecommerce.Servicios.ClienteServicio;
import Macart.Ecommerce.Servicios.DireccionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;



@RestController
public class DireccionControlador {
    @Autowired
    private ClienteServicio clienteServicio;
    @Autowired
    private DireccionServicio direccionServicio;



    @GetMapping("/api/clientes/direcciones")
    public ResponseEntity<Object> obtenerDireccionesClientes(Authentication authentication) {
        if(direccionServicio.isAdmin(authentication)){
            return (new ResponseEntity<>(direccionServicio.obtenerDireccionesClientes(authentication), HttpStatus.ACCEPTED));
        }
        return new ResponseEntity<>("No tiene los permisos para solicitar estos datos", HttpStatus.FORBIDDEN);
    }


    @PostMapping("/api/direcciones")
    public ResponseEntity<Object> crearDirecciones(
            @RequestParam Long clienteId,
            @RequestParam String calle,
            @RequestParam String numeroDomicilio,
            @RequestParam String barrio,
            @RequestParam String ciudad,
            @RequestParam String departamento,
            @RequestParam String codigoPostal) {

        Cliente cliente = clienteServicio.obtenerClientePorId(clienteId);

        Direccion nuevaDireccion = new Direccion(calle, numeroDomicilio, barrio, ciudad, departamento, codigoPostal);
        cliente.agregarDirecciones(nuevaDireccion);
        direccionServicio.guardarDireccion(nuevaDireccion);


        return ResponseEntity.status(HttpStatus.CREATED).body("Direccion creada");
    }

    @PutMapping("/api/direcciones")
    public ResponseEntity<Object> modificarDirecciones(
            @RequestParam Long id,
            @RequestParam String calle,
            @RequestParam String numeroDomicilio,
            @RequestParam String barrio,
            @RequestParam String ciudad,
            @RequestParam String departamento,
            @RequestParam String codigoPostal) {

        Direccion direccion = direccionServicio.obtenerDireccionPorId(id);

        if (direccion != null) {
            direccion.setCalle(calle);
            direccion.setNumeroDomicilio(numeroDomicilio);
            direccion.setBarrio(barrio);
            direccion.setCiudad(ciudad);
            direccion.setDepartamento(departamento);
            direccion.setCodigoPostal(codigoPostal);

            direccionServicio.guardarDireccion(direccion);


            return ResponseEntity.status(HttpStatus.CREATED).body("Direccion modificada");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La direccion con ID" + id + "no fue encontrada");
        }
    }
}


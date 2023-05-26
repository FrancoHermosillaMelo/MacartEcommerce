package Macart.Ecommerce.Controladores;

import Macart.Ecommerce.DTO.ClienteDTO;
import Macart.Ecommerce.DTO.DireccionDTO;
import Macart.Ecommerce.Modelos.Cliente;
import Macart.Ecommerce.Modelos.Direccion;
import Macart.Ecommerce.Repositorio.ClienteRepositorio;
import Macart.Ecommerce.Repositorio.DireccionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class DireccionControlador {
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    @Autowired
    private DireccionRepositorio direccionRepositorio;

    @GetMapping("/api/clientes/direcciones")
    public List<DireccionDTO> obtenerDireccionesClientes() {
        return direccionRepositorio.findAll()
                .stream().map(direccion ->
                        new DireccionDTO(direccion)).collect(toList());
    }


    @PostMapping("/api/direcciones")
    public ResponseEntity<Object> crearDirecciones(@RequestParam Long clienteId, @RequestParam String calle
            , @RequestParam String numeroDomicilio, @RequestParam String barrio, @RequestParam String ciudad
            , @RequestParam String departamento, @RequestParam String codigoPostal) {
        Cliente cliente = clienteRepositorio.findById(clienteId).orElse(null);

        Direccion nuevaDireccion = new Direccion(calle, numeroDomicilio, barrio, ciudad, departamento, codigoPostal);
        cliente.agregarDirecciones(nuevaDireccion);
        direccionRepositorio.save(nuevaDireccion);


        return ResponseEntity.status(HttpStatus.CREATED).body("Direccion creada");
    }

    @PutMapping("/api/direcciones")
    public ResponseEntity<Object> modificarDirecciones(@RequestParam Long id, @RequestParam String calle
            , @RequestParam String numeroDomicilio, @RequestParam String barrio, @RequestParam String ciudad
            , @RequestParam String departamento, @RequestParam String codigoPostal) {

        Direccion direccion = direccionRepositorio.findById(id).orElse(null);

        if (direccion != null) {
            direccion.setCalle(calle);
            direccion.setNumeroDomicilio(numeroDomicilio);
            direccion.setBarrio(barrio);
            direccion.setCiudad(ciudad);
            direccion.setDepartamento(departamento);
            direccion.setCodigoPostal(codigoPostal);

            direccionRepositorio.save(direccion);


            return ResponseEntity.status(HttpStatus.CREATED).body("Direccion modificada");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La direccion con ID" + id + "no fue encontrada");
        }
    }
}


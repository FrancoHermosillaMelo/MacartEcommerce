package Macart.Ecommerce.Controladores;

import Macart.Ecommerce.DTO.ClienteDTO;
import Macart.Ecommerce.Modelos.Cliente;
import Macart.Ecommerce.Repositorio.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class ClienteControlador {
    @Autowired
    private ClienteRepositorio clienteRepositorio;


    @GetMapping("/api/clientes")
    public List<ClienteDTO> obtenerClientes(){
        return clienteRepositorio.findAll()
                .stream().map(client ->
                        new ClienteDTO(client)).collect(toList());  }
   @GetMapping("/api/clientes/actual")
    public  ClienteDTO obtenerCliente(@RequestParam long id){
       Cliente cliente = clienteRepositorio.findById(id);
       return new ClienteDTO(cliente);
   }
    @PostMapping("/api/clientes")
    public ResponseEntity<Object> registrarCliente(
            @RequestParam String primerNombre,
            @RequestParam String segundoNombre,
            @RequestParam String primerApellido,
            @RequestParam String segundoApellido,
            @RequestParam String correo,
            @RequestParam String telefono,
            @RequestParam String celular,
            @RequestParam String contraseña) {

        if (clienteRepositorio.findByEmail(correo) !=  null) {
            return new ResponseEntity<>("El coreo electrónico ya está en uso", HttpStatus.FORBIDDEN);
        }
        Cliente nuevoClient = new Cliente(primerNombre, segundoNombre, primerApellido, segundoApellido,correo,telefono,celular,contraseña);
        clienteRepositorio.save(nuevoClient);

        return ResponseEntity.status(HttpStatus.CREATED).body("Cuenta creada con exito");
    }
}

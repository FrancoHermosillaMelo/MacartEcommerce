package Macart.Ecommerce.Controladores;

import Macart.Ecommerce.DTO.ClienteDTO;
import Macart.Ecommerce.Modelos.Cliente;
import Macart.Ecommerce.Repositorio.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ClienteDTO obtenerCliente(@RequestParam long id){
       Cliente cliente = clienteRepositorio.findById(id);
       return new ClienteDTO(cliente);
   }



}

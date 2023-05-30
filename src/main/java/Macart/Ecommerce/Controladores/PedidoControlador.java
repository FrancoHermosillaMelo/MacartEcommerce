package Macart.Ecommerce.Controladores;

import Macart.Ecommerce.DTO.ClienteDTO;
import Macart.Ecommerce.DTO.PedidoDTO;
import Macart.Ecommerce.Modelos.Cliente;
import Macart.Ecommerce.Modelos.Direccion;
import Macart.Ecommerce.Modelos.Pedido;
import Macart.Ecommerce.Modelos.PedidoMetodoDePago;
import Macart.Ecommerce.Repositorio.ClienteRepositorio;
import Macart.Ecommerce.Repositorio.PedidoRepositorio;
import Macart.Ecommerce.Servicios.ClienteServicio;
import Macart.Ecommerce.Servicios.PedidoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
public class PedidoControlador {
    @Autowired
    private ClienteServicio clienteServicio;
    @Autowired
    private PedidoServicio pedidoServicio;


    @GetMapping("/api/pedidos")
    public ResponseEntity<Object> obtenerPedidos(Authentication authentication) {
        if(pedidoServicio.isAdmin(authentication)){
            return (new ResponseEntity<>(pedidoServicio.obtenerPedidos(authentication), HttpStatus.ACCEPTED));
        }
        return new ResponseEntity<>("No tiene los permisos para solicitar estos datos", HttpStatus.FORBIDDEN);
    }


    @GetMapping("/api/clientes/{id}/pedidos")
    public List<PedidoDTO> obtenerPedidoCliente(Authentication authentication,@PathVariable long id) {
    Cliente cliente = clienteServicio.obtenerClientePorId(id);

    List<Pedido> pedidos = pedidoServicio.findByCliente(cliente);

    List<PedidoDTO> pedidosDTO = pedidos.stream()
            .map(pedido -> new PedidoDTO(pedido))
            .collect(Collectors.toList());

    return  pedidosDTO;
    }

    @PostMapping("/api/pedidos")
    public ResponseEntity<Object> crearPedidos(Authentication authentication,
            @RequestParam Long clienteId,
            @RequestParam LocalDateTime fechaDePedido,
            @RequestParam double montoTotal,
            @RequestParam String metodoDeEnvio,
            @RequestParam String metodoDePago,
            @RequestParam String codigoPostal) {

        Cliente cliente = clienteServicio.obtenerClientePorId(clienteId);

        Pedido nuevoPedido = new Pedido(LocalDateTime.now(),false , montoTotal,metodoDeEnvio,PedidoMetodoDePago.valueOf(metodoDePago));
        cliente.agregarPedido(nuevoPedido);
        pedidoServicio.guardarPedido(nuevoPedido);


        return ResponseEntity.status(HttpStatus.CREATED).body("Direccion creada");
    }
}

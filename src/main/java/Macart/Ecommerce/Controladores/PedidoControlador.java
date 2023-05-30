package Macart.Ecommerce.Controladores;

import Macart.Ecommerce.DTO.ClienteDTO;
import Macart.Ecommerce.DTO.PedidoDTO;
import Macart.Ecommerce.Modelos.Cliente;
import Macart.Ecommerce.Modelos.Direccion;
import Macart.Ecommerce.Modelos.Pedido;
import Macart.Ecommerce.Modelos.PedidoMetodoDePago;
import Macart.Ecommerce.Repositorio.ClienteRepositorio;
import Macart.Ecommerce.Repositorio.PedidoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
public class PedidoControlador {
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    @Autowired
    private PedidoRepositorio pedidoRepositorio;

    @GetMapping("/api/pedidos")
    public List<PedidoDTO> obtenerPedidos() {
        return pedidoRepositorio.findAll()
                .stream().map(pedido ->
                        new PedidoDTO(pedido)).collect(toList());
    }


    @GetMapping("/api/clientes/{id}/pedidos")
    public List<PedidoDTO> obtenerPedidoCliente(@PathVariable long id) {
    Cliente cliente = clienteRepositorio.findById(id);

    List<Pedido> pedidos = pedidoRepositorio.findByCliente(cliente);

    List<PedidoDTO> pedidosDTO = pedidos.stream()
            .map(pedido -> new PedidoDTO(pedido))
            .collect(Collectors.toList());

    return  pedidosDTO;
    }

    @PostMapping("/api/pedidos")
    public ResponseEntity<Object> crearPedidos(
            @RequestParam Long clienteId,
            @RequestParam LocalDateTime fechaDePedido,
            @RequestParam double montoTotal,
            @RequestParam String metodoDeEnvio,
            @RequestParam String metodoDePago,
            @RequestParam String codigoPostal) {

        Cliente cliente = clienteRepositorio.findById(clienteId).orElse(null);

        Pedido nuevoPedido = new Pedido(LocalDateTime.now(),false , montoTotal,metodoDeEnvio,PedidoMetodoDePago.valueOf(metodoDePago));
        cliente.agregarPedido(nuevoPedido);
        pedidoRepositorio.save(nuevoPedido);


        return ResponseEntity.status(HttpStatus.CREATED).body("Direccion creada");
    }
}

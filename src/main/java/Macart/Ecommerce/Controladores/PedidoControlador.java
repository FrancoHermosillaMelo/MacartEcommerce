package Macart.Ecommerce.Controladores;

import Macart.Ecommerce.DTO.ClienteDTO;
import Macart.Ecommerce.DTO.PedidoDTO;
import Macart.Ecommerce.Modelos.Cliente;
import Macart.Ecommerce.Modelos.Pedido;
import Macart.Ecommerce.Repositorio.ClienteRepositorio;
import Macart.Ecommerce.Repositorio.PedidoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}

package Macart.Ecommerce.Controladores;

import Macart.Ecommerce.DTO.PedidoDTO;
import Macart.Ecommerce.DTO.PedidoProductoDTO;
import Macart.Ecommerce.Modelos.PedidoProducto;
import Macart.Ecommerce.Repositorio.ClienteRepositorio;
import Macart.Ecommerce.Repositorio.PedidoProductoRepositorio;
import Macart.Ecommerce.Repositorio.PedidoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;
@RestController
public class PedidoProductorControlador {
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    @Autowired
    private PedidoRepositorio pedidoRepositorio;
    @Autowired
    private PedidoProductoRepositorio pedidoProductoRepositorio;

    @GetMapping("/api/pedidoProducto")
    public List<PedidoProductoDTO> obtenerPedidoProducto() {
        return pedidoProductoRepositorio.findAll()
                .stream().map(pedidoProducto ->
                        new PedidoProductoDTO(pedidoProducto)).collect(toList());
    }
    @GetMapping("/api/pedidoProducto/{id}")
    public ResponseEntity<PedidoProductoDTO> obtenerPedidoProductoPorId(@PathVariable Long id) {
        Optional<PedidoProducto> pedidoProductoOptional = pedidoProductoRepositorio.findById(id);

        if (pedidoProductoOptional.isPresent()) {
            PedidoProducto pedidoProducto = pedidoProductoOptional.get();
            PedidoProductoDTO pedidoProductoDTO = new PedidoProductoDTO(pedidoProducto);
            return ResponseEntity.ok(pedidoProductoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}

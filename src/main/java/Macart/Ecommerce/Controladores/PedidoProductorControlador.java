package Macart.Ecommerce.Controladores;


import Macart.Ecommerce.DTO.PedidoProductoDTO;
import Macart.Ecommerce.DTO.ProductoTiendaDTO;
import Macart.Ecommerce.Modelos.PedidoProducto;
import Macart.Ecommerce.Servicios.PedidoProductoServicio;
import Macart.Ecommerce.Servicios.PedidoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PedidoProductorControlador {
    @Autowired
    private PedidoServicio pedidoServicio;
    @Autowired
    private PedidoProductoServicio pedidoProductoServicio;


    @GetMapping("/api/pedidoProducto")
    public ResponseEntity<Object> obtenerPedidoProducto() {
        return (new ResponseEntity<>(pedidoProductoServicio.obtenerPedidoProducto(), HttpStatus.ACCEPTED));
    }
    @GetMapping("/api/pedidoProducto/{id}")
    public ResponseEntity<PedidoProductoDTO> obtenerPedidoProductoPorId(Authentication authentication,@PathVariable Long id) {
        PedidoProducto pedidoProducto = pedidoProductoServicio.ObtenerPedidoProductoPorId(id);

        if (pedidoProducto != null) {
            PedidoProductoDTO pedidoProductoDTO = new PedidoProductoDTO(pedidoProducto);
            return ResponseEntity.ok(pedidoProductoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}

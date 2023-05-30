package Macart.Ecommerce.Controladores;

import Macart.Ecommerce.DTO.PedidoDTO;
import Macart.Ecommerce.DTO.PedidoProductoDTO;
import Macart.Ecommerce.Modelos.PedidoProducto;
import Macart.Ecommerce.Repositorio.ClienteRepositorio;
import Macart.Ecommerce.Repositorio.PedidoProductoRepositorio;
import Macart.Ecommerce.Repositorio.PedidoRepositorio;
import Macart.Ecommerce.Servicios.PedidoProductoServicio;
import Macart.Ecommerce.Servicios.PedidoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;
@RestController
public class PedidoProductorControlador {
    @Autowired
    private PedidoServicio pedidoServicio;
    @Autowired
    private PedidoProductoServicio pedidoProductoServicio;


    @GetMapping("/api/pedidoProducto")
    public ResponseEntity<Object> obtenerPedidoProducto(Authentication authentication) {
        if(pedidoProductoServicio.isAdmin(authentication)){
            return (new ResponseEntity<>(pedidoProductoServicio.obtenerPedidoProducto(authentication), HttpStatus.ACCEPTED));
        }
        return new ResponseEntity<>("No tiene los permisos para solicitar estos datos", HttpStatus.FORBIDDEN);
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

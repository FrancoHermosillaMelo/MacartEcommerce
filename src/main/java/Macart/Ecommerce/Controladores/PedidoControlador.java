package Macart.Ecommerce.Controladores;


import Macart.Ecommerce.DTO.CarritoDTO;
import Macart.Ecommerce.DTO.PedidoDTO;
import Macart.Ecommerce.DTO.PedidoProductoDTO;
import Macart.Ecommerce.Modelos.*;
import Macart.Ecommerce.Servicios.ClienteServicio;
import Macart.Ecommerce.Servicios.PedidoProductoServicio;
import Macart.Ecommerce.Servicios.PedidoServicio;
import Macart.Ecommerce.Servicios.ProductoTiendaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


@RestController
public class PedidoControlador {
    @Autowired
    private ClienteServicio clienteServicio;
    @Autowired
    private PedidoServicio pedidoServicio;
    @Autowired
    private ProductoTiendaServicio productoTiendaServicio;
    @Autowired
    private PedidoProductoServicio pedidoProductoServicio;


    @GetMapping("/api/pedidos")
    public ResponseEntity<Object> obtenerPedidos() {
        return (new ResponseEntity<>(pedidoServicio.obtenerPedidos(), HttpStatus.ACCEPTED));
    }


    @GetMapping("/api/clientes/{id}/pedidos")
    public List<PedidoDTO> obtenerPedidoCliente(Authentication authentication,@PathVariable long id) {
    Cliente cliente = clienteServicio.obtenerClientePorId(id);

    List<Pedido> pedidos = pedidoServicio.findByCliente(cliente);

    List<PedidoDTO> pedidosDTO = pedidos.stream()
            .map(pedido -> new PedidoDTO(pedido))
            .collect(toList());

    return  pedidosDTO;
    }

    @PostMapping("/api/pedidos")
    public ResponseEntity<Object> crearPedidos(
            @RequestParam Long clienteId,
            @RequestParam String metodoDeEnvio,
            @RequestParam String metodoDePago) {

        Cliente cliente = clienteServicio.obtenerClientePorId(clienteId);
        if( !cliente.getPedidos().stream().filter(pedidos -> !pedidos.isPagado()).collect(toList()).isEmpty()  ){
            return new ResponseEntity<>("Ya tienes un pedido en curso", HttpStatus.OK);
        }

        Pedido nuevoPedido = new Pedido(LocalDateTime.now(),false , 0,metodoDeEnvio,PedidoMetodoDePago.valueOf(metodoDePago));
        cliente.agregarPedido(nuevoPedido);
        pedidoServicio.guardarPedido(nuevoPedido);


        return ResponseEntity.status(HttpStatus.CREATED).body("Pedido creado");
    }
    @PostMapping("/api/pedidos/carrito")
    public  ResponseEntity<Object> añardirCarrito(Authentication authentication, @RequestBody CarritoDTO pedidoProductoDTO){

        Cliente cliente = clienteServicio.obtenerClienteAutenticado(authentication);
        Pedido pedido = cliente.getPedidos().stream().filter(pedidos -> !pedidos.isPagado()).collect(toList()).get(0);
        ProductoTienda producto = productoTiendaServicio.obtenerProductoPorId(pedidoProductoDTO.getId());
        Map<String, Integer> tallas = pedidoProductoDTO.getTallas();

        PedidoProducto pedidoProducto = new PedidoProducto();
        producto.agregarPedidoProducto(pedidoProducto);
        pedido.agregarPedidoProducto(pedidoProducto);

        Map<String, Integer> tallasProducto = pedidoProducto.getTallas();

        for (Map.Entry<String, Integer> entry : tallas.entrySet()) {
            if (!entry.getKey().equalsIgnoreCase("XS") && !entry.getKey().equalsIgnoreCase("S") && !entry.getKey().equalsIgnoreCase("M") &&
                    !entry.getKey().equalsIgnoreCase("L") && !entry.getKey().equalsIgnoreCase("XL")) {
                return new ResponseEntity<>("Las tallas superiores disponibles son : 'XS','S','M','L','XL'", HttpStatus.FORBIDDEN);
            }
            if((entry.getValue() < 0)){
                return new ResponseEntity<>("La cantidad de stock no puede ser negativa", HttpStatus.FORBIDDEN);
            }

            tallasProducto.put(entry.getKey(), entry.getValue());
            pedidoProductoServicio.guardarPedidoProducto(pedidoProducto);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Producto tienda");
    }


}

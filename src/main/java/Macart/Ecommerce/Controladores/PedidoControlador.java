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
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;


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


    @GetMapping("/api/pedidos") /* CHECK */
    public ResponseEntity<Object> obtenerPedidos() {
        return (new ResponseEntity<>(pedidoServicio.obtenerPedidos(), HttpStatus.ACCEPTED));
    }
    @GetMapping("/api/pedidos/{id}") /* CHECK */
    public ResponseEntity<Object> obtenerPedidoPorId(@PathVariable long id, Authentication authentication){
        Pedido pedidoSolicitado = pedidoServicio.ObtenerPedidoPorId(id);
        Cliente cliente = clienteServicio.obtenerClienteAutenticado(authentication);
        if(!clienteServicio.isAdmin(authentication)){
            if(pedidoSolicitado == null){
                return new ResponseEntity<>("El pedido no existe", HttpStatus.FORBIDDEN);
            }
            if(pedidoSolicitado.isEliminado()){
                return new ResponseEntity<>("El pedido está eliminado", HttpStatus.FORBIDDEN);
            }
            if(cliente.getPedidos().stream().noneMatch(pedido -> pedido.getId() == pedidoSolicitado.getId())){
                return new ResponseEntity<>("Este pedido no te pertenece", HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>(new PedidoDTO(pedidoSolicitado), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(new PedidoDTO(pedidoSolicitado), HttpStatus.ACCEPTED);
    }


    @GetMapping("/api/clientes/pedidos") /* CHECK */
    public List<PedidoDTO> obtenerPedidoCliente(Authentication authentication) {
    Cliente cliente = clienteServicio.obtenerClienteAutenticado(authentication);

    List<Pedido> pedidos = pedidoServicio.findByCliente(cliente);

    List<PedidoDTO> pedidosDTO = pedidos.stream()
            .map(pedido -> new PedidoDTO(pedido))
            .collect(toList());

    return  pedidosDTO;
    }

    @GetMapping("/api/clientes/pedidosActivados") /* CHECK */
    public ResponseEntity<Object> obtenerPedidosActivados(Authentication authentication){
        Cliente cliente = clienteServicio.obtenerClienteAutenticado(authentication);
        Set<Pedido> pedidosCliente = cliente.getPedidos();
        Set<Pedido> pedidosActivados = cliente.getPedidos().stream().filter(pedido -> !pedido.isEliminado()).collect(toSet());
        Set<PedidoDTO> pedidoClienteDTOS = pedidosActivados.stream().map(pedido -> new PedidoDTO(pedido)).collect(toSet());

        return new ResponseEntity<>(pedidoClienteDTOS, HttpStatus.ACCEPTED);
    }

    @PutMapping("/api/pedidos") /* CHECK */
    public ResponseEntity<Object> eliminarPedido(Authentication authentication, @RequestParam long idPedido){
        Pedido pedidoEliminar = pedidoServicio.ObtenerPedidoPorId(idPedido);
        Cliente cliente = clienteServicio.obtenerClienteAutenticado(authentication);

        if(pedidoEliminar == null){
            return new ResponseEntity<>("El pedido a eliminar no existe", HttpStatus.FORBIDDEN);
        }
        if(pedidoEliminar.isPagado()){
            return new ResponseEntity<>("No se puede eliminar un pedido pago", HttpStatus.FORBIDDEN);
        }
        if(cliente == null){
            return new ResponseEntity<>("El cliente no existe", HttpStatus.FORBIDDEN);
        }
        if(cliente.getPedidos().stream().noneMatch(pedido -> pedido.getId() == pedido.getId())){
            return new ResponseEntity<>("El pedido a eliminar no es tuyo", HttpStatus.FORBIDDEN);
        }
        pedidoEliminar.setEliminado(true);
        pedidoServicio.guardarPedido(pedidoEliminar);

        return new ResponseEntity<>("Eliminado correctamente", HttpStatus.ACCEPTED);
    }
    @PostMapping("/api/pedidos") /* CHECK */
    public ResponseEntity<Object> crearPedidos(
            Authentication authentication)
    {

        Cliente cliente = clienteServicio.obtenerClienteAutenticado(authentication);

        Pedido nuevoPedido = new Pedido(LocalDateTime.now(),false , 0, false);
        cliente.agregarPedido(nuevoPedido);
        pedidoServicio.guardarPedido(nuevoPedido);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido.getId());
    }
    @PostMapping("/api/pedidos/carrito") /* CHECK */
    public  ResponseEntity<Object> añadirCarrito(Authentication authentication, @RequestBody CarritoDTO carritoDTO){

        Cliente cliente = clienteServicio.obtenerClienteAutenticado(authentication);
        Pedido pedido = pedidoServicio.ObtenerPedidoPorId(carritoDTO.getIdPedido());
        ProductoTienda producto = productoTiendaServicio.obtenerProductoPorId(carritoDTO.getIdProducto());
        Map<String, Integer> tallas = carritoDTO.getTallas();

        PedidoProducto pedidoProducto = new PedidoProducto();
        producto.agregarPedidoProducto(pedidoProducto);
        pedido.agregarPedidoProducto(pedidoProducto);

        Map<String, Integer> tallasProducto = pedidoProducto.getTallas();
        if(!producto.isActivo()){
            return new ResponseEntity<>("Este producto no está disponible", HttpStatus.FORBIDDEN);
        }
        if(pedido.isEliminado()){
            return new ResponseEntity<>("El pedido está eliminado", HttpStatus.FORBIDDEN);
        }
        if(pedido.isPagado()){
            return new ResponseEntity<>("El pedido está pago", HttpStatus.FORBIDDEN);
        }
        if(tallas.isEmpty()){
            return new ResponseEntity<>("Las tallas no pueden estar vacias", HttpStatus.FORBIDDEN);
        }

        for (Map.Entry<String, Integer> entry : tallas.entrySet()) {
            if (!entry.getKey().equalsIgnoreCase("XS") && !entry.getKey().equalsIgnoreCase("S") && !entry.getKey().equalsIgnoreCase("M") &&
                    !entry.getKey().equalsIgnoreCase("L") && !entry.getKey().equalsIgnoreCase("XL")) {
                return new ResponseEntity<>("Las tallas superiores disponibles son : 'XS','S','M','L','XL'", HttpStatus.FORBIDDEN);
            }
            if((entry.getValue() <= 0)){
                return new ResponseEntity<>("La cantidad de stock no puede ser negativa", HttpStatus.FORBIDDEN);
            }
            tallasProducto.put(entry.getKey(), entry.getValue());
            productoTiendaServicio.guardarProducto(producto);
        }
        pedido.setMontoTotal(carritoDTO.getMontoTotal());
        pedidoServicio.guardarPedido(pedido);
        pedidoProductoServicio.guardarPedidoProducto(pedidoProducto);

        return ResponseEntity.status(HttpStatus.CREATED).body("Se añadieron correctamente los productos al pedido");
    }


}

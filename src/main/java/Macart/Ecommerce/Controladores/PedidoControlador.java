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


    @GetMapping("/api/pedidos")
    public ResponseEntity<Object> obtenerPedidos() {
        return (new ResponseEntity<>(pedidoServicio.obtenerPedidos(), HttpStatus.ACCEPTED));
    }


    @GetMapping("/api/clientes/pedidos")
    public List<PedidoDTO> obtenerPedidoCliente(Authentication authentication) {
    Cliente cliente = clienteServicio.obtenerClienteAutenticado(authentication);

    List<Pedido> pedidos = pedidoServicio.findByCliente(cliente);

    List<PedidoDTO> pedidosDTO = pedidos.stream()
            .map(pedido -> new PedidoDTO(pedido))
            .collect(toList());

    return  pedidosDTO;
    }

    @GetMapping("/api/clientes/pedidosActivados")
    public ResponseEntity<Object> obtenerPedidosActivados(Authentication authentication){
        Cliente cliente = clienteServicio.obtenerClienteAutenticado(authentication);
        Set<Pedido> pedidosCliente = cliente.getPedidos();
        Set<PedidoDTO> pedidoClienteDTOS = pedidosCliente.stream().map(pedido -> new PedidoDTO(pedido)).collect(toSet());

        return new ResponseEntity<>(pedidoClienteDTOS, HttpStatus.ACCEPTED);
    }

    @PostMapping("/api/pedidos")
    public ResponseEntity<Object> crearPedidos(
            Authentication authentication,
            @RequestParam String metodoDeEnvio)
             {

        Cliente cliente = clienteServicio.obtenerClienteAutenticado(authentication);

        Pedido nuevoPedido = new Pedido(LocalDateTime.now(),false , 0,metodoDeEnvio, false);
        cliente.agregarPedido(nuevoPedido);
        pedidoServicio.guardarPedido(nuevoPedido);

        return ResponseEntity.status(HttpStatus.CREATED).body("Pedido creado");
    }
    @PutMapping("/api/pedidos")
    public ResponseEntity<Object> eliminarPedido(Authentication authentication, @RequestParam long idPedido){
        Pedido pedidoEliminar = pedidoServicio.ObtenerPedidoPorId(idPedido);
        Cliente cliente = clienteServicio.obtenerClienteAutenticado(authentication);

        if(pedidoEliminar == null){
            return new ResponseEntity<>("El pedido a eliminar no existe", HttpStatus.FORBIDDEN);
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
    @PostMapping("/api/pedidos/carrito")
    public  ResponseEntity<Object> añadirCarrito(Authentication authentication, @RequestBody CarritoDTO pedidoProductoDTO){

        Cliente cliente = clienteServicio.obtenerClienteAutenticado(authentication);
        Pedido pedido = cliente.getPedidos().stream().filter(pedidos -> !pedidos.isPagado()).collect(toList()).get(0);
        ProductoTienda producto = productoTiendaServicio.obtenerProductoPorId(pedidoProductoDTO.getId());
        Map<String, Integer> tallas = pedidoProductoDTO.getTallas();

        PedidoProducto pedidoProducto = new PedidoProducto();
        producto.agregarPedidoProducto(pedidoProducto);
        pedido.agregarPedidoProducto(pedidoProducto);

        Map<String, Integer> tallasProducto = pedidoProducto.getTallas();
        int cantidad = 0;

        for (Map.Entry<String, Integer> entry : tallas.entrySet()) {
            if (!entry.getKey().equalsIgnoreCase("XS") && !entry.getKey().equalsIgnoreCase("S") && !entry.getKey().equalsIgnoreCase("M") &&
                    !entry.getKey().equalsIgnoreCase("L") && !entry.getKey().equalsIgnoreCase("XL")) {
                return new ResponseEntity<>("Las tallas superiores disponibles son : 'XS','S','M','L','XL'", HttpStatus.FORBIDDEN);
            }
            if((entry.getValue() < 0)){
                return new ResponseEntity<>("La cantidad de stock no puede ser negativa", HttpStatus.FORBIDDEN);
            }

            tallasProducto.put(entry.getKey(), entry.getValue());
            cantidad = entry.getValue();
            pedidoProductoServicio.guardarPedidoProducto(pedidoProducto);
            pedido.setMontoTotal(pedido.getMontoTotal() + pedidoProducto.getProductoTienda().getPrecio()*cantidad);
            pedidoServicio.guardarPedido(pedido);
        }




        return ResponseEntity.status(HttpStatus.CREATED).body("Producto tienda");
    }


}

package Macart.Ecommerce.DTO;

import Macart.Ecommerce.Modelos.PedidoProducto;
import Macart.Ecommerce.Modelos.ProductoTienda;

import java.util.HashMap;
import java.util.Map;

public class PedidoProductoDTO {
    private long id;
    private long idPedido;
    private Long productoTiendaId;
    private ProductoTiendaDTO productoTienda;
    private Map<String, Integer> tallas =  new HashMap<>();


    public PedidoProductoDTO(PedidoProducto pedidoProducto) {
        this.id = pedidoProducto.getId();
        this.productoTiendaId = pedidoProducto.getProductoTienda().getId();
        this.tallas = pedidoProducto.getTallas();
        this.productoTienda = new ProductoTiendaDTO(pedidoProducto.getProductoTienda()) ;
    }

    public long getId() {
        return id;
    }

    public long getIdPedido() {return idPedido;}
    public Map<String, Integer> getTallas() {
        return tallas;
    }

    public Long getProductoTiendaId() {
        return productoTiendaId;
    }

    public ProductoTiendaDTO getProductoTienda() {
        return productoTienda;
    }
}

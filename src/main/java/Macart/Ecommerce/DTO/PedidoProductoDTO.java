package Macart.Ecommerce.DTO;

import Macart.Ecommerce.Modelos.PedidoProducto;
import Macart.Ecommerce.Modelos.ProductoTienda;

public class PedidoProductoDTO {
    private long id;
    private Long productoTiendaId;
    private ProductoTiendaDTO productoTienda;
    private int cantidad;

    public PedidoProductoDTO(PedidoProducto pedidoProducto) {
        this.id = pedidoProducto.getId();
        this.productoTiendaId = pedidoProducto.getProductoTienda().getId();
        this.cantidad = pedidoProducto.getCantidad();
        this.productoTienda = new ProductoTiendaDTO(pedidoProducto.getProductoTienda()) ;
    }

    public long getId() {
        return id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public Long getProductoTiendaId() {
        return productoTiendaId;
    }

    public ProductoTiendaDTO getProductoTienda() {
        return productoTienda;
    }
}

package Macart.Ecommerce.Modelos;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class PedidoProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String cantidad;
    private String precioTotal;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productoTiendaId")
    private ProductoTienda productoTienda;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pedidoId")
    private Pedido pedido;

    public PedidoProducto() {
    }

    public PedidoProducto(String cantidad, String precioTotal) {
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
    }

    public long getId() {
        return id;
    }


    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(String precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public ProductoTienda getProductoTienda() {
        return productoTienda;
    }

    public void setProductoTienda(ProductoTienda productoTienda) {
        this.productoTienda = productoTienda;
    }
}

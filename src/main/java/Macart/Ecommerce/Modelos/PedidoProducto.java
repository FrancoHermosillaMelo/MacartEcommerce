package Macart.Ecommerce.Modelos;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class PedidoProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private int cantidad;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productoTiendaId")
    private ProductoTienda productoTienda;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pedidoId")
    private Pedido pedido;

    public PedidoProducto() {
    }

    public PedidoProducto(int cantidad, ProductoTienda productoTienda) {
        this.cantidad = cantidad;
        this.productoTienda = productoTienda;
    }

    public long getId() {
        return id;
    }


    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
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

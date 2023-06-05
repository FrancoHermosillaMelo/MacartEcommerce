package Macart.Ecommerce.Modelos;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class PedidoProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    @ElementCollection
    private Map<String, Integer> tallas =  new HashMap<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productoTiendaId")
    private ProductoTienda productoTienda;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pedidoId")
    private Pedido pedido;

    public PedidoProducto() {
    }

    public long getId() {
        return id;
    }

    public Map<String, Integer> getTallas() {
        return tallas;
    }

    public ProductoTienda getProductoTienda() {
        return productoTienda;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setTallas(Map<String, Integer> tallas) {this.tallas = tallas;}
    public void setProductoTienda(ProductoTienda productoTienda) {this.productoTienda = productoTienda;}
    public void setPedido(Pedido pedido) {this.pedido = pedido;}
}

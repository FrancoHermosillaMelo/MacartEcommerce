package Macart.Ecommerce.DTO;
import Macart.Ecommerce.Modelos.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;


public class PedidoDTO {
    private long id;
    private LocalDateTime fechaDePedido;
    private boolean pagado;
    private double montoTotal;
    private Set<PedidoProductoDTO> pedidoProductos;
    private boolean eliminado;
    public PedidoDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.fechaDePedido = pedido.getFechaDePedido();
        this.pagado = pedido.isPagado();
        this.montoTotal = pedido.getMontoTotal() ;
        this.pedidoProductos = pedido.getPedidoProductos().stream().map(pedidoProducto -> new PedidoProductoDTO(pedidoProducto)).collect(Collectors.toSet());
        this.eliminado = pedido.isEliminado();
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getFechaDePedido() {
        return fechaDePedido;
    }

    public boolean isPagado() {
        return pagado;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public Set<PedidoProductoDTO> getPedidoProductos() {
        return pedidoProductos;
    }

    public boolean isEliminado() {
        return eliminado;
    }
}

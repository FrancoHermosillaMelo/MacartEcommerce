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
    private String metodoDeEnvio;
    private PedidoMetodoDePago metodoDePago;
    private Set<PedidoProductoDTO> pedidoProductos;


    public PedidoDTO(Pedido pedido) {

        this.id = pedido.getId();
        this.fechaDePedido = pedido.getFechaDePedido();
        this.pagado = pedido.isPagado();
        this.montoTotal = pedido.getMontoTotal() ;
        this.metodoDeEnvio = pedido.getMetodoDeEnvio();
        this.metodoDePago = pedido.getMetodoDePago();
        this.pedidoProductos = pedido.getPedidoProductos().stream().map(pedidoProducto -> new PedidoProductoDTO(pedidoProducto)).collect(Collectors.toSet());
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

    public String getMetodoDeEnvio() {
        return metodoDeEnvio;
    }

    public PedidoMetodoDePago getMetodoDePago() {
        return metodoDePago;
    }

    public Set<PedidoProductoDTO> getPedidoProductos() {
        return pedidoProductos;
    }


}

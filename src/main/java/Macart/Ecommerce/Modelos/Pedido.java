package Macart.Ecommerce.Modelos;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private LocalDateTime fechaDePedido;
    private boolean pagado;
    private double montoTotal;
    @OneToMany(mappedBy="pedido", fetch= FetchType.EAGER)
    private Set<PedidoProducto> pedido_productos = new HashSet<>();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clientId")
    private Cliente cliente;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "comprobanteId", referencedColumnName = "id")
    private Comprobante comprobante;


    public Pedido() {
    }

    public Pedido(LocalDateTime fechaDePedido, Boolean pagado, Double montoTotal) {
        this.fechaDePedido = fechaDePedido;
        this.pagado = pagado;
        montoTotal = montoTotal;
    }
    public void agregarPedido_Producto(PedidoProducto pedido_producto) {
        pedido_producto.setPedido(this);
        pedido_productos.add(pedido_producto);
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getFechaDePedido() {
        return fechaDePedido;
    }

    public void setFechaDePedido(LocalDateTime fechaDePedido) {
        this.fechaDePedido = fechaDePedido;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Comprobante getComprobante() {
        return comprobante;
    }

    public void setComprobante(Comprobante comprobante) {
        this.comprobante = comprobante;
    }

    public Set<PedidoProducto> getPedido_productos() {
        return pedido_productos;
    }

    public void setPedido_productos(Set<PedidoProducto> pedido_productos) {
        this.pedido_productos = pedido_productos;
    }

}

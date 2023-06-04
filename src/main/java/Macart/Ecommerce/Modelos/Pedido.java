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
    private String metodoDeEnvio;

    @OneToMany(mappedBy="pedido", fetch= FetchType.EAGER)
    private Set<PedidoProducto> pedidoProductos = new HashSet<>();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clientId")
    private Cliente cliente;

    public Pedido() {
    }

    public Pedido(LocalDateTime fechaDePedido, boolean pagado, double montoTotal, String metodoDeEnvio) {
        this.fechaDePedido = fechaDePedido;
        this.pagado = pagado;
        this.montoTotal = montoTotal;
        this.metodoDeEnvio = metodoDeEnvio;

    }

    public void agregarPedidoProducto(PedidoProducto pedidoproducto) {
        pedidoproducto.setPedido(this);
        pedidoProductos.add(pedidoproducto);
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

    public Set<PedidoProducto> getPedidoProductos() {
        return pedidoProductos;
    }

    public void setPedidoProductos(Set<PedidoProducto> pedidoProductos) {
        this.pedidoProductos = pedidoProductos;
    }

    public String getMetodoDeEnvio() {
        return metodoDeEnvio;
    }

    public void setMetodoDeEnvio(String metodoDeEnvio) {
        this.metodoDeEnvio = metodoDeEnvio;
    }



}

package Macart.Ecommerce.Modelos;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Comprobante {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String metodoDePago;
    private String tipoDeEnvio;
    private LocalDateTime fechaDeComprobante;
    private double montoTotal;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clienteId")
    private Cliente cliente;
    @OneToOne(mappedBy = "comprobante")
    private Pedido pedido;

    public Comprobante() {
    }

    public Comprobante(String metodoDePago, String tipoDeEnvio, LocalDateTime fechaDeComprobante, double montoTotal) {
        this.metodoDePago = metodoDePago;
        this.tipoDeEnvio = tipoDeEnvio;
        this.fechaDeComprobante = fechaDeComprobante;
        this.montoTotal = montoTotal;
    }

    public long getId() {
        return id;
    }

    public String getMetodoDePago() {
        return metodoDePago;
    }

    public void setMetodoDePago(String metodoDePago) {
        this.metodoDePago = metodoDePago;
    }

    public String getTipoDeEnvio() {
        return tipoDeEnvio;
    }

    public void setTipoDeEnvio(String tipoDeEnvio) {
        tipoDeEnvio = tipoDeEnvio;
    }

    public LocalDateTime getFechaDeComprobante() {
        return fechaDeComprobante;
    }

    public void setFechaDeComprobante(LocalDateTime fechaDeComprobante) {
        this.fechaDeComprobante = fechaDeComprobante;
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

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }


}

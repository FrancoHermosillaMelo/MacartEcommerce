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
    private String tipoDeEnvio;
    private LocalDateTime fechaDeComprobante;
    private double montoTotal;
    private CardColor coloTarjeta;
    private CardType tipoTarjeta;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clienteId")
    private Cliente cliente;



    public Comprobante() {
    }

    public Comprobante( String tipoDeEnvio, LocalDateTime fechaDeComprobante, double montoTotal,CardType tipoTarjeta, CardColor colorTarjeta ) {
        this.tipoDeEnvio = tipoDeEnvio;
        this.fechaDeComprobante = fechaDeComprobante;
        this.montoTotal = montoTotal;
        this.coloTarjeta = colorTarjeta;
        this.tipoTarjeta = tipoTarjeta;
    }

    public long getId() {
        return id;
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

    public CardColor getColoTarjeta() {
        return coloTarjeta;
    }

    public void setColoTarjeta(CardColor coloTarjeta) {
        this.coloTarjeta = coloTarjeta;
    }

    public CardType getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(CardType tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }
}

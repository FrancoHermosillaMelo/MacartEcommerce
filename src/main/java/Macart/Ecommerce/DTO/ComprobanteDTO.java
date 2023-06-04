package Macart.Ecommerce.DTO;

import Macart.Ecommerce.Modelos.CardColor;
import Macart.Ecommerce.Modelos.CardType;
import Macart.Ecommerce.Modelos.Comprobante;
import Macart.Ecommerce.Modelos.PedidoMetodoDePago;
import java.time.LocalDateTime;



public class ComprobanteDTO {
    private long id;
    private String tipoDeEnvio;
    private LocalDateTime fechaDeComprobante;
    private double montoTotal;
    private CardColor colorTarjeta;
    private CardType tipoTarjeta;


    public ComprobanteDTO(Comprobante comprobante) {

        this.id = comprobante.getId();
        this.tipoDeEnvio = comprobante.getTipoDeEnvio();
        this.fechaDeComprobante = comprobante.getFechaDeComprobante();
        this.montoTotal = comprobante.getMontoTotal();
        this.tipoTarjeta = comprobante.getTipoTarjeta();
        this.colorTarjeta = comprobante.getColoTarjeta();
    }

    public long getId() {
        return id;
    }

    public String getTipoDeEnvio() {
        return tipoDeEnvio;
    }

    public LocalDateTime getFechaDeComprobante() {
        return fechaDeComprobante;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public CardColor getColoTarjeta() {
        return colorTarjeta;
    }

    public CardType getTipoTarjeta() {
        return tipoTarjeta;
    }
}

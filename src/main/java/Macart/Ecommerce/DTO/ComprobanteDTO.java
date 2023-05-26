package Macart.Ecommerce.DTO;

import Macart.Ecommerce.Modelos.Comprobante;
import Macart.Ecommerce.Modelos.PedidoMetodoDePago;
import java.time.LocalDateTime;



public class ComprobanteDTO {
    private long id;
    private PedidoMetodoDePago metodoDePago;
    private String tipoDeEnvio;
    private LocalDateTime fechaDeComprobante;
    private double montoTotal;


    public ComprobanteDTO(Comprobante comprobante) {

        this.id = comprobante.getId();
        this.metodoDePago = comprobante.getMetodoDePago();
        this.tipoDeEnvio = comprobante.getTipoDeEnvio();
        this.fechaDeComprobante = comprobante.getFechaDeComprobante();
        this.montoTotal = comprobante.getMontoTotal();
    }

    public long getId() {
        return id;
    }

    public PedidoMetodoDePago getMetodoDePago() {
        return metodoDePago;
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

}

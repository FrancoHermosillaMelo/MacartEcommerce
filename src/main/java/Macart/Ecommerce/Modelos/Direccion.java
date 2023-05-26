package Macart.Ecommerce.Modelos;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String calle;
    private String numeroDomicilio;
    private String barrio;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clientId")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "departamentoId")
    private Departamento departamento;

    public Direccion() {
    }

    public Direccion(String calle, String numeroDomicilio, String barrio) {
        this.calle = calle;
        this.numeroDomicilio = numeroDomicilio;
        this.barrio = barrio;
    }

    public long getId() {
        return id;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumeroDomicilio() {
        return numeroDomicilio;
    }

    public void setNumeroDomicilio(String numeroDomicilio) {
        this.numeroDomicilio = numeroDomicilio;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }
}

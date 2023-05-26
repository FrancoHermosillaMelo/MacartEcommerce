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
    private String ciudad;
    private String departamento;

    private String codigoPostal;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clientId")
    private Cliente cliente;

    public Direccion() {
    }

    public Direccion(String calle, String numeroDomicilio, String barrio, String ciudad, String departamento, String codigoPostal) {
        this.calle = calle;
        this.numeroDomicilio = numeroDomicilio;
        this.barrio = barrio;
        this.ciudad = ciudad;
        this.departamento = departamento;
        this.codigoPostal = codigoPostal;
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

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
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

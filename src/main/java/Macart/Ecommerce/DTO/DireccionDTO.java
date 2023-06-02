package Macart.Ecommerce.DTO;

import Macart.Ecommerce.Modelos.Direccion;

public class DireccionDTO {
    private long id;
    private String calle;
    private String numeroDomicilio;
    private String barrio;
    private String ciudad;
    private String departamento ;
    private String codigoPostal;
    public DireccionDTO(Direccion direccion) {
        this.id = direccion.getId();
        this.calle = direccion.getCalle();
        this.numeroDomicilio = direccion.getNumeroDomicilio();
        this.barrio = direccion.getBarrio();
        this.departamento = direccion.getDepartamento();
        this.ciudad = direccion.getCiudad();
        this.codigoPostal = direccion.getCodigoPostal();
    }

    public long getId() {
        return id;
    }

    public String getCalle() {
        return calle;
    }

    public String getNumeroDomicilio() {
        return numeroDomicilio;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getDepartamento() {
        return departamento;
    }

    public String getBarrio() {
        return barrio;
    }

    public String getCodigoPostal() {return codigoPostal;}
}

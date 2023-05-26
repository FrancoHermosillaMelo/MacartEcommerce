package Macart.Ecommerce.Modelos;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Departamento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String nombre;
    @OneToMany(mappedBy = "departamento", fetch = FetchType.EAGER)
    private Set<Direccion> direcciones;
    @OneToMany(mappedBy = "departamento", fetch = FetchType.EAGER)
    private Set<Ciudad> ciudades;

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(Set<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }

    public Set<Direccion> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(Set<Direccion> direcciones) {
        this.direcciones = direcciones;
    }
}

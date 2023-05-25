package Macart.Ecommerce.Modelos;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Promocion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String descripcion;
    private String name;
    private double porcentajeDescuento;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFinal;
    private int cupos;
    private boolean activa;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productoTiendaId")
    private ProductoTienda productoTienda;

    public Promocion() {
    }

    public Promocion(String descripcion, String name, double porcentajeDescuento, LocalDateTime fechaInicio, LocalDateTime fechaFinal, int cupos, boolean activa) {
        this.descripcion = descripcion;
        this.name = name;
        this.porcentajeDescuento = porcentajeDescuento;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.cupos = cupos;
        this.activa = activa;
    }

    public long getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(LocalDateTime fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public int getCupos() {
        return cupos;
    }

    public void setCupos(int cupos) {
        this.cupos = cupos;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public ProductoTienda getProductoTienda() {
        return productoTienda;
    }

    public void setProductoTienda(ProductoTienda productoTienda) {
        this.productoTienda = productoTienda;
    }


}

package Macart.Ecommerce.DTO;

import Macart.Ecommerce.Modelos.*;

import javax.persistence.ElementCollection;
import java.util.List;
import java.util.Map;

public class ProductoTiendaDTO {
    private long id;
    private String nombre;
    private double precio;
    private String descripcion;
    private Map<String,Integer> tallas;
    private List<String> imagenesUrl;
    private ProductoTiendaCategoriaGenero categoriaGenero;
    private String subCategoria;
    private boolean activo;
    private boolean unico;
    private int cantidadStockUnico;
    public ProductoTiendaDTO(ProductoTienda productoTienda) {
        this.id = productoTienda.getId();
        this.nombre = productoTienda.getNombre();
        this.precio = productoTienda.getPrecio();
        this.descripcion = productoTienda.getDescripcion();
        this.tallas = productoTienda.getTallas();
        this.imagenesUrl = productoTienda.getImagenesUrl();
        this.categoriaGenero = productoTienda.getCategoriaGenero();
        this.subCategoria = productoTienda.getSubCategoria();
        this.activo = productoTienda.isActivo();
        this.unico = productoTienda.isUnico();
        this.cantidadStockUnico = productoTienda.getCantidadStockUnico();
    }

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Map<String, Integer> getTallas() {
        return tallas;
    }
    public ProductoTiendaCategoriaGenero getCategoriaGenero() {
        return categoriaGenero;
    }
    public String getSubCategoria() {
        return subCategoria;
    }
    public List<String> getImagenesUrl() {
        return imagenesUrl;
    }
    public boolean isActivo() {
        return activo;
    }
    public boolean isUnico() {return unico;}
    public int getCantidadStockUnico() {return cantidadStockUnico;}
}

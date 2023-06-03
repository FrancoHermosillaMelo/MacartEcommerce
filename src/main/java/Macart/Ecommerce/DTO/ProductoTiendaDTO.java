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
    private Map<String,Integer> tallaSuperior;
    private Map<String,Integer> tallaInferior;
    private List<String> imagenesUrl;
    private ProductoTiendaCategoriaGenero categoriaGenero;
    private String subCategoria;
    private boolean activo;
    private int stock;
    public ProductoTiendaDTO(ProductoTienda productoTienda) {
        this.id = productoTienda.getId();
        this.nombre = productoTienda.getNombre();
        this.precio = productoTienda.getPrecio();
        this.descripcion = productoTienda.getDescripcion();
        this.tallaSuperior = productoTienda.getTallaSuperior();
        this.tallaInferior = productoTienda.getTallaInferior();
        this.imagenesUrl = productoTienda.getImagenesUrl();
        this.categoriaGenero = productoTienda.getCategoriaGenero();
        this.subCategoria = productoTienda.getSubCategoria();
        this.stock = productoTienda.getStock();
        this.activo = productoTienda.isActivo();
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

    public Map<String, Integer> getTallaSuperior() {
        return tallaSuperior;
    }

    public Map<String, Integer> getTallaInferior() {
        return tallaInferior;
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

    public int getStock() {
        return stock;
    }

    public boolean isActivo() {
        return activo;
    }
}

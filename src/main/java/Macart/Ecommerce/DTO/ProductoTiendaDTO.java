package Macart.Ecommerce.DTO;

import Macart.Ecommerce.Modelos.*;

public class ProductoTiendaDTO {
    private long id;
    private String nombre;
    private double precio;
    private String descripcion;
    private int cantidadStock;
    private ProductoTiendaTallaSuperior tallaSuperior;
    private ProductoTiendaTallaInferior tallaInferior;
    private String imagenUrl;
    private ProductoTiendaCategoriaGenero categoriaGenero;
    private String subCategoria;
    public ProductoTiendaDTO(ProductoTienda productoTienda) {
        this.id = productoTienda.getId();
        this.nombre = productoTienda.getNombre();
        this.precio = productoTienda.getPrecio();
        this.descripcion = productoTienda.getDescripcion();
        this.cantidadStock = productoTienda.getCantidadStock();
        this.tallaSuperior = productoTienda.getTallaSuperior();
        this.tallaInferior = productoTienda.getTallaInferior();
        this.imagenUrl = productoTienda.getImagenUrl();
        this.categoriaGenero = productoTienda.getCategoriaGenero();
        this.subCategoria = productoTienda.getSubCategoria();
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

    public int getCantidadStock() {
        return cantidadStock;
    }

    public ProductoTiendaTallaSuperior getTallaSuperior() {
        return tallaSuperior;
    }

    public ProductoTiendaTallaInferior getTallaInferior() {
        return tallaInferior;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public ProductoTiendaCategoriaGenero getCategoriaGenero() {
        return categoriaGenero;
    }

    public String getSubCategoria() {
        return subCategoria;
    }


}

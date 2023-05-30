package Macart.Ecommerce.Servicios;

import Macart.Ecommerce.DTO.ProductoTiendaDTO;
import Macart.Ecommerce.Modelos.ProductoTienda;

import java.util.List;

public interface ProductoTiendaServicio {
    List<ProductoTiendaDTO> obtenerTodosLosProductos();
    ProductoTienda obtenerProductoPorId(long id);
    ProductoTienda obtenerProductoPorNombre(String nombre);

    void guardarProducto(ProductoTienda productoTienda);
}

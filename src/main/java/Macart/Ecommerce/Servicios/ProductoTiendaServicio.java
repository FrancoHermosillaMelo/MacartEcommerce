package Macart.Ecommerce.Servicios;

import Macart.Ecommerce.DTO.ProductoTiendaDTO;
import Macart.Ecommerce.Modelos.ProductoTienda;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ProductoTiendaServicio {
    List<ProductoTiendaDTO> obtenerTodosLosProductos();
    ProductoTienda obtenerProductoPorId(long id);
    ProductoTienda obtenerProductoPorNombre(String nombre);
}

package Macart.Ecommerce.Servicios.Implementacion;


import Macart.Ecommerce.DTO.ProductoTiendaDTO;
import Macart.Ecommerce.Modelos.ProductoTienda;
import Macart.Ecommerce.Repositorio.ProductoTiendaRepositorio;
import Macart.Ecommerce.Servicios.ProductoTiendaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ProductoTiendaImplementacion implements ProductoTiendaServicio {
    @Autowired
    ProductoTiendaRepositorio productoTiendaRepositorio;
    @Override
    public List<ProductoTiendaDTO> obtenerTodosLosProductos() {
        return productoTiendaRepositorio.findAll()
                .stream().map(productoTienda ->
                        new ProductoTiendaDTO(productoTienda)).collect(toList());
    }

    @Override
    public ProductoTienda obtenerProductoPorId(long id) {
        return productoTiendaRepositorio.findById(id).orElse(null);
    }

    @Override
    public ProductoTienda obtenerProductoPorNombre(String nombre) {
        return productoTiendaRepositorio.findByNombre(nombre);
    }

    @Override
    public void guardarProducto(ProductoTienda productoTienda) {

    }

    @Override
    public void borrarProducto(ProductoTienda productoTienda) {
        productoTiendaRepositorio.delete(productoTienda);
    }
}

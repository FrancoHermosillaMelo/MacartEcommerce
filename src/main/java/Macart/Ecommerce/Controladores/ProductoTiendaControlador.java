package Macart.Ecommerce.Controladores;

import Macart.Ecommerce.DTO.PedidoProductoDTO;
import Macart.Ecommerce.DTO.ProductoTiendaDTO;
import Macart.Ecommerce.Modelos.ProductoTienda;
import Macart.Ecommerce.Modelos.ProductoTiendaCategoriaGenero;
import Macart.Ecommerce.Modelos.ProductoTiendaTallaInferior;
import Macart.Ecommerce.Modelos.ProductoTiendaTallaSuperior;
import Macart.Ecommerce.Repositorio.ClienteRepositorio;
import Macart.Ecommerce.Repositorio.PedidoProductoRepositorio;
import Macart.Ecommerce.Repositorio.PedidoRepositorio;
import Macart.Ecommerce.Repositorio.ProductoTiendaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;
@RestController
public class ProductoTiendaControlador {
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    @Autowired
    private PedidoRepositorio pedidoRepositorio;
    @Autowired
    private PedidoProductoRepositorio pedidoProductoRepositorio;
    @Autowired
    private ProductoTiendaRepositorio productoTiendaRepositorio;

    @GetMapping("/api/productoTienda")
    public List<ProductoTiendaDTO> obtenerPedidoProductoTienda() {
        return productoTiendaRepositorio.findAll()
                .stream().map(productoTienda ->
                        new ProductoTiendaDTO(productoTienda)).collect(toList());
    }

    @GetMapping("/api/productoTienda/{id}")
    public ResponseEntity<Object> obtenerProductoTiendaPorId(@PathVariable Long id) {
        ProductoTienda productoTienda = productoTiendaRepositorio.findById(id).orElse(null);

        if (productoTienda != null) {
            ProductoTiendaDTO productoTiendaDTO = new ProductoTiendaDTO(productoTienda);
            return ResponseEntity.ok(productoTiendaDTO);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body("hola");
        }
    }

    @PostMapping("/api/productoTienda")
    public ResponseEntity<Object> crearNuevoProductoTienda(
            @RequestParam String nombre,
            @RequestParam double precio,
            @RequestParam String descripcion,
            @RequestParam int cantidadStock,
            @RequestParam(required = false) ProductoTiendaTallaSuperior tallaSuperior,
            @RequestParam(required = false) ProductoTiendaTallaInferior tallaInferior,
            @RequestParam(required = false) String imagenUrl,
            @RequestParam ProductoTiendaCategoriaGenero categoriaGenero,
            @RequestParam String subCategoria
    ) {

        ProductoTienda productoTiendaExistente = productoTiendaRepositorio.findByNombre(nombre);

        if (productoTiendaExistente != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("El producto ya existe.");
        }

        ProductoTienda nuevoProductoTienda = new ProductoTienda(
                nombre,
                precio,
                descripcion,
                cantidadStock,
                tallaSuperior,
                tallaInferior,
                imagenUrl,
                categoriaGenero,
                subCategoria
        );

        productoTiendaRepositorio.save(nuevoProductoTienda);

        return ResponseEntity.status(HttpStatus.CREATED).body("Se creó el nuevo producto.");
    }

    @PutMapping("/api/productoTienda")
    public ResponseEntity<Object> modificarProductoTienda(
            @RequestParam String nombre,
            @RequestParam double precio,
            @RequestParam String descripcion,
            @RequestParam int cantidadStock,
            @RequestParam(required = false) ProductoTiendaTallaSuperior tallaSuperior,
            @RequestParam(required = false) ProductoTiendaTallaInferior tallaInferior,
            @RequestParam(required = false) String imagenUrl,
            @RequestParam ProductoTiendaCategoriaGenero categoriaGenero,
            @RequestParam String subCategoria
    ) {
        ProductoTienda productoTiendaExistente = productoTiendaRepositorio.findByNombre(nombre);

        if (productoTiendaExistente != null) {
            productoTiendaExistente.setNombre(nombre);
            productoTiendaExistente.setPrecio(precio);
            productoTiendaExistente.setDescripcion(descripcion);
            productoTiendaExistente.setCantidadStock(cantidadStock);
            productoTiendaExistente.setTallaSuperior(tallaSuperior);
            productoTiendaExistente.setTallaInferior(tallaInferior);
            productoTiendaExistente.setImagenUrl(imagenUrl);
            productoTiendaExistente.setCategoriaGenero(categoriaGenero);
            productoTiendaExistente.setSubCategoria(subCategoria);

            productoTiendaRepositorio.save(productoTiendaExistente);

            return ResponseEntity.status(HttpStatus.CREATED).body("Se modificó el producto con el nombre" + nombre);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El producto con el" + nombre + "no fue encontrado");
        }
    }
}










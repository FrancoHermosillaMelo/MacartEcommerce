package Macart.Ecommerce.Controladores;

import Macart.Ecommerce.DTO.ProductoTiendaDTO;
import Macart.Ecommerce.Modelos.ProductoTienda;
import Macart.Ecommerce.Modelos.ProductoTiendaCategoriaGenero;
import Macart.Ecommerce.Modelos.ProductoTiendaTallaInferior;
import Macart.Ecommerce.Modelos.ProductoTiendaTallaSuperior;

import Macart.Ecommerce.Servicios.ClienteServicio;
import Macart.Ecommerce.Servicios.ProductoTiendaServicio;
import Macart.Ecommerce.Utilidades.ProductoTiendaUtilidades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


@RestController
public class ProductoTiendaControlador {

    @Autowired
    private ProductoTiendaServicio productoTiendaServicio;
    @Autowired
    private ClienteServicio clienteServicio;

    @GetMapping("/api/productoTienda")
    public ResponseEntity<Object> obtenerPedidoProductoTienda() {
        return new ResponseEntity<>(productoTiendaServicio.obtenerTodosLosProductos(),HttpStatus.ACCEPTED);
    }

    @GetMapping("/api/productoTienda/{id}")
    public ResponseEntity<Object> obtenerProductoTiendaPorId(@PathVariable Long id) {
        ProductoTienda productoTienda = productoTiendaServicio.obtenerProductoPorId(id);

        if (productoTienda != null) {
            ProductoTiendaDTO productoTiendaDTO = new ProductoTiendaDTO(productoTienda);
            return ResponseEntity.ok(productoTiendaDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el producto");
        }
    }


    @PostMapping("/api/productoTienda")
    public ResponseEntity<Object> crearNuevoProductoTienda(
            @RequestParam String nombre,
            @RequestParam double precio,
            @RequestParam String descripcion,
            @RequestParam(required = false) ProductoTiendaTallaSuperior tallaSuperior,
            @RequestParam(required = false) ProductoTiendaTallaInferior tallaInferior,
            @RequestParam(value = "archivo", required = false) MultipartFile[] imagenesUrl,
            @RequestParam ProductoTiendaCategoriaGenero categoriaGenero,
            @RequestParam String subCategoria,
            Authentication authentication) throws Exception {

        ProductoTienda productoTiendaExistente = productoTiendaServicio.obtenerProductoPorNombre(nombre);

        if (productoTiendaExistente != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("El producto ya existe.");
        }
        if(nombre.isBlank()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("El nombre no puede estar en blanco");
        }
        if (!Pattern.matches("^[a-zA-Z]+$", nombre)) {
            return new ResponseEntity<>("El nombre solo puede contener letras", HttpStatus.FORBIDDEN);
        }

        if(precio < 0){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("El precio no puede estar en negativo");
        }
        if(subCategoria.isBlank()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("La subcategoria no puede estar en blanco");
        }

        ProductoTienda nuevoProductoTienda = new ProductoTienda(nombre);

        List<String> imagenes = new ArrayList<>();
        for(MultipartFile imagen : imagenesUrl){
            if(imagen.isEmpty()){

                return new ResponseEntity<>("El archivo está vacio", HttpStatus.FORBIDDEN);
            }
            if(imagen.getContentType().endsWith(".png")&&
                    imagen.getContentType().endsWith(".jpeg")&&
                    imagen.getContentType().endsWith(".png")){

                return new ResponseEntity<>("Tipo de archivo no permitido", HttpStatus.FORBIDDEN);
            }
            if(imagen.getSize() > 8 * 1024 * 1024){

                return new ResponseEntity<>("El tamaño del archivo supera los 5MB", HttpStatus.FORBIDDEN);
            }
            String url = ProductoTiendaUtilidades.guardarArchivo(imagen);
            imagenes.add(url);
        }

        nuevoProductoTienda.setImagenenesUrl(imagenes);
        nuevoProductoTienda.setPrecio(precio);
        nuevoProductoTienda.setDescripcion(descripcion);
        nuevoProductoTienda.setSubCategoria(subCategoria);
        nuevoProductoTienda.setCategoriaGenero(categoriaGenero);

        productoTiendaServicio.guardarProducto(nuevoProductoTienda);

        return ResponseEntity.status(HttpStatus.CREATED).body("Se creó el nuevo producto.");
    }

    @PutMapping("/api/productoTienda")
    public ResponseEntity<Object> modificarProductoTienda(
            @RequestParam long id,
            @RequestParam String nombre,
            @RequestParam double precio,
            @RequestParam String descripcion,
            @RequestParam(required = false) String tallaSuperior,
            @RequestParam(required = false) String tallaInferior,
            @RequestParam(value = "archivo", required = false) MultipartFile[] imagenesUrl,
            @RequestParam String categoriaGenero,
            @RequestParam String subCategoria
    ) {
        ProductoTienda productoTiendaExistente = productoTiendaServicio.obtenerProductoPorId(id);

        if (productoTiendaExistente != null) {
            productoTiendaExistente.setNombre(nombre);
            productoTiendaExistente.setPrecio(precio);
            productoTiendaExistente.setDescripcion(descripcion);
            productoTiendaExistente.setTallaSuperior(toEnum(ProductoTiendaTallaSuperior.class, tallaSuperior));
            productoTiendaExistente.setTallaInferior(toEnum(ProductoTiendaTallaInferior.class, tallaInferior));

            List<String> imagenes = new ArrayList<>();
            for(MultipartFile imagen : imagenesUrl){
                if(imagen.isEmpty()){

                    return new ResponseEntity<>("El archivo está vacio", HttpStatus.FORBIDDEN);
                }
                if(imagen.getContentType().endsWith(".png")&&
                        imagen.getContentType().endsWith(".jpeg")&&
                        imagen.getContentType().endsWith(".png")){

                    return new ResponseEntity<>("Tipo de archivo no permitido", HttpStatus.FORBIDDEN);
                }
                if(imagen.getSize() > 8 * 1024 * 1024){

                    return new ResponseEntity<>("El tamaño del archivo supera los 5MB", HttpStatus.FORBIDDEN);
                }
                String url = ProductoTiendaUtilidades.guardarArchivo(imagen);
                imagenes.add(url);
            }
            productoTiendaExistente.setImagenenesUrl(imagenes);
            productoTiendaExistente.setCategoriaGenero(toEnum(ProductoTiendaCategoriaGenero.class, categoriaGenero));
            productoTiendaExistente.setSubCategoria(subCategoria);

            productoTiendaServicio.guardarProducto(productoTiendaExistente);

            return ResponseEntity.status(HttpStatus.CREATED).body("Se modificó el producto con el nombre de: " + nombre);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El producto con el nombre " + nombre + " no fue encontrado");
        }
    }

    private <T extends Enum<T>> T toEnum(Class<T> enumClass, String value) {
        if (value != null) {
            try {
                return Enum.valueOf(enumClass, value.toUpperCase());
            } catch (IllegalArgumentException e) {
                // Manejar el error si el valor proporcionado no coincide con ninguna constante del enumerado
            }
        }
        return null;
    }

    @DeleteMapping("/api/productoTienda")
    public ResponseEntity<Object> eliminarProductoTienda(@RequestParam long id) {
        ProductoTienda productoTiendaExistente = productoTiendaServicio.obtenerProductoPorId(id);

        if (productoTiendaExistente != null) {
            productoTiendaServicio.borrarProducto(productoTiendaExistente);
            return ResponseEntity.status(HttpStatus.OK).body("Se eliminó el producto con el nombre de : " + productoTiendaExistente.getNombre());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El producto con el nombre de : " + productoTiendaExistente.getNombre() + " no fue encontrado");
        }
    }


}

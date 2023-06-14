package Macart.Ecommerce.Modelos;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.*;

@Entity
public class ProductoTienda {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String nombre;
    private double precio;
    private String descripcion;
    @ElementCollection
    private Map<String, Integer> tallas =  new HashMap<>();
    @ElementCollection
    private List<String> imagenesUrl = new ArrayList<>();
    private ProductoTiendaCategoriaGenero categoriaGenero;
    private String subCategoria;
    @OneToMany(mappedBy="productoTienda", fetch= FetchType.EAGER)
    private Set<PedidoProducto> pedidoproductos = new HashSet<>();
    private boolean activo;
    private boolean unico;
    private int cantidadStockUnico;

    public ProductoTienda() {
    }

    public ProductoTienda(String nombre, double precio, String descripcion, List<String>imagenesUrl, ProductoTiendaCategoriaGenero categoriaGenero, String subCategoria, boolean activo, boolean unico) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.imagenesUrl = imagenesUrl;
        this.categoriaGenero = categoriaGenero;
        this.subCategoria = subCategoria;
        this.activo = activo;
        this.unico = unico;
    }

    /* CONSTRUCTOR PARA ACCESORIOS */
    public ProductoTienda(String nombre, double precio, String descripcion, List<String> imagenesUrl, ProductoTiendaCategoriaGenero categoriaGenero, String subCategoria, boolean activo, boolean unico, int cantidadStockUnico) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.imagenesUrl = imagenesUrl;
        this.categoriaGenero = categoriaGenero;
        this.subCategoria = subCategoria;
        this.activo = activo;
        this.unico = unico;
        this.cantidadStockUnico = cantidadStockUnico;
    }

    public ProductoTienda(String nombre) {
        this.nombre = nombre;
    }

    public void agregarPedidoProducto(PedidoProducto pedidoproducto) {
        pedidoproducto.setProductoTienda(this);
        pedidoproductos.add(pedidoproducto);
    }
    public void agregarTalla(String medida, int cantidad){
        tallas.put(medida, cantidad);
    }

    /* GETTERS */
    public long getId() {return id;}
    public String getNombre() {return nombre;}
    public double getPrecio() {return precio;}
    public String getDescripcion() {return descripcion;}
    public Map<String, Integer> getTallas() {return tallas;}
    public List<String> getImagenesUrl() {return imagenesUrl;}
    public ProductoTiendaCategoriaGenero getCategoriaGenero() {return categoriaGenero;}
    public String getSubCategoria() {return subCategoria;}
    public Set<PedidoProducto> getPedidoproductos() {return pedidoproductos;}
    public boolean isActivo() {return activo;}
    public boolean isUnico() {return unico;}
    public int getCantidadStockUnico() {return cantidadStockUnico;}

    /*SETTERS*/
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setPrecio(double precio) {this.precio = precio;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
    public void setTallas(Map<String, Integer> tallas) {this.tallas = tallas;}
    public void setImagenesUrl(List<String> imagenesUrl) {this.imagenesUrl = imagenesUrl;}
    public void setCategoriaGenero(ProductoTiendaCategoriaGenero categoriaGenero) {this.categoriaGenero = categoriaGenero;}
    public void setSubCategoria(String subCategoria) {this.subCategoria = subCategoria;}
    public void setPedidoproductos(Set<PedidoProducto> pedidoproductos) {this.pedidoproductos = pedidoproductos;}
    public void setActivo(boolean activo) {this.activo = activo;}
    public void setUnico(boolean unico) {this.unico = unico;}
    public void setCantidadStockUnico(int cantidadStockUnico) {this.cantidadStockUnico = cantidadStockUnico;}
}

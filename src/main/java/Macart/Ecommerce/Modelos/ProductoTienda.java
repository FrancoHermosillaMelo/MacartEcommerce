package Macart.Ecommerce.Modelos;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ProductoTienda {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String nombre;
    private double precio;
    private String descripcion;
    private int cantidadStock;
    private  ProductoTiendaTallaSuperior tallaSuperior;
    private ProductoTiendaTallaInferior tallaInferior;
    private String imagenUrl;
    private ProductoTiendaCategoriaGenero categoriaGenero;
    private String subCategoria;
    @OneToMany(mappedBy="productoTienda", fetch= FetchType.EAGER)
    private Set<PedidoProducto> pedidoproductos = new HashSet<>();
    @OneToMany(mappedBy="productoTienda", fetch= FetchType.EAGER)
    private Set<Promocion> promociones = new HashSet<>();

    public ProductoTienda() {
    }

    public ProductoTienda(String nombre, double precio, String descripcion, int cantidadStock, ProductoTiendaTallaSuperior tallaSuperior, ProductoTiendaTallaInferior tallaInferior, String imagenUrl, ProductoTiendaCategoriaGenero categoriaGenero, String subCategoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.cantidadStock = cantidadStock;
        this.tallaSuperior = tallaSuperior;
        this.tallaInferior = tallaInferior;
        this.imagenUrl = imagenUrl;
        this.categoriaGenero = categoriaGenero;
        this.subCategoria = subCategoria;
    }
    public void agregarPedidoProducto(PedidoProducto pedidoproducto) {
        pedidoproducto.setProductoTienda(this);
        pedidoproductos.add(pedidoproducto);
    }
    public void agregarPromocion(Promocion promocion) {
        promocion.setProductoTienda(this);
        promociones.add(promocion);
    }

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(int cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public ProductoTiendaTallaSuperior getTallaSuperior() {
        return tallaSuperior;
    }

    public void setTallaSuperior(ProductoTiendaTallaSuperior tallaSuperior) {
        this.tallaSuperior = tallaSuperior;
    }

    public ProductoTiendaTallaInferior getTallaInferior() {
        return tallaInferior;
    }

    public void setTallaInferior(ProductoTiendaTallaInferior tallaInferior) {
        this.tallaInferior = tallaInferior;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public ProductoTiendaCategoriaGenero getCategoriaGenero() {
        return categoriaGenero;
    }

    public void setCategoriaGenero(ProductoTiendaCategoriaGenero categoriaGenero) {
        this.categoriaGenero = categoriaGenero;
    }

    public String getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(String subCategoria) {
        this.subCategoria = subCategoria;
    }

    public Set<Promocion> getPromociones() {
        return promociones;
    }

    public void setPromociones(Set<Promocion> promociones) {
        this.promociones = promociones;
    }

    public Set<PedidoProducto> getPedidoproductos() {
        return pedidoproductos;
    }

    public void setPedidoproductos(Set<PedidoProducto> pedidoproductos) {
        this.pedidoproductos = pedidoproductos;
    }

}

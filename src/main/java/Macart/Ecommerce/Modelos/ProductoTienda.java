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
    private int stock;
    @ElementCollection
    private Map<String, Integer> tallaSuperior = new HashMap<>();
    @ElementCollection
    private Map<String, Integer> tallaInferior = new HashMap<>();
    @ElementCollection
    private List<String> imagenesUrl = new ArrayList<>();
    private ProductoTiendaCategoriaGenero categoriaGenero;
    private String subCategoria;
    @OneToMany(mappedBy="productoTienda", fetch= FetchType.EAGER)
    private Set<PedidoProducto> pedidoproductos = new HashSet<>();

    private boolean activo;

    public ProductoTienda() {
    }

    public ProductoTienda(String nombre, double precio, String descripcion, List<String>imagenesUrl, ProductoTiendaCategoriaGenero categoriaGenero, String subCategoria, int stock, boolean activo) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.imagenesUrl = imagenesUrl;
        this.categoriaGenero = categoriaGenero;
        this.subCategoria = subCategoria;
        this.stock = stock;
        this.activo = activo;
    }

    public ProductoTienda(String nombre) {
        this.nombre = nombre;
    }

    public void agregarPedidoProducto(PedidoProducto pedidoproducto) {
        pedidoproducto.setProductoTienda(this);
        pedidoproductos.add(pedidoproducto);
    }
    public void agregarTallaSuperior(String talla, int stock){
        tallaSuperior.put(talla, stock);
    }

    public void actualizarUnidadesDisponiblesTallaSuperior(String talla, int nuevasUnidades) {
        tallaSuperior.put(talla, nuevasUnidades);
    }

    public void agregarTallaInferior(String talla, int stock){
        tallaInferior.put(talla, stock);
    }

    public void actualizarUnidadesDisponiblesTallaInferior(String talla, int nuevasUnidades) {
        tallaInferior.put(talla, nuevasUnidades);
    }

    public long getId() {
        return id;
    }

    public int getStock() {
        return stock;
    }

    public Map<String, Integer> getTallaSuperior() {
        return tallaSuperior;
    }

    public void setTallaSuperior(Map<String, Integer> tallaSuperior) {
        this.tallaSuperior = tallaSuperior;
    }

    public Map<String, Integer> getTallaInferior() {
        return tallaInferior;
    }

    public void setTallaInferior(Map<String, Integer> tallaInferior) {
        this.tallaInferior = tallaInferior;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public List<String> getImagenesUrl() {
        return imagenesUrl;
    }

    public void setImagenesUrl(List<String> imagenesUrl) {
        this.imagenesUrl = imagenesUrl;
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

    public Set<PedidoProducto> getPedidoproductos() {
        return pedidoproductos;
    }

    public void setPedidoproductos(Set<PedidoProducto> pedidoproductos) {
        this.pedidoproductos = pedidoproductos;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}

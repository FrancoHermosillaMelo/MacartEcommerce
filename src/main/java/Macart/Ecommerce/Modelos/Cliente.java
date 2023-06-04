package Macart.Ecommerce.Modelos;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String correo;
    private String telefono;
    private String contraseña;
    private boolean verificado;
    private String tokenAutenticacion;
    @OneToMany(mappedBy="cliente", fetch= FetchType.EAGER)
    private Set<Pedido> pedidos = new HashSet<>();
    @OneToMany(mappedBy="cliente", fetch= FetchType.EAGER)
    private Set<Direccion> direcciones = new HashSet<>();
    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
    private Set<Comprobante> comprobantes = new HashSet<>();

    public Cliente() {
    }

    public Cliente(String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, String correo, String telefono, String contraseña,boolean verificado,String tokenAutenticacion) {
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.correo = correo;
        this.telefono = telefono;
        this.contraseña = contraseña;
        this.verificado = verificado;
        this.tokenAutenticacion = tokenAutenticacion;
    }
    public void agregarPedido(Pedido pedido) {
        pedido.setCliente(this);
        pedidos.add(pedido);
    }
   
    public void agregarDirecciones(Direccion direccion) {
        direccion.setCliente(this);
        direcciones.add(direccion);
    }

    public void agregarComprobantes(Comprobante comprobante){
        comprobante.setCliente(this);
        comprobantes.add(comprobante);
    }
    public long getId() {
        return id;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public Set<Direccion> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(Set<Direccion> direcciones) {
        this.direcciones = direcciones;
    }

    public Set<Comprobante> getComprobantes() {
        return comprobantes;
    }

    public void setComprobantes(Set<Comprobante> comprobantes) {
        this.comprobantes = comprobantes;
    }

    public boolean isVerificado() {
        return verificado;
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }

    public String getTokenAutenticacion() {
        return tokenAutenticacion;
    }

    public void setTokenAutenticacion(String tokenAutenticacion) {
        this.tokenAutenticacion = tokenAutenticacion;
    }
}

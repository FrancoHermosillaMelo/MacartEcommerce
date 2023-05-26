package Macart.Ecommerce.DTO;

import Macart.Ecommerce.Modelos.Cliente;
import Macart.Ecommerce.Modelos.Comprobante;
import Macart.Ecommerce.Modelos.Direccion;
import Macart.Ecommerce.Modelos.Pedido;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ClienteDTO {
    private long id;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String correo;
    private String telefono;
    private String celular;
    private Set<PedidoDTO> pedidos;
    private Set<ComprobanteDTO> comprobantes;
    private Set<DireccionDTO> direcciones;
    public ClienteDTO(Cliente cliente) {

        this.id = cliente.getId();

        this.primerNombre = cliente.getPrimerNombre();

        this.segundoNombre = cliente.getSegundoNombre();

        this.primerApellido = cliente.getPrimerApellido();

        this.segundoApellido = cliente.getSegundoApellido();

        this.correo = cliente.getCorreo();

        this.telefono = cliente.getTelefono();

        this.celular = cliente.getCelular();

        this.pedidos = cliente.getPedidos().stream().map(pedido -> new PedidoDTO(pedido)).collect(Collectors.toSet());

        this.comprobantes = cliente.getComprobantes().stream().map(comprobante -> new ComprobanteDTO(comprobante)).collect(Collectors.toSet());

        this.direcciones = cliente.getDirecciones().stream().map(direccion -> new DireccionDTO(direccion)).collect(Collectors.toSet());
    }

    public long getId() {
        return id;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCelular() {
        return celular;
    }

    public Set<PedidoDTO> getPedidos() {
        return pedidos;
    }

    public Set<ComprobanteDTO> getComprobantes() {
        return comprobantes;
    }

    public Set<DireccionDTO> getDirecciones() {
        return direcciones;
    }
}

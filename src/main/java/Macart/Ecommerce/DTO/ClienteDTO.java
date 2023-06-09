package Macart.Ecommerce.DTO;

import Macart.Ecommerce.Modelos.Cliente;
import Macart.Ecommerce.Modelos.Comprobante;

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
    private boolean verificado ;
    private String tokenAutenticacion;
    private Set<PedidoDTO> pedidos;
    private Set<DireccionDTO> direcciones;
    private Set<ComprobanteDTO> comprobantes;
    public ClienteDTO(Cliente cliente) {

        this.id = cliente.getId();
        this.primerNombre = cliente.getPrimerNombre();
        this.segundoNombre = cliente.getSegundoNombre();
        this.primerApellido = cliente.getPrimerApellido();
        this.segundoApellido = cliente.getSegundoApellido();
        this.correo = cliente.getCorreo();
        this.telefono = cliente.getTelefono();
        this.verificado = cliente.isVerificado();
        this.tokenAutenticacion = cliente.getTokenAutenticacion();
        this.pedidos = cliente.getPedidos().stream().map(pedido -> new PedidoDTO(pedido)).collect(Collectors.toSet());
        this.direcciones = cliente.getDirecciones().stream().map(direccion -> new DireccionDTO(direccion)).collect(Collectors.toSet());
        this.comprobantes = cliente.getComprobantes().stream().map(comprobante -> new ComprobanteDTO(comprobante)).collect(Collectors.toSet());
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

    public Set<PedidoDTO> getPedidos() {
        return pedidos;
    }

    public Set<DireccionDTO> getDirecciones() {
        return direcciones;
    }

    public Set<ComprobanteDTO> getComprobantes() {
        return comprobantes;
    }

    public boolean isVerificado() {
        return verificado;
    }

    public String getTokenAutenticacion() {
        return tokenAutenticacion;
    }
}

package Macart.Ecommerce.DTO;

import java.util.HashMap;
import java.util.Map;

public class CarritoDTO {
    private  long idPedido;
    private long idProducto;
    private double montoTotal;
    private Map<String, Integer> tallas =  new HashMap<>();
    public long getIdPedido() {return idPedido;}
    public long getIdProducto() {return idProducto;}
    public double getMontoTotal() {return montoTotal;}
    public Map<String, Integer> getTallas() {
        return tallas;
    }
}

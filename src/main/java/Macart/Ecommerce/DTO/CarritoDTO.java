package Macart.Ecommerce.DTO;

import java.util.HashMap;
import java.util.Map;

public class CarritoDTO {
    private  long id;
    private Map<String, Integer> tallas =  new HashMap<>();

    public CarritoDTO() {
    }

    public long getId() {
        return id;
    }

    public Map<String, Integer> getTallas() {
        return tallas;
    }
}

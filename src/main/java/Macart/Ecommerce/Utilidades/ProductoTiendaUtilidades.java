package Macart.Ecommerce.Utilidades;

import org.apache.commons.io.FileUtils;
import org.aspectj.util.FileUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public final class ProductoTiendaUtilidades {
    public static String guardarArchivo(MultipartFile archivo){
            try{
                String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename();
                String rutaDestino = "src\\main\\resources\\static\\img\\" + nombreArchivo;
                FileUtils.writeByteArrayToFile(new File(rutaDestino), archivo.getBytes());
                return "../static/img/" + nombreArchivo;

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
    }
}

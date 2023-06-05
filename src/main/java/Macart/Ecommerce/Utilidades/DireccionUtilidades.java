package Macart.Ecommerce.Utilidades;

public class DireccionUtilidades {
    public static boolean esContraseñaValida(String contraseña) {
        if (contraseña.length() < 8) {
            return false;
        }

        boolean tieneNumero = false;
        boolean tieneMayuscula = false;

        for (char c : contraseña.toCharArray()) {
            if (Character.isDigit(c)) {
                tieneNumero = true;
            } else if (Character.isUpperCase(c)) {
                tieneMayuscula = true;
            }

            if (tieneNumero && tieneMayuscula) {
                return true;
            }
        }

        return false;
    }
}

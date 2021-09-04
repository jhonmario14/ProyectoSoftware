package Modelo;

public class Usuario {
    
    private static String User;
    private static String Contrasena;
    private static String Instancia;
    private static String Host;
    private static String Puerto;
    private static String BaseDatos;

    public Usuario() {
    }
    

    public static String getUser() {
        return User;
    }

    public static void setUser(String User) {
        Usuario.User = User;
    }

    public static String getContrasena() {
        return Contrasena;
    }

    public static void setContrasena(String Contrasena) {
        Usuario.Contrasena = Contrasena;
    }

    public static String getInstancia() {
        return Instancia;
    }

    public static void setInstancia(String Instancia) {
        Usuario.Instancia = Instancia;
    }

    public static String getHost() {
        return Host;
    }

    public static void setHost(String Host) {
        Usuario.Host = Host;
    }

    public static String getPuerto() {
        return Puerto;
    }

    public static void setPuerto(String Puerto) {
        Usuario.Puerto = Puerto;
    }

    public static String getBaseDatos() {
        return BaseDatos;
    }

    public static void setBaseDatos(String BaseDatos) {
        Usuario.BaseDatos = BaseDatos;
    } 
    
}

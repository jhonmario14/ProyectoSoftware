
package Controlador;

import Modelo.Usuario;
import Vista.VentanaLogin;
import com.sun.istack.internal.logging.Logger;
import java.sql.*;
import java.util.logging.Level;
import javax.swing.JOptionPane;

public class Conexion {
    
    private static Connection connection = null;
    private static Usuario usuario_BD = new Usuario();
    VentanaLogin login = new VentanaLogin();
    
    public static Connection conectar() throws SQLException, ClassNotFoundException{
        
//        String ip = "localhost";
//        String basedatos = "oracle";
//        String usuario = "jhon";
//        String contrasena = "sistemas";
//        String puerto = "1521";
//        String instancia = "XE";
//        String url = "jdbc:"+ basedatos + ":thin:@" + ip + ":"+ puerto + ":"+ instancia + "";
//        String basedatos = "oracle";

        String usuario = usuario_BD.getUser();
        String contrasena = usuario_BD.getContrasena();
        String instancia = usuario_BD.getInstancia();
        String host = usuario_BD.getHost();
        String puerto = usuario_BD.getPuerto();
        String BD = usuario_BD.getBaseDatos();
        String url = "jdbc:"+ BD + ":thin:@" + host + ":"+ puerto + ":"+ instancia + "";

        try{
            if (Conexion.connection == null) {
                System.out.println(Conexion.connection);

                if (BD == "oracle") {
                    try {
                            Class.forName("oracle.jdbc.OracleDriver");
                        } catch (ClassNotFoundException e) {
                            JOptionPane.showMessageDialog(null, "ERROR DE CONEXION" +e.getMessage());
                        }
                        Conexion.connection = DriverManager.getConnection(url, usuario, contrasena);          
                }else if (BD == "postgresql") {
                        String urlp = "jdbc:"+ BD+ "://" + host + ":"+ puerto + "/"+ instancia + "";
//                                "jdbc:postgresql://localhost:5432/Taller4";

                        Class.forName("org.postgresql.Driver");
                        Conexion.connection = DriverManager.getConnection(urlp, usuario, contrasena); 
                }
                System.out.println("CONEXION EXITOSA");
                System.out.println(Conexion.connection);
                
                return Conexion.connection;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR DE CONEXION" +e.getMessage());
        }
        return Conexion.connection;
    }
}

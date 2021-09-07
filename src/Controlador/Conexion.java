
package Controlador;

import Modelo.Usuario;
import Vista.VentanaLogin;
import com.sun.istack.internal.logging.Logger;
import java.sql.*;
import java.util.logging.Level;
import javax.swing.JOptionPane;

public class Conexion {
    
    private static Connection connection;
    static Usuario usuario_BD = new Usuario();
    VentanaLogin login = new VentanaLogin();
    
    public static Connection conectar() throws SQLException{
        
        String usuario = usuario_BD.getUser();
        String contrasena = usuario_BD.getContrasena();
        String instancia = usuario_BD.getInstancia();
        String host = usuario_BD.getHost();
        String puerto = usuario_BD.getPuerto();
        String BD = usuario_BD.getBaseDatos();
        String url = "jdbc:oracle:thin:@" + host + ":"+ puerto + ":"+ instancia + "";
        // jdbc:oracle:thin:@localhost:1521:XE
        
        // jdbc:postgresql://localhost:5432/Taller4 
        
        if (usuario_BD.getBaseDatos() == "oracle") {
            try {
                if (Conexion.connection == null) {
                    Class.forName("oracle.jdbc.OracleDriver");
                    Conexion.connection = DriverManager.getConnection(url, usuario, contrasena); 
                    Conexion.connection.setAutoCommit(false);
                    return Conexion.connection;       
                }   
            } catch (ClassNotFoundException | SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
                Conexion.connection.close();    
            }
            return Conexion.connection; 
        } if(usuario_BD.getBaseDatos() == "postgresql"){
            try {
                if (Conexion.connection == null) {
                    String urlp = "jdbc:postgresql://" + host + ":"+ puerto + "/"+ instancia + "";
                    Conexion.connection = DriverManager.getConnection(urlp, usuario, contrasena); 
                   // Conexion.connection.setAutoCommit(false);
                    return Conexion.connection;       
                }  
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
                Conexion.connection.close();    
            }
            return Conexion.connection; 
        }
        return null;

//        try{
//            if (Conexion.connection == null) {
//                System.out.println(Conexion.connection);
//                
//                if (BD == "oracle") {
//                    try {
//                        Class.forName("oracle.jdbc.OracleDriver");
//                        } catch (ClassNotFoundException e) {
//                            JOptionPane.showMessageDialog(null, "ERROR DE CONEXION..." +e.getMessage());
//                        }
//                        Conexion.connection = DriverManager.getConnection(url, usuario, contrasena);          
//                }else if (BD == "postgresql") {
//                     try {
//                        Class.forName("org.postgresql.Driver");
//                    } catch (ClassNotFoundException ex) {
//                        System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
//                    }
//                        //Class.forName("org.postgresql.Driver");
//                    Conexion.connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Taller4", usuario, contrasena);   
//                }
//                System.out.println("CONEXION EXITOSA");
//                System.out.println(Conexion.connection);
//                
//                return Conexion.connection;
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "ERROR DE CONEXION: " +e.getMessage());
//             return Conexion.connection;
//        }
//        return Conexion.connection;
    }
    

    
    
}

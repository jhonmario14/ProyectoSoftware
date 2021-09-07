package Modelo;

import Modelo.Usuario;
import Vista.VentanaPrincipal;
import java.sql.*;
import javax.swing.*;

public class BaseDatosOracle {
    
    private static Connection connection = null;
    private static Usuario usuario_BD = new Usuario();
    
    public static Connection conectar_Oracle() throws SQLException, ClassNotFoundException{
//        String ip = "localhost";
//        String basedatos = "oracle";
//        String usuario = "jhon";
//        String contrasena = "sistemas";
//        String puerto = "1521";
//        String instancia = "XE";
//        String url = "jdbc:"+ basedatos + ":thin:@" + ip + ":"+ puerto + ":"+ instancia + "";

        String usuario = usuario_BD.getUser();
        String contrasena = usuario_BD.getContrasena();
        String instancia = usuario_BD.getInstancia();
        String host = usuario_BD.getHost();
        String puerto = usuario_BD.getPuerto();
        String basedatos = usuario_BD.getBaseDatos();
        String url = "jdbc:"+ basedatos + ":thin:@" + host + ":"+ puerto + ":"+ instancia + "";

        try{
            if (BaseDatosOracle.connection == null) {
                System.out.println(BaseDatosOracle.connection);
                
                Class.forName("oracle.jdbc.OracleDriver");
                BaseDatosOracle.connection = DriverManager.getConnection(url, usuario, contrasena);
                BaseDatosOracle.connection.setAutoCommit(false);
                return BaseDatosOracle.connection;  
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR DE CONEXION" +e.getMessage());
        }
        return BaseDatosOracle.connection;
    }
}
    



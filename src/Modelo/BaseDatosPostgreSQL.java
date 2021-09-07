package Modelo;

import Modelo.Usuario;
import Vista.VentanaPrincipal;
import java.sql.*;
import javax.swing.*;

public class BaseDatosPostgreSQL {
    
    private static Connection connection = null;
    private static Usuario usuario_BD = new Usuario();
  
    
    public static Connection conectar_PostgreSQL() throws SQLException, ClassNotFoundException {
        VentanaPrincipal vp = new VentanaPrincipal();
        
        String usuario = usuario_BD.getUser();
        String contrasena = usuario_BD.getContrasena();
        String instancia = usuario_BD.getInstancia();
        String host = usuario_BD.getHost();
        String puerto = usuario_BD.getPuerto();
        String basedatos = usuario_BD.getBaseDatos();
        String url = "jdbc:"+ basedatos + "://" + host + ":"+ puerto + "/"+ instancia + "";
        
        try{
            if (BaseDatosPostgreSQL.connection == null) {
                System.out.println(BaseDatosPostgreSQL.connection);
                
                Class.forName("org.postgresql.Driver");
                BaseDatosPostgreSQL.connection = DriverManager.getConnection(url, usuario, contrasena);
                BaseDatosPostgreSQL.connection.setAutoCommit(false);
                return BaseDatosPostgreSQL.connection;  
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR DE CONEXION" +e.getMessage());
        }
        return BaseDatosPostgreSQL.connection;
     
//        try {
//            
//            Class.forName("org.postgresql.Driver");
//            Connection conexion = DriverManager.getConnection(url, usuario, contrasenia);
//            java.sql.Statement st = conexion.createStatement();
//            String sql = "select cargo from cargos";
//            ResultSet result = st.executeQuery(sql);
//            if (result.next()) {
//                JOptionPane.showMessageDialog(null, "El usuario y la contraseña son correctos...");
//                VentanaPrincipal p = new VentanaPrincipal();
//                p.setVisible(true);
//            }else{
//                JOptionPane.showMessageDialog(null, "Usuario Invalido...");
//            }
//            result.close();
//            st.close();
//            conexion.close();
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "DATOS ERRONEOS");
//        }
//        return null;
    }
    
}

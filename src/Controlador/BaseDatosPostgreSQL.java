package Controlador;

import Vista.VentanaPrincipal;
import java.sql.*;
import javax.swing.JOptionPane;

public class BaseDatosPostgreSQL {
    public static Connection conectar_PostgreSQL(String usuario, String contrasenia) {
        String url = "jdbc:postgresql://localhost:5432/Taller4";
        try {
            Class.forName("org.postgresql.Driver");
            Connection conexion = DriverManager.getConnection(url, usuario, contrasenia);
            java.sql.Statement st = conexion.createStatement();
            String sql = "select cargo from cargos";
            ResultSet result = st.executeQuery(sql);
            if (result.next()) {
                JOptionPane.showMessageDialog(null, "El usuario y la contraseña son correctos...");
                VentanaPrincipal p = new VentanaPrincipal();
                p.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null, "Usuario Invalido...");
            }
            result.close();
            st.close();
            conexion.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "DATOS ERRONEOS");
        }
        return null;
    }
    
}

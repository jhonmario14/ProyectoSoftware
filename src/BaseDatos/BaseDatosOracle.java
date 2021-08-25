package BaseDatos;

import Vistas.VentanaPrincipal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class BaseDatosOracle {
    
    private static Connection connection = null;
    
    public static Connection conectar_Oracle() throws SQLException, ClassNotFoundException{
        String ip = "localhost";
        String basedatos = "oracle";
        String usuario = "jhon";
        String contrasena = "sistemas";
        String puerto = "1521";
        String instancia = "XE";
        String url = "jdbc:"+ basedatos + ":thin:@" + ip + ":"+ puerto + ":"+ instancia + "";

        try{
            if (BaseDatosOracle.connection == null) {
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
    



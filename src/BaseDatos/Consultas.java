package BaseDatos;

import Entidades.Persona;
import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;
import javax.swing.JOptionPane;

public class Consultas {
    
    public Collection<Persona> consultar () throws ClassNotFoundException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "select libro, titulo, autor, numpag from libro";
        //System.out.println("sql: "+sql);
        
        Vector<Persona> lista = new Vector<Persona>();
        try {
            Connection conn = BaseDatosOracle.conectar_Oracle();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                Persona p = new Persona(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
                lista.add(p);  
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null , "ERROR "+e.getMessage());
        }
        return lista;
    }   
}

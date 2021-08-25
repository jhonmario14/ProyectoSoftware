package Entidades;

import BaseDatos.BaseDatosOracle;
import Entidades.Persona;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PersonaDAO {
    
    private String mensaje = "";
    
    public String agregar(Connection con, Persona p){
//        PreparedStatement pst = null;
//        String sql = "INSERT INTO CARGOS (cod_cargo, cargo) "
//                + "VALUES(?, ?) "; 
//        try {
//            pst = con.prepareStatement(sql);
//            pst.setInt(1, p.getCod_cargo());
//            pst.setString(1, p.getCargo());
//            JOptionPane.showMessageDialog(null, "AGREGADO CORRECTAMENTE");
//            pst.execute();
//            pst.close();
//
//        } catch (Exception e) {
//           JOptionPane.showMessageDialog(null, "NO SE PUDO AGREGAR CORRECTAMENTE "+e.getMessage());
//        }
        return mensaje;
    }
    public String modificar(Connection con, Persona p){
        return mensaje;
    }
    public String eliminar(Connection con, int cod_cargo){
        return mensaje;
    }
    public DefaultTableModel listar(JTable tabla){
        
       String [] nombre_columnas = {"LIBRO", "TITULO", "AUTOR", "NUMPAG"};
       String [] registros = new String[4];
       
       DefaultTableModel modelo = new DefaultTableModel(null, nombre_columnas);
       String sql = "select * from libro";

       PreparedStatement pst = null;
       ResultSet rs = null;
       
        try {
            
            Connection conn = BaseDatosOracle.conectar_Oracle();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {   
                registros[0] = rs.getString("libro");
                registros[1] = rs.getString("titulo");
                registros[2] = rs.getString("autor");
                registros[3] = rs.getString("numpag");
                modelo.addRow(registros);    
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error");
        }
        finally{
            try {
                if (rs!=null) rs.close();
                if (pst!=null) pst.close(); 
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        return modelo;
    }
}

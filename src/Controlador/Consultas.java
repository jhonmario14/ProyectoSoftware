package Controlador;

import Modelo.Usuario;
import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class Consultas {
    
    public Collection<Usuario> consultar () throws ClassNotFoundException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "select libro, titulo, autor, numpag from libro";
        //System.out.println("sql: "+sql);
        
        Vector<Usuario> lista = new Vector<Usuario>();
        try {
            Connection conn = BaseDatosOracle.conectar_Oracle();
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
//                Usuario p = new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
//                lista.add(p);  
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null , "ERROR "+e.getMessage());
        }
        return lista;
    }
    
    public static void ListarDatosTabla(JTable tabla, String sql, JTextArea textError) throws SQLException, ClassNotFoundException{
        
        DefaultTableModel model;
        Statement st = null;
        ResultSet rs = null;
        String[] filas;
        String[] columnas;
        Connection conn = BaseDatosOracle.conectar_Oracle();
            
        try {
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            
            int Num_columnas = rs.getMetaData().getColumnCount();
            System.out.println("COLUMNAS "+Num_columnas);
            
            filas = new String[Num_columnas];
            columnas = new String[Num_columnas];
            
            for (int i = 0; i < Num_columnas; i++) {
                columnas[i] = rs.getMetaData().getColumnName(i+1);
                System.out.println(" "+columnas[i]);           
            }
            
            model = new DefaultTableModel(null, columnas);
            
            while (rs.next()) {                
                for (int i = 1; i < Num_columnas; i++) {
                    filas[i-1] = rs.getString(i);
                }
                model.addRow(filas);
            }
            
            tabla.setModel(model);
            textError.setText("");
        } catch (Exception e) {
            textError.setText(e.getMessage());
        }
    }
}

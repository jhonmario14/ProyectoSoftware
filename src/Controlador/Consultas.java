package Controlador;

import Modelo.Usuario;
import Vista.VentanaLogin;
import java.awt.List;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class Consultas{
    
    private VentanaLogin vista;
    private Usuario user;
  
    public static ArrayList<String> consultar(String consulta) throws ClassNotFoundException{
        
        ArrayList<String> lista = new ArrayList<String>();
        try {
            Connection conex = BaseDatosOracle.conectar_Oracle();
            Statement st = conex.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            ResultSetMetaData result = rs.getMetaData();
            int colum = result.getColumnCount();
            while (rs.next()) {                
                lista.add(String.valueOf(rs.getObject(1)));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
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

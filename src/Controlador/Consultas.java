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

public class Consultas {
    
    private VentanaLogin vista;
    private Usuario user;

    //Listas diccionario Oracle
    public static ArrayList<String> Tablas_Oracle = consultar("SELECT TABLE_NAME AS RESULTADO FROM USER_TABLES ORDER BY RESULTADO ASC");
    public static ArrayList<String> Funciones_Oracle = consultar("SELECT OBJECT_NAME AS RESULTADO FROM USER_PROCEDURES WHERE OBJECT_TYPE = 'FUNCTION' ORDER BY OBJECT_NAME ASC");
    public static ArrayList<String> Paquetes_Oracle = consultar("SELECT OBJECT_NAME AS RESULTADO FROM USER_PROCEDURES WHERE OBJECT_TYPE = 'PACKAGE' GROUP BY OBJECT_NAME");
    public static ArrayList<String> Procedimientos_Oracle = consultar("SELECT OBJECT_NAME AS RESULTADO FROM USER_PROCEDURES WHERE OBJECT_TYPE = 'PROCEDURE' ORDER BY OBJECT_NAME ASC");
    public static ArrayList<String> Triggers_Oracle = consultar("SELECT TRIGGER_NAME AS RESULTADO FROM USER_TRIGGERS ORDER BY TRIGGER_NAME ASC");
    public static ArrayList<String> Vistas_Oracle = consultar("SELECT VIEW_NAME AS RESULTADO FROM USER_VIEWS ORDER BY VIEW_NAME ASC");
    public static ArrayList<String> Usuarios_Oracle = consultar("SELECT USERNAME AS RESULTADO FROM USER_USERS");
    public static ArrayList<String> Indices_Oracle = consultar("SELECT INDEX_NAME AS RESULTADO FROM USER_INDEXES ORDER BY INDEX_NAME ASC");
    public static ArrayList<String> Roles_Oracle = consultar("SELECT GRANTED_ROLE AS RESULTADO FROM USER_ROLE_PRIVS");
    
    //Listas diccionario PostgreSQL
    public static ArrayList<String> Tablas_PostgreSQL = consultar("SELECT TABLENAME FROM PG_TABLES WHERE SCHEMANAME = 'public'"); 
    public static ArrayList<String> Funciones_PostgreSQL = consultar("SELECT P.PRONAME AS SPECIFIC_NAME, CASE P.PROKIND WHEN 'f' THEN 'FUNCTION' END AS KIND FROM PG_PROC P LEFT JOIN PG_NAMESPACE N ON P.PRONAMESPACE = N.OID WHERE  N.NSPNAME NOT IN ('pg_catalog', 'information_schema') AND P.PROKIND = 'f' ORDER BY SPECIFIC_NAME;");
    public static ArrayList<String> Paquetes_PostgreSQL = consultar("");
    public static ArrayList<String> Procedimientos_PostgreSQL = consultar("SELECT  P.PRONAME AS SPECIFIC_NAME FROM PG_PROC P WHERE  P.PROKIND = 'p' ORDER BY SPECIFIC_NAME"); 
    public static ArrayList<String> Triggers_PostgreSQL = consultar("SELECT TRIGGER_NAME FROM INFORMATION_SCHEMA.TRIGGERS"); 
    public static ArrayList<String> Vistas_PostgreSQL = consultar("select table_name as view_name from information_schema.views here table_schema not in ('information_schema', 'pg_catalog')"); 
    public static ArrayList<String> Usuarios_PostgreSQL = consultar("SELECT USENAME AS USERNAME FROM PG_SHADOW ORDER BY USENAME"); 
    public static ArrayList<String> Indices_PostgreSQL = consultar("SELECT INDEXNAME FROM PG_INDEXES WHERE SCHEMANAME = 'public'");
    public static ArrayList<String> Roles_OPostgreSQL = consultar("SELECT ROLNAME FROM PG_ROLES");   
        

    public static ArrayList<String> consultar(String consulta){
        
        ArrayList<String> lista = new ArrayList<String>();
        try {
            //Connection conex = BaseDatosOracle.conectar_Oracle();
            Connection conex = Conexion.conectar();
            Statement st = conex.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            ResultSetMetaData result = rs.getMetaData();
            int col = result.getColumnCount();
            while (rs.next()) {                
                lista.add(String.valueOf(rs.getObject(1)));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return lista;
    }
    
    public static void ListarDatosTabla(String consulta, JTable tabla , JTextArea textError) throws SQLException, ClassNotFoundException{
        
        DefaultTableModel model;
        Statement st = null;
        ResultSet rs = null;
        String[] filas;
        String[] columnas;
        Connection conn = Conexion.conectar();
        // Connection conn = BaseDatosOracle.conectar_Oracle();
            
        try {
            st = conn.createStatement();
            rs = st.executeQuery(consulta);
            
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

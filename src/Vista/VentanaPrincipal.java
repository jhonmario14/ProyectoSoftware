package Vista;

import Modelo.BaseDatosOracle;
import Controlador.Consultas;
import Modelo.Usuario;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;

public class VentanaPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form Principal
     */
    private Consultas cons = new Consultas();
    Usuario usu = new Usuario();
   
    
            
    public VentanaPrincipal() throws ClassNotFoundException {
        initComponents();
        cargar_Arbol();
    }
    
    static DefaultMutableTreeNode treeNode1;
    static DefaultMutableTreeNode treeNode2;
    static DefaultMutableTreeNode treeNode3;
    
    ArrayList<String> consulta_tablas;
    ArrayList<String> consulta_funciones;
    ArrayList<String> consulta_paquetes;
    ArrayList<String> consulta_procedimientos;
    ArrayList<String> consulta_triggers;
    ArrayList<String> consulta_vistas;
    ArrayList<String> consulta_usuarios;
    ArrayList<String> consulta_indices;
    ArrayList<String> consulta_roles;
    
    public void consulta_diccionario() throws ClassNotFoundException{
        
       if (Usuario.getBaseDatos()== "oracle") {
        consulta_tablas = Consultas.consultar("SELECT TABLE_NAME AS RESULTADO FROM USER_TABLES ORDER BY RESULTADO ASC");
        consulta_funciones = Consultas.consultar("SELECT OBJECT_NAME AS RESULTADO FROM USER_PROCEDURES WHERE OBJECT_TYPE = 'FUNCTION' ORDER BY OBJECT_NAME ASC");
        consulta_paquetes = Consultas.consultar("SELECT OBJECT_NAME AS RESULTADO FROM USER_PROCEDURES WHERE OBJECT_TYPE = 'PACKAGE' GROUP BY OBJECT_NAME");
        consulta_procedimientos = Consultas.consultar("SELECT OBJECT_NAME AS RESULTADO FROM USER_PROCEDURES WHERE OBJECT_TYPE = 'PROCEDURE' ORDER BY OBJECT_NAME ASC");
        consulta_triggers = Consultas.consultar("SELECT TRIGGER_NAME AS RESULTADO FROM USER_TRIGGERS ORDER BY TRIGGER_NAME ASC");
        consulta_vistas = Consultas.consultar("SELECT VIEW_NAME AS RESULTADO FROM USER_VIEWS ORDER BY VIEW_NAME ASC");
        consulta_usuarios = Consultas.consultar("SELECT USERNAME AS RESULTADO FROM USER_USERS");
        consulta_indices = Consultas.consultar("SELECT INDEX_NAME AS RESULTADO FROM USER_INDEXES ORDER BY INDEX_NAME ASC");
        consulta_roles = Consultas.consultar("SELECT GRANTED_ROLE AS RESULTADO FROM USER_ROLE_PRIVS");
        }if(usu.getBaseDatos()== "postgresql"){
            System.out.println("HOLA ENTRÉ A POSTGRES");
            //System.out.println("HOLA..."+Usuario.getBaseDatos());
            consulta_tablas = Consultas.consultar("SELECT TABLENAME FROM PG_TABLES WHERE SCHEMANAME = 'public'");  //OK
            consulta_funciones = Consultas.consultar("SELECT P.PRONAME AS SPECIFIC_NAME, CASE P.PROKIND WHEN 'f' THEN 'FUNCTION' END AS KIND FROM PG_PROC P LEFT JOIN PG_NAMESPACE N ON P.PRONAMESPACE = N.OID WHERE  N.NSPNAME NOT IN ('pg_catalog', 'information_schema') AND P.PROKIND = 'f' ORDER BY SPECIFIC_NAME;"); // OK
            consulta_paquetes = Consultas.consultar("");
            consulta_procedimientos = Consultas.consultar("SELECT  P.PRONAME AS SPECIFIC_NAME FROM PG_PROC P WHERE  P.PROKIND = 'p' ORDER BY SPECIFIC_NAME"); // OK
            consulta_triggers = Consultas.consultar("SELECT TRIGGER_NAME FROM INFORMATION_SCHEMA.TRIGGERS"); // OK
            consulta_vistas = Consultas.consultar("select table_name as view_name from information_schema.views here table_schema not in ('information_schema', 'pg_catalog')"); //OK
            consulta_usuarios = Consultas.consultar("SELECT USENAME AS USERNAME FROM PG_SHADOW ORDER BY USENAME"); // OK
            consulta_indices = Consultas.consultar("SELECT INDEXNAME FROM PG_INDEXES WHERE SCHEMANAME = 'public'");// OK
            consulta_roles = Consultas.consultar("SELECT ROLNAME FROM PG_ROLES");   
        }
    } 
   
    private void cargar_Arbol() throws ClassNotFoundException{
        consulta_diccionario();
        treeNode1 = new DefaultMutableTreeNode(usu.getBaseDatos()+ " - BD ");
        
        treeNode2 = new DefaultMutableTreeNode("Tablas");
        for (int i = 0; i < consulta_tablas.size(); i++) {
            treeNode3 = new DefaultMutableTreeNode(consulta_tablas.get(i));
            treeNode2.add(treeNode3);
            treeNode1.add(treeNode2); 
        }
        treeNode3 = new DefaultMutableTreeNode();
        
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Funciones");
        for (int i = 0; i < consulta_funciones.size(); i++) {
            treeNode3 = new javax.swing.tree.DefaultMutableTreeNode(consulta_funciones.get(i));
            treeNode2.add(treeNode3);
            treeNode1.add(treeNode2); 
        }
        
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Paquetes");
        for (int i = 0; i < consulta_paquetes.size(); i++) {
            treeNode3 = new javax.swing.tree.DefaultMutableTreeNode(consulta_paquetes.get(i));
            treeNode2.add(treeNode3);
            treeNode1.add(treeNode2); 
        }
        
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Procedimientos");
        for (int i = 0; i < consulta_procedimientos.size(); i++) {
            treeNode3 = new javax.swing.tree.DefaultMutableTreeNode(consulta_procedimientos.get(i));
            treeNode2.add(treeNode3);
            treeNode1.add(treeNode2); 
        }
        
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Triggers");
        for (int i = 0; i < consulta_triggers.size(); i++) {
            treeNode3 = new javax.swing.tree.DefaultMutableTreeNode(consulta_triggers.get(i));
            treeNode2.add(treeNode3);
            treeNode1.add(treeNode2); 
        }
        
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Vistas");
        for (int i = 0; i < consulta_vistas.size(); i++) {
            treeNode3 = new javax.swing.tree.DefaultMutableTreeNode(consulta_vistas.get(i));
            treeNode2.add(treeNode3);
            treeNode1.add(treeNode2); 
        }
        
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Roles");
        for (int i = 0; i < consulta_roles.size(); i++) {
            treeNode3 = new javax.swing.tree.DefaultMutableTreeNode(consulta_roles.get(i));
            treeNode2.add(treeNode3);
            treeNode1.add(treeNode2); 
        }
        
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Usuarios");
        for (int i = 0; i < consulta_usuarios.size(); i++) {
            treeNode3 = new javax.swing.tree.DefaultMutableTreeNode(consulta_usuarios.get(i));
            treeNode2.add(treeNode3);
            treeNode1.add(treeNode2); 
        }
        
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Indices");
        for (int i = 0; i < consulta_indices.size(); i++) {
            treeNode3 = new javax.swing.tree.DefaultMutableTreeNode(consulta_indices.get(i));
            treeNode2.add(treeNode3);
            treeNode1.add(treeNode2); 
        }
        
        Arbol_Jtree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        Arbol_JScroll.setViewportView(Arbol_Jtree);  
    }
    
    private String abrir_Archivo(){
        
        String aux = "";
        String texto = "";
        try {
            JFileChooser archivo = new JFileChooser();
            archivo.setDialogTitle("Abrir");
            FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivo SQL [.sql]", "sql");
            archivo.setFileFilter(filtro);
            archivo.showOpenDialog(archivo);
            File abre = archivo.getSelectedFile();
            if (abre != null) {
                FileReader archivos = new FileReader(abre);
                BufferedReader lee = new BufferedReader(archivos);
                while ((aux = lee.readLine()) != null) {                    
                    texto += aux;
                }
                jTextArea2.append(texto);
                lee.close();
                
            }
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(rootPane, e +""+ "\nNo se ha encontrado el archivo", "ADVERTENCIA", 0);
        }
        return texto;
    }
    
    private void guardar_archivo(JTextArea Jarea){
        try {
            String nombre = "";
            JFileChooser archivo = new JFileChooser();
            archivo.setDialogTitle("Guardar");
            FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivo SQL [.sql]", "sql");
            archivo.setFileFilter(filtro);
            archivo.showOpenDialog(archivo);
            File guarda = archivo.getSelectedFile();
            if (guarda.exists()) {
                
                
            }
        } catch (Exception e) {
        }
        
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem2 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem3 = new javax.swing.JRadioButtonMenuItem();
        jMenu1 = new javax.swing.JMenu();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jMenuItem2 = new javax.swing.JMenuItem();
        jRadioButtonMenuItem4 = new javax.swing.JRadioButtonMenuItem();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jRadioButtonMenuItem5 = new javax.swing.JRadioButtonMenuItem();
        jDialog1 = new javax.swing.JDialog();
        jDialog2 = new javax.swing.JDialog();
        jFrame1 = new javax.swing.JFrame();
        menuBar1 = new java.awt.MenuBar();
        menu1 = new java.awt.Menu();
        menu2 = new java.awt.Menu();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jRadioButtonMenuItem6 = new javax.swing.JRadioButtonMenuItem();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jCheckBoxMenuItem2 = new javax.swing.JCheckBoxMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jDialog3 = new javax.swing.JDialog();
        jFrame2 = new javax.swing.JFrame();
        jMenuItem3 = new javax.swing.JMenuItem();
        jCheckBoxMenuItem3 = new javax.swing.JCheckBoxMenuItem();
        jRadioButtonMenuItem7 = new javax.swing.JRadioButtonMenuItem();
        buttonGroup4 = new javax.swing.ButtonGroup();
        jFrame3 = new javax.swing.JFrame();
        jFrame4 = new javax.swing.JFrame();
        jMenu3 = new javax.swing.JMenu();
        jFrame5 = new javax.swing.JFrame();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        jPopupMenu3 = new javax.swing.JPopupMenu();
        jCheckBoxMenuItem4 = new javax.swing.JCheckBoxMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        btnListar = new javax.swing.JButton();
        btn_ListarPostgres = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbResultados = new javax.swing.JTable();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu7 = new javax.swing.JMenu();
        jMenu8 = new javax.swing.JMenu();
        jMenu9 = new javax.swing.JMenu();
        jMenu10 = new javax.swing.JMenu();
        Arbol_JScroll = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        Arbol_Jtree = new javax.swing.JTree();
        btn_CrearUsuario = new javax.swing.JButton();
        btn_CambiarBD = new javax.swing.JButton();
        btn_AbrirArchivo = new javax.swing.JButton();
        btn_GuardarArchivo = new javax.swing.JButton();
        btn_EjecutarCodigo = new javax.swing.JButton();
        btn_Refrescar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        jMenuItem1.setText("jMenuItem1");

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");

        jRadioButtonMenuItem2.setSelected(true);
        jRadioButtonMenuItem2.setText("jRadioButtonMenuItem2");

        jRadioButtonMenuItem3.setSelected(true);
        jRadioButtonMenuItem3.setText("jRadioButtonMenuItem3");

        jMenu1.setText("jMenu1");

        jMenuItem2.setText("jMenuItem2");

        jRadioButtonMenuItem4.setSelected(true);
        jRadioButtonMenuItem4.setText("jRadioButtonMenuItem4");

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        jMenu4.setText("jMenu4");

        jRadioButtonMenuItem5.setSelected(true);
        jRadioButtonMenuItem5.setText("jRadioButtonMenuItem5");

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
        jDialog2.getContentPane().setLayout(jDialog2Layout);
        jDialog2Layout.setHorizontalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog2Layout.setVerticalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        menu1.setLabel("File");
        menuBar1.add(menu1);

        menu2.setLabel("Edit");
        menuBar1.add(menu2);

        jRadioButtonMenuItem6.setSelected(true);
        jRadioButtonMenuItem6.setText("jRadioButtonMenuItem6");

        jCheckBoxMenuItem2.setSelected(true);
        jCheckBoxMenuItem2.setText("jCheckBoxMenuItem2");

        jMenu2.setText("jMenu2");

        javax.swing.GroupLayout jDialog3Layout = new javax.swing.GroupLayout(jDialog3.getContentPane());
        jDialog3.getContentPane().setLayout(jDialog3Layout);
        jDialog3Layout.setHorizontalGroup(
            jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog3Layout.setVerticalGroup(
            jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jMenuItem3.setText("jMenuItem3");

        jCheckBoxMenuItem3.setSelected(true);
        jCheckBoxMenuItem3.setText("jCheckBoxMenuItem3");

        jRadioButtonMenuItem7.setSelected(true);
        jRadioButtonMenuItem7.setText("jRadioButtonMenuItem7");

        javax.swing.GroupLayout jFrame3Layout = new javax.swing.GroupLayout(jFrame3.getContentPane());
        jFrame3.getContentPane().setLayout(jFrame3Layout);
        jFrame3Layout.setHorizontalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame3Layout.setVerticalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame4Layout = new javax.swing.GroupLayout(jFrame4.getContentPane());
        jFrame4.getContentPane().setLayout(jFrame4Layout);
        jFrame4Layout.setHorizontalGroup(
            jFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame4Layout.setVerticalGroup(
            jFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jMenu3.setText("jMenu3");

        javax.swing.GroupLayout jFrame5Layout = new javax.swing.GroupLayout(jFrame5.getContentPane());
        jFrame5.getContentPane().setLayout(jFrame5Layout);
        jFrame5Layout.setHorizontalGroup(
            jFrame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame5Layout.setVerticalGroup(
            jFrame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jCheckBoxMenuItem4.setSelected(true);
        jCheckBoxMenuItem4.setText("jCheckBoxMenuItem4");

        jMenuItem4.setText("jMenuItem4");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jInternalFrame1.setVisible(true);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane4.setViewportView(jTextArea2);

        btnListar.setText("Listar");
        btnListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarActionPerformed(evt);
            }
        });

        btn_ListarPostgres.setText("ListarPostgres");
        btn_ListarPostgres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ListarPostgresActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Resultados"));

        tbResultados.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tbResultados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tbResultados);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(74, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 754, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenu7.setText("SQL");
        jMenuBar2.add(jMenu7);

        jMenu8.setText("PLSQL");
        jMenuBar2.add(jMenu8);

        jMenu9.setText("TQSQL");
        jMenuBar2.add(jMenu9);

        jMenu10.setText("DDL");
        jMenuBar2.add(jMenu10);

        jInternalFrame1.setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_ListarPostgres)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnListar))
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4)))
                .addContainerGap())
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnListar)
                    .addComponent(btn_ListarPostgres))
                .addContainerGap(71, Short.MAX_VALUE))
        );

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Base de Datos");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Tablas");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Funciones");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Paquetes");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Procedimientos");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Triggers");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Vistas");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Roles");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Usuarios");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Indices");
        treeNode1.add(treeNode2);
        Arbol_Jtree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        Arbol_Jtree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Arbol_JtreeMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Arbol_Jtree);

        Arbol_JScroll.setViewportView(jScrollPane2);

        btn_CrearUsuario.setText("Crear Usuario");

        btn_CambiarBD.setText("Cambiar BD");

        btn_AbrirArchivo.setText("Abrir");
        btn_AbrirArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AbrirArchivoActionPerformed(evt);
            }
        });

        btn_GuardarArchivo.setText("Guardar");

        btn_EjecutarCodigo.setText("Ejecutar");

        btn_Refrescar.setText("Refrescar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_CrearUsuario)
                        .addGap(18, 18, 18)
                        .addComponent(btn_CambiarBD))
                    .addComponent(Arbol_JScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(btn_AbrirArchivo)
                        .addGap(18, 18, 18)
                        .addComponent(btn_GuardarArchivo)
                        .addGap(18, 18, 18)
                        .addComponent(btn_EjecutarCodigo)
                        .addGap(228, 228, 228)
                        .addComponent(btn_Refrescar)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                        .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_CrearUsuario)
                            .addComponent(btn_CambiarBD))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Arbol_JScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_AbrirArchivo)
                            .addComponent(btn_GuardarArchivo)
                            .addComponent(btn_EjecutarCodigo)
                            .addComponent(btn_Refrescar))
                        .addGap(9, 9, 9)
                        .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("mensajes de error...");
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarActionPerformed
     
      /*  try {
            cons.ListarDatosTabla(tbResultados, "select * from cargos", jTextArea1);
        } catch (SQLException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
    }//GEN-LAST:event_btnListarActionPerformed

    private void btn_ListarPostgresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ListarPostgresActionPerformed
       /* try {
            cons.ListarDatosTabla(tbResultados, "select * from cargos", jTextArea1);

        } catch (SQLException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
    }//GEN-LAST:event_btn_ListarPostgresActionPerformed

    private void btn_AbrirArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AbrirArchivoActionPerformed
        abrir_Archivo();
    }//GEN-LAST:event_btn_AbrirArchivoActionPerformed

    private void Arbol_JtreeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Arbol_JtreeMouseClicked
        try {
            if (evt.getClickCount() == 2) {
                DefaultMutableTreeNode selec = (DefaultMutableTreeNode) Arbol_Jtree.getSelectionPath().getLastPathComponent();
                System.out.println(selec.getUserObject().toString());
                String consulta = "" + selec.getUserObject().toString();
                Consultas.ListarDatosTabla(consulta, tbResultados, jTextArea1);
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_Arbol_JtreeMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new VentanaPrincipal().setVisible(true);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane Arbol_JScroll;
    private javax.swing.JTree Arbol_Jtree;
    private javax.swing.JButton btnListar;
    private javax.swing.JButton btn_AbrirArchivo;
    private javax.swing.JButton btn_CambiarBD;
    private javax.swing.JButton btn_CrearUsuario;
    private javax.swing.JButton btn_EjecutarCodigo;
    private javax.swing.JButton btn_GuardarArchivo;
    private javax.swing.JButton btn_ListarPostgres;
    private javax.swing.JButton btn_Refrescar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem2;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem3;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem4;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JDialog jDialog3;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JFrame jFrame3;
    private javax.swing.JFrame jFrame4;
    private javax.swing.JFrame jFrame5;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JPopupMenu jPopupMenu3;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem2;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem3;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem4;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem5;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem6;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private java.awt.Menu menu1;
    private java.awt.Menu menu2;
    private java.awt.MenuBar menuBar1;
    private javax.swing.JTable tbResultados;
    // End of variables declaration//GEN-END:variables

    private DefaultMutableTreeNode DefaultMutableTreeNode(String bd_oracle) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

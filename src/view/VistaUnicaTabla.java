/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.Controller;
import java.awt.Font;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import model.Libro;
import persistence.BaseDatos;
import persistence.LibrosNegocio;

/**
 *
 * @author jtech
 */
public class VistaUnicaTabla extends PantallaOpcion{
    
       private ModeloDatos modeloDatos;
       private JTable jTable;
       private ListSelectionModel listSelectionModel;
       private JLabel jLabelCriterioOrdenacion;
       private JRadioButton jRadioButtonIdLibro;
       private JRadioButton jRadioButtonTitulo;
       private JRadioButton jRadioButtonGenero;
       private ButtonGroup buttonGroupCriteriosOrdenacion;
       private JButton jButtonInsertarFila;
       private JButton jButtonCancelarInsercionFila;
       private JButton jButtonGuardarFilaInsertada;
       private JButton jButtonEliminarFilaSeleccionada;
       private JLabel jLabelInformaFilaInsertada;
       private JLabel jLabelInformaGuardarFilaInsertada;
       private int filaAInsertarJTable = -1;

    public VistaUnicaTabla() {
        
        componentesJPanel[2] = -1;   //  Fila seleccionada en JTable
        componentesJPanel[10] = 1;   //  El valor registrado determina el criterio de ordenación de las filas del JTable. 
        componentesJPanel[13] = 1;   //  Fila de celda actualizada en JTable. 
        componentesJPanel[14] = 1;   //  Columna de celda actualizada en JTable.
        ubicarComponentes();
    }
    
        private void ubicarComponentes() {
            
        setLayout(null);  
        
        jLabelCriterioOrdenacion = new JLabel("CRITERIO ORDENACION :");
        jLabelCriterioOrdenacion.setBounds(100, 550, 150, 25);
        add(jLabelCriterioOrdenacion);

        jRadioButtonIdLibro = new JRadioButton("identificador libro", true);
        jRadioButtonIdLibro.setBounds(100, 590, 150, 25);
        add(jRadioButtonIdLibro);
        jRadioButtonIdLibro.setActionCommand("ordenarPorIdentificador");   
        
        jRadioButtonTitulo = new JRadioButton("título", false);
        jRadioButtonTitulo.setBounds(100, 630, 150, 25);
        add(jRadioButtonTitulo);
        jRadioButtonTitulo.setActionCommand("ordenarPorTitulo");     
        
        jRadioButtonGenero = new JRadioButton("género", false);
        jRadioButtonGenero.setBounds(100, 670, 150, 25);
        add(jRadioButtonGenero);
        jRadioButtonGenero.setActionCommand("ordenarPorGenero");  
        
        buttonGroupCriteriosOrdenacion = new ButtonGroup();
        buttonGroupCriteriosOrdenacion.add(jRadioButtonIdLibro);
        buttonGroupCriteriosOrdenacion.add(jRadioButtonTitulo);
        buttonGroupCriteriosOrdenacion.add(jRadioButtonGenero);      
        
        jButtonInsertarFila = new JButton("Insertar fila al final");
        jButtonInsertarFila.setBounds(400,510,200,40);
        jButtonInsertarFila.setActionCommand("insertarFila");
        add(jButtonInsertarFila);      
        componentesJPanel[3] = jButtonInsertarFila;
        
        jButtonCancelarInsercionFila = new JButton("Cancelar inserción fila");
        jButtonCancelarInsercionFila.setBounds(400,580,200,40);
        jButtonCancelarInsercionFila.setActionCommand("cancelarInsercionFila");
        add(jButtonCancelarInsercionFila);        
        componentesJPanel[4] = jButtonCancelarInsercionFila;
        
        jButtonGuardarFilaInsertada = new JButton("Guardar fila insertada");
        jButtonGuardarFilaInsertada.setBounds(400,650,200,40);
        jButtonGuardarFilaInsertada.setActionCommand("guardarFilaInsertada");
        add(jButtonGuardarFilaInsertada);          
        componentesJPanel[5] = jButtonGuardarFilaInsertada; 
        
        jButtonEliminarFilaSeleccionada = new JButton("Eliminar fila seleccionada");
        jButtonEliminarFilaSeleccionada.setBounds(400,720,200,40);
        jButtonEliminarFilaSeleccionada.setActionCommand("eliminarFilaSeleccionada");
        add(jButtonEliminarFilaSeleccionada);         
        componentesJPanel[6] = jButtonEliminarFilaSeleccionada; 
        
        jLabelInformaFilaInsertada = new JLabel();
        jLabelInformaFilaInsertada.setBounds(650,510,200,40);
        add(jLabelInformaFilaInsertada);            
        jLabelInformaFilaInsertada.setText("                                     ");
        
        jLabelInformaGuardarFilaInsertada = new JLabel();
        jLabelInformaGuardarFilaInsertada.setBounds(650,650,500,40);
        add(jLabelInformaGuardarFilaInsertada);             
        jLabelInformaGuardarFilaInsertada.setText("                                                                              ");         
    } 
       
       

    @Override
    public void inicializarPostInstanciar(Controller controller) throws Exception {
        
        this.controller = controller;
        modeloDatos = new ModeloDatos(controller, new LibrosNegocio().consultarNumeroFilas((BaseDatos)controller.getRepositorio()[0]));
        componentesJPanel[15] = modeloDatos;
               
        jTable = new JTable(modeloDatos);
        JScrollPane jScrollPaneTabla = new JScrollPane(jTable);
        jScrollPaneTabla.setBounds(20,20,1140,450);
        add(jScrollPaneTabla); 
        componentesJPanel[0] = jTable;
        
        jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listSelectionModel = jTable.getSelectionModel();
        listSelectionModel.addListSelectionListener(controller);        
        componentesJPanel[1] = listSelectionModel;
        
        TableColumn columna[] = new TableColumn[6];
        columna[0] = jTable.getColumnModel().getColumn(0);
        columna[0].setPreferredWidth(30);

        columna[1] = jTable.getColumnModel().getColumn(1);
        columna[1].setPreferredWidth(450);
      
        columna[2] = jTable.getColumnModel().getColumn(2);
        columna[2].setPreferredWidth(180);
        JComboBox jComboBoxGenero = new JComboBox(new LibrosNegocio().consultarGeneros((BaseDatos)controller.getRepositorio()[0]));           
        jComboBoxGenero.setFont(new Font("TimesRoman", Font.BOLD, 10));
        columna[2].setCellEditor(new DefaultCellEditor(jComboBoxGenero));
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Click para seleccionar genero");
        columna[2].setCellRenderer(renderer);          

        columna[3] = jTable.getColumnModel().getColumn(3);
        columna[3].setPreferredWidth(40);
        
        columna[4] = jTable.getColumnModel().getColumn(4);
        columna[4].setPreferredWidth(45);

        columna[5] = jTable.getColumnModel().getColumn(5);
        columna[5].setPreferredWidth(35);
          
        jRadioButtonIdLibro.addActionListener(controller);        
        jRadioButtonTitulo.addActionListener(controller);
        jRadioButtonGenero.addActionListener(controller);          
        jButtonInsertarFila.addActionListener(controller);           
        jButtonCancelarInsercionFila.addActionListener(controller);          
        jButtonGuardarFilaInsertada.addActionListener(controller);
        jButtonEliminarFilaSeleccionada.addActionListener(controller);
    }
    
    
       @Override
    public void inicializarPantalla() throws Exception {      
       activarDesactivarJButtonInsercionFila(new boolean[]{true, false, false, true}); 
       modeloDatos.cargar(new LibrosNegocio().consultarTodos((BaseDatos)controller.getRepositorio()[0], ((Integer)componentesJPanel[10]), null, null));   
    }    

   
    private void activarDesactivarJButtonInsercionFila(boolean[] valoresActivacionDesactivacion) {
        for (int i=3; i<=6; i++)
            ((JButton)componentesJPanel[i]).setEnabled(valoresActivacionDesactivacion[i-3]); 
    } 

    @Override
    public void responderAController(String actionCommand) throws Exception {
        
        Libro libro;
        
        switch(actionCommand)
        {   
             case "reordenar" :       
                    inicializarPantalla();
                    break;     
             case "insertarFila" :
                    modeloDatos.insertarFila();
                    listSelectionModel.setSelectionInterval((modeloDatos.getDatos().length)-1, (modeloDatos.getDatos().length)-1);
                    componentesJPanel[2] = (modeloDatos.getDatos().length)-1;   // Modifica valor de fila seleccionada en JTable 
                    filaAInsertarJTable = (modeloDatos.getDatos().length)-1;    
                    activarDesactivarJButtonInsercionFila(new boolean[]{false, true, true, false}); 
                    jLabelInformaFilaInsertada.setText("Fila insertada al final de la tabla");
                    jLabelInformaGuardarFilaInsertada.setText("Antes de pulsar el botón Guardar situe el cursor en columna Código de la fila");                    
                    break;           
             case "cancelarInsercionFila" :                                                                 
                    modeloDatos.eliminarFilaInsertada();        
                    listSelectionModel.removeSelectionInterval(((Integer)componentesJPanel[2]), ((Integer)componentesJPanel[2]));
                    componentesJPanel[2] = -1;                        // Modifica valor de fila seleccionada en JTable
                    filaAInsertarJTable = -1;                                       
                    activarDesactivarJButtonInsercionFila(new boolean[]{true, false, false, true});
                    jLabelInformaFilaInsertada.setText("                                     ");
                    jLabelInformaGuardarFilaInsertada.setText("                                                                              ");                                       
                    break; 
             case "guardarFilaInsertada" :                          
                    Filtros.filtrarDatosNulos( new String[]{ (String)(modeloDatos.getDatos()[filaAInsertarJTable][1]),
                                                             (String)(modeloDatos.getDatos()[filaAInsertarJTable][2]),
                                                             (String)(modeloDatos.getDatos()[filaAInsertarJTable][3])
                                                            }                         
                                             );
                    Filtros.filtrarFecha((String) modeloDatos.getDatos()[filaAInsertarJTable][3]);
                    Filtros.filtrarNumeroPaginasLibro(((Integer)modeloDatos.getDatos()[filaAInsertarJTable][4]));                         
                    libro = new Libro();
                    libro.setTitulo((String)(modeloDatos.getDatos()[filaAInsertarJTable][1]));
                    libro.setGenero(((String)(modeloDatos.getDatos()[filaAInsertarJTable][2])).substring(0, 1));
                    libro.setFechaEdicion(java.sql.Date.valueOf(((String)(modeloDatos.getDatos()[filaAInsertarJTable][3])).substring(6, 10) +"-"+
                                                                 ((String)(modeloDatos.getDatos()[filaAInsertarJTable][3])).substring(3, 5) +"-"+
                                                                 ((String)(modeloDatos.getDatos()[filaAInsertarJTable][3])).substring(0, 2)
                                                               ));
                    libro.setNumeroPaginas(((Integer)(modeloDatos.getDatos()[filaAInsertarJTable][4])));
                    libro.setPremiado(((Boolean)(modeloDatos.getDatos()[filaAInsertarJTable][5])));                                     
                    new LibrosNegocio().insertar((BaseDatos)controller.getRepositorio()[0], libro);   
                    inicializarPantalla();
                    listSelectionModel.removeSelectionInterval(((Integer)componentesJPanel[2]), ((Integer)componentesJPanel[2]));                          
                    componentesJPanel[2] = -1;                        // Modifica valor de fila seleccionada en JTable
                    filaAInsertarJTable = -1;                                                      
                    activarDesactivarJButtonInsercionFila(new boolean[]{true, false, false, true});  
                    jLabelInformaFilaInsertada.setText("                                     ");
                    jLabelInformaGuardarFilaInsertada.setText("                                                                              ");                                                                                                                  
                    break;  
             case "eliminarFilaSeleccionada" :
                    if (((Integer)componentesJPanel[2]) != -1)    
                    {  
                        libro = new Libro();
                        libro.setIdLibro((String)modeloDatos.getDatos()[((Integer)componentesJPanel[2])][0]);
                        new LibrosNegocio().eliminar((BaseDatos)controller.getRepositorio()[0], libro);
                        inicializarPantalla();
                        listSelectionModel.removeSelectionInterval(((Integer)componentesJPanel[2]), ((Integer)componentesJPanel[2]));                                                             
                        componentesJPanel[2] = -1;
                    }
                    break;      
             case "actualizadaColumnaJTable" :System.out.println("vamos al filtro");
                    if (((Integer)componentesJPanel[14]) == 3)
                       Filtros.filtrarFecha((String) modeloDatos.getDatos()[((Integer)componentesJPanel[13])][((Integer)componentesJPanel[14])]);

                    if (((Integer)componentesJPanel[14]) == 4)
                       Filtros.filtrarNumeroPaginasLibro(((Integer)modeloDatos.getDatos()[((Integer)componentesJPanel[13])][((Integer)componentesJPanel[14])]));
                    
                    if (((String)modeloDatos.getDatos()[((Integer)componentesJPanel[13])][0]).compareTo("") != 0)
                    {
                        libro = new Libro();
                        libro.setIdLibro((String)modeloDatos.getDatos()[((Integer)componentesJPanel[13])][0]);
                        
                        if (((Integer)componentesJPanel[14]) == 2)
                             libro.setDatoActualizado(((String)modeloDatos.getDatos()[((Integer)componentesJPanel[13])][((Integer)componentesJPanel[14])]).substring(0, 1));
                          else
                             libro.setDatoActualizado(modeloDatos.getDatos()[((Integer)componentesJPanel[13])][((Integer)componentesJPanel[14])]);
                        libro.setColumnaActualizada(((Integer)componentesJPanel[14]));
                                  
                        new LibrosNegocio().actualizar((BaseDatos)controller.getRepositorio()[0], libro, -1);       
                    }
                    break;                             
        }        
    }
    
}

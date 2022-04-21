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
public class VistaPaginadaTabla extends PantallaOpcion {

       private ModeloDatos modeloDatos;
       private JTable jTable;
       private ListSelectionModel listSelectionModel;
       private JButton[] botonesPaginacion = {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null};
       private JLabel[] etiquetasPuntosSuspensivos = {null, null};
       private ParametrosPaginacion parametrosPaginacion;
       private int numeroTotalPaginas = 0;
       
    public VistaPaginadaTabla() {
        componentesJPanel[2] = -1;   //  Fila seleccionada en JTable
        componentesJPanel[10] = 1;   //  Criterio de ordenación de las filas del JTable. 
        componentesJPanel[11] = 1;   //  Número de página seleccionada para ser visualizada
        componentesJPanel[16] = -1;  //  En botones de paginación numérica, número de página que consta en el text del botón
        
        ubicarComponentes();
    }
    
   
    
    private void ubicarComponentes() {
        setLayout(null);  
    }    
    

    
    public void inicializarPostInstanciar(Controller controller) throws Exception {
        
        this.controller = controller;
        parametrosPaginacion = (ParametrosPaginacion)controller.getRepositorio()[4];
                
        modeloDatos = new ModeloDatos(controller, ((ParametrosPaginacion)controller.getRepositorio()[4]).getNumeroFilasPagina());
        //componentesJPanel[15] = modeloDatos;
        
        jTable = new JTable(modeloDatos);
        JScrollPane jScrollPaneTabla = new JScrollPane(jTable);
        jScrollPaneTabla.setBounds(20, 20, 1140, 20+16*((ParametrosPaginacion)controller.getRepositorio()[4]).getNumeroFilasPagina());
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
        
        JLabel jLabelCriterioOrdenacion = new JLabel("CRITERIO ORDENACION :");
        jLabelCriterioOrdenacion.setBounds(100, 180+16*((ParametrosPaginacion)controller.getRepositorio()[4]).getNumeroFilasPagina(), 150, 25);
        add(jLabelCriterioOrdenacion);

        JRadioButton jRadioButtonIdLibro = new JRadioButton("identificador libro", true);
        jRadioButtonIdLibro.setBounds(100, 220+16*((ParametrosPaginacion)controller.getRepositorio()[4]).getNumeroFilasPagina(), 150, 25);
        add(jRadioButtonIdLibro);
        jRadioButtonIdLibro.setActionCommand("ordenarPorIdentificador");   
        jRadioButtonIdLibro.addActionListener(controller);
        
        JRadioButton jRadioButtonTitulo = new JRadioButton("título", false);
        jRadioButtonTitulo.setBounds(100, 260+16*((ParametrosPaginacion)controller.getRepositorio()[4]).getNumeroFilasPagina(), 150, 25);
        add(jRadioButtonTitulo);
        jRadioButtonTitulo.setActionCommand("ordenarPorTitulo");         
        jRadioButtonTitulo.addActionListener(controller);

        JRadioButton jRadioButtonGenero = new JRadioButton("género", false);
        jRadioButtonGenero.setBounds(100, 300+16*((ParametrosPaginacion)controller.getRepositorio()[4]).getNumeroFilasPagina(), 150, 25);
        add(jRadioButtonGenero);
        jRadioButtonGenero.setActionCommand("ordenarPorGenero");
        jRadioButtonGenero.addActionListener(controller);
        
        ButtonGroup buttonGroupCriteriosOrdenacion = new ButtonGroup();
        buttonGroupCriteriosOrdenacion.add(jRadioButtonIdLibro);
        buttonGroupCriteriosOrdenacion.add(jRadioButtonTitulo);
        buttonGroupCriteriosOrdenacion.add(jRadioButtonGenero);      
        
        JButton jButtonEliminarFilaSeleccionada = new JButton("Eliminar fila seleccionada");
        jButtonEliminarFilaSeleccionada.setBounds(400, 260+16*((ParametrosPaginacion)controller.getRepositorio()[4]).getNumeroFilasPagina(), 200, 40);
        jButtonEliminarFilaSeleccionada.setActionCommand("eliminarFilaSeleccionada");
        add(jButtonEliminarFilaSeleccionada);            
        jButtonEliminarFilaSeleccionada.addActionListener(controller);                
    }   
    

    private void instanciarBotonesPaginacion() {
 
       int numeroBotonesNumericos = ((ParametrosPaginacion)controller.getRepositorio()[4]).getNumeroBotonesNumericos();
       int paginaActual = ((Integer)componentesJPanel[11]).intValue();      
      
       //  Eliminar de la Vista los botones de paginación de la visualización anterior
       for (int i=0; i<botonesPaginacion.length; i++) 
       {
           if (botonesPaginacion[i] != null)
              {   botonesPaginacion[i].setVisible(false);
                  remove(botonesPaginacion[i]);   
                  botonesPaginacion[i] = null;
              }           
       }     
       //  Eliminar de la Vista las etiquetas de puntos suspensivos de la visualización anterior
       for (int i=0; i<etiquetasPuntosSuspensivos.length; i++) 
       {
           if (etiquetasPuntosSuspensivos[i] != null)
              {   etiquetasPuntosSuspensivos[i].setVisible(false);
                  remove(etiquetasPuntosSuspensivos[i]);  
                  etiquetasPuntosSuspensivos[i] = null;
              }          
       }            
    
       int contadorBotones = 0; 
       if (paginaActual > 1) 
       {             
          botonesPaginacion[contadorBotones] = new JButton();
          botonesPaginacion[contadorBotones].setBounds(50+(80*contadorBotones), 80+16*((ParametrosPaginacion)controller.getRepositorio()[4]).getNumeroFilasPagina(), 70, 50);
          botonesPaginacion[contadorBotones].setActionCommand("botonPaginacionAnterior");
          botonesPaginacion[contadorBotones].setText("<");
          add(botonesPaginacion[contadorBotones]);            
          botonesPaginacion[contadorBotones].addActionListener(controller);  
          contadorBotones++;                
       }          
          
       int paginaInicio = paginaActual;
       if (paginaInicio-(numeroBotonesNumericos/2) > 0 && (numeroTotalPaginas-numeroBotonesNumericos) >= 0)
            paginaInicio = paginaInicio-(numeroBotonesNumericos/2);
         else
            paginaInicio = 1;   
       
       if ((numeroTotalPaginas-paginaInicio) < (numeroBotonesNumericos-1) && (numeroTotalPaginas-(numeroBotonesNumericos-1))>0)
            paginaInicio = numeroTotalPaginas-(numeroBotonesNumericos-1);
        
       boolean esElPrimero = true;
       int ultimoValorVariableControl = 0;
       for (int i=paginaInicio; i < paginaInicio+numeroBotonesNumericos && i<=numeroTotalPaginas; i++) 
       {           
           if (esElPrimero)
           {
               if (i > 1)
               {
                  botonesPaginacion[contadorBotones] = new JButton();
                  botonesPaginacion[contadorBotones].setBounds(50+(80*contadorBotones), 80+16*((ParametrosPaginacion)controller.getRepositorio()[4]).getNumeroFilasPagina(), 70, 50);
                  botonesPaginacion[contadorBotones].setActionCommand("botonPaginacionNumerica");
                  botonesPaginacion[contadorBotones].setText("1");
                  add(botonesPaginacion[contadorBotones]);            
                  botonesPaginacion[contadorBotones].addActionListener(controller);    
                  contadorBotones++;                                               
               }
                     
               if (i > 2)
               {
                  etiquetasPuntosSuspensivos[0] = new JLabel("         .  .  .");
                  etiquetasPuntosSuspensivos[0].setBounds(50+(80*contadorBotones), 80+16*((ParametrosPaginacion)controller.getRepositorio()[4]).getNumeroFilasPagina(), 70, 50);
                  add(etiquetasPuntosSuspensivos[0]);
                  contadorBotones++;
               }   
                     
               esElPrimero = false;
           }
 
           botonesPaginacion[contadorBotones] = new JButton();
           botonesPaginacion[contadorBotones].setBounds(50+(80*contadorBotones), 80+16*((ParametrosPaginacion)controller.getRepositorio()[4]).getNumeroFilasPagina(), 70, 50);
           botonesPaginacion[contadorBotones].setActionCommand("botonPaginacionNumerica");
           botonesPaginacion[contadorBotones].setText(new Integer(i+1-1).toString());  
           add(botonesPaginacion[contadorBotones]);
           botonesPaginacion[contadorBotones].addActionListener(controller);                     
           contadorBotones++;    
               
           ultimoValorVariableControl = i;
       }
       
       if (ultimoValorVariableControl <= numeroTotalPaginas-2)
       {
          etiquetasPuntosSuspensivos[1] = new JLabel("         .  .  .");
          etiquetasPuntosSuspensivos[1].setBounds(50+(80*contadorBotones), 80+16*((ParametrosPaginacion)controller.getRepositorio()[4]).getNumeroFilasPagina(), 70, 50);
          add(etiquetasPuntosSuspensivos[1]);
          contadorBotones++;                                        
       }
             
       if (ultimoValorVariableControl <= numeroTotalPaginas-1)
       {
          botonesPaginacion[contadorBotones] = new JButton();
          botonesPaginacion[contadorBotones].setBounds(50+(80*contadorBotones), 80+16*((ParametrosPaginacion)controller.getRepositorio()[4]).getNumeroFilasPagina(), 70, 50);
          botonesPaginacion[contadorBotones].setActionCommand("botonPaginacionNumerica");
          botonesPaginacion[contadorBotones].setText(new Integer(numeroTotalPaginas).toString());
          add(botonesPaginacion[contadorBotones]);            
          botonesPaginacion[contadorBotones].addActionListener(controller);    
          contadorBotones++;                                      
       }                       

       if (paginaActual < numeroTotalPaginas) 
       {            
          botonesPaginacion[contadorBotones] = new JButton();
          botonesPaginacion[contadorBotones].setBounds(50+(80*contadorBotones), 80+16*((ParametrosPaginacion)controller.getRepositorio()[4]).getNumeroFilasPagina(), 70, 50);
          botonesPaginacion[contadorBotones].setActionCommand("botonPaginacionSiguiente");
          botonesPaginacion[contadorBotones].setText(">");
          add(botonesPaginacion[contadorBotones]);            
          botonesPaginacion[contadorBotones].addActionListener(controller);                 
       }        
       
       for (int i=0; i<botonesPaginacion.length; i++) 
       {
           if (botonesPaginacion[i] != null)
              if (botonesPaginacion[i].getActionCommand().compareTo("botonPaginacionNumerica") == 0 )
                  if (Integer.parseInt(botonesPaginacion[i].getText()) == paginaActual )
                      botonesPaginacion[i].setForeground(java.awt.Color.LIGHT_GRAY);  
       }     
    }
    
    
    
    private int calcularNumeroPaginas(Integer numeroFilas, ParametrosPaginacion parametrosPaginacion) {
        int numeroPaginas = numeroFilas.intValue() / parametrosPaginacion.getNumeroFilasPagina(); 
        
        if (numeroFilas.intValue() % parametrosPaginacion.getNumeroFilasPagina() > 0)
            numeroPaginas++;
        
        return numeroPaginas;
    }
    
    
    
    public void inicializarPantalla() throws Exception {      

        parametrosPaginacion.setNumeroPagina(((Integer)componentesJPanel[11]).intValue());
        modeloDatos.cargar(new LibrosNegocio().consultarPagina((BaseDatos)controller.getRepositorio()[0], ((Integer)componentesJPanel[10]).intValue(), parametrosPaginacion));
        numeroTotalPaginas = calcularNumeroPaginas(new LibrosNegocio().consultarNumeroFilas((BaseDatos)controller.getRepositorio()[0]), parametrosPaginacion);  // Es necesario tener en cuenta que actualizaciones realizadas por otras sesiones podrían repercutir en el número de filas de la tabla, y en consecuencia, en el número de páginas.
        instanciarBotonesPaginacion(); 
    }      
    
    
    
    public void responderAController(String actionCommand) throws Exception {
                
        switch(actionCommand)
        {   
             case "reordenar" :       
                    componentesJPanel[11] =  1;  
                    break;              
             case "botonPaginacionAnterior" :                     
                    componentesJPanel[11] =  ((Integer)componentesJPanel[11]) - 1;             
                    break;     
             case "botonPaginacionSiguiente" :
                    componentesJPanel[11] =  ((Integer)componentesJPanel[11]) + 1;        
                    break;           
             case "botonPaginacionNumerica" :                                                                 
                    componentesJPanel[11] =  ((Integer)componentesJPanel[16]);                      
                    break;        
             case "eliminarFilaSeleccionada" :
                    if (((Integer)componentesJPanel[2]) != -1)    
                    {  
                        Libro libro = new Libro();
                        libro.setIdLibro((String)modeloDatos.getDatos()[((Integer)componentesJPanel[2])][0]);
                        new LibrosNegocio().eliminar((BaseDatos)controller.getRepositorio()[0], libro);
                        componentesJPanel[11] =  1; 
                        listSelectionModel.removeSelectionInterval(((Integer)componentesJPanel[2]), ((Integer)componentesJPanel[2]));                                                             
                        componentesJPanel[2] = -1;
                    }
                    break;                          
        }  
        
        inicializarPantalla();
    }
}


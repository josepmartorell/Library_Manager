/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import application.GenericaExcepcion;
import controller.Controller;
import java.awt.Color;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import model.Libro;
import persistence.BaseDatos;
import persistence.LibrosNegocio;
import persistence.LimitesListado;
import printer.ParametrosListado;

/**
 *
 * @author jtech
 */
public class ConfigurarDocumento extends PantallaOpcion {

       private JLabel jLabelCriterioOrdenacion;
       private JRadioButton jRadioButtonIdLibro;
       private JRadioButton jRadioButtonTitulo;
       private JRadioButton jRadioButtonGenero;
       private ButtonGroup buttonGroupCriteriosOrdenacion;
       private JLabel jLabelSeleccionLimiteInferior;
       private JComboBox jComboBoxSeleccionLimiteInferior;
       private JLabel jLabelSeleccionLimiteSuperior;
       private JComboBox jComboBoxSeleccionLimiteSuperior;
       private JButton jButtonGenerarPDF;
       private JButton jButtonImprimir;
       
      
    public ConfigurarDocumento() {
        componentesJPanel[10] = 1;   //  El valor registrado determina el criterio de ordenación de las filas en el documento PDF.
        ubicarComponentes();
    }
  
    private void ubicarComponentes() {
        setLayout(null);

        jLabelCriterioOrdenacion = new JLabel("CRITERIO ORDENACION :");
        jLabelCriterioOrdenacion.setBounds(300, 100, 150, 25);
        add(jLabelCriterioOrdenacion);

        jRadioButtonIdLibro = new JRadioButton("identificador libro", true);
        jRadioButtonIdLibro.setBounds(300, 140, 150, 25);
        add(jRadioButtonIdLibro);
        jRadioButtonIdLibro.setActionCommand("ordenarPorIdentificador");   
        
        jRadioButtonTitulo = new JRadioButton("título", false);
        jRadioButtonTitulo.setBounds(300, 180, 150, 25);
        add(jRadioButtonTitulo);
        jRadioButtonTitulo.setActionCommand("ordenarPorTitulo");     
        
        jRadioButtonGenero = new JRadioButton("género", false);
        jRadioButtonGenero.setBounds(300, 220, 150, 25);
        add(jRadioButtonGenero);
        jRadioButtonGenero.setActionCommand("ordenarPorGenero");  
        
        buttonGroupCriteriosOrdenacion = new ButtonGroup();
        buttonGroupCriteriosOrdenacion.add(jRadioButtonIdLibro);
        buttonGroupCriteriosOrdenacion.add(jRadioButtonTitulo);
        buttonGroupCriteriosOrdenacion.add(jRadioButtonGenero);      
        
        jLabelSeleccionLimiteInferior = new JLabel("LIMITE INFERIOR :");
        jLabelSeleccionLimiteInferior.setBounds(280, 300, 120, 20);
        add(jLabelSeleccionLimiteInferior);
        
        jComboBoxSeleccionLimiteInferior = new JComboBox();
        jComboBoxSeleccionLimiteInferior.setBounds(410,300,370,20);
        jComboBoxSeleccionLimiteInferior.setBackground(Color.white);
        add(jComboBoxSeleccionLimiteInferior);       
        
        jLabelSeleccionLimiteSuperior = new JLabel("LIMITE SUPERIOR :");
        jLabelSeleccionLimiteSuperior.setBounds(280, 470, 120, 20);
        add(jLabelSeleccionLimiteSuperior);
        
        jComboBoxSeleccionLimiteSuperior = new JComboBox();
        jComboBoxSeleccionLimiteSuperior.setBounds(410,470,370,20);
        jComboBoxSeleccionLimiteSuperior.setBackground(Color.white);
        add(jComboBoxSeleccionLimiteSuperior);     
        
        jButtonGenerarPDF = new JButton("Generar PDF");
        jButtonGenerarPDF.setBounds(375,650,150,40);
        jButtonGenerarPDF.setActionCommand("generarPDF");
        add(jButtonGenerarPDF);  
        
        jButtonImprimir = new JButton("Imprimir");
        jButtonImprimir.setBounds(575,650,150,40);
        jButtonImprimir.setActionCommand("imprimir");
        add(jButtonImprimir);        
    }   
    
    
    public void inicializarPostInstanciar(Controller controller) throws Exception {
        
        this.controller = controller;    
        
        jRadioButtonIdLibro.addActionListener(controller);        
        jRadioButtonTitulo.addActionListener(controller);
        jRadioButtonGenero.addActionListener(controller);          
        jButtonGenerarPDF.addActionListener(controller); 
        jButtonImprimir.addActionListener(controller); 

        inicializarPantalla();        
    }

    
    @Override
    public void inicializarPantalla() throws Exception {  
        
        jComboBoxSeleccionLimiteInferior.removeAllItems();
        jComboBoxSeleccionLimiteSuperior.removeAllItems(); 
        
        List<Libro> listaLibros = null;
        String[] listaGeneros = null;
        int numeroItems = 0;
        if (((Integer)componentesJPanel[10]).intValue() != 3)    
            {                       
               listaLibros = new LibrosNegocio().consultarTodos((BaseDatos)controller.getRepositorio()[0], ((Integer)componentesJPanel[10]).intValue(), null, null);
               numeroItems = listaLibros.size();
            }
          else
            {  listaGeneros = new LibrosNegocio().consultarGeneros((BaseDatos)controller.getRepositorio()[0]);
               numeroItems = listaGeneros.length;
            }
        
        if (numeroItems > 0)
        {
           for (int i=0; i<numeroItems; i++)
           {
              if (((Integer)componentesJPanel[10]) != 3)    
                  {               
                     Libro libro = listaLibros.get(i);
                     jComboBoxSeleccionLimiteInferior.addItem(libro.getIdLibro()+"  -  "+libro.getTitulo());
                     jComboBoxSeleccionLimiteSuperior.addItem(libro.getIdLibro()+"  -  "+libro.getTitulo());
                  }
                else
                  {  
                     jComboBoxSeleccionLimiteInferior.addItem(listaGeneros[i]);
                     jComboBoxSeleccionLimiteSuperior.addItem(listaGeneros[i]);                     
                  }              
           }       
           jComboBoxSeleccionLimiteInferior.setSelectedIndex(0);
           jComboBoxSeleccionLimiteSuperior.setSelectedIndex(numeroItems-1);
        }
    }    

        
    @Override
    public void responderAController(String actionCommand) throws Exception {
        List<Libro> listaLibros;
        
        switch(actionCommand)
        {   
           case "reordenar" :   
                  inicializarPantalla();
                  break;     
           case "generarPDF" : 
                  listaLibros = obtenerListaLibros();
                  if (!listaLibros.isEmpty())
                       new LibrosNegocio().generarPDF(listaLibros, (ParametrosListado)controller.getRepositorio()[5], seleccionDirectorioCreacionPDFs());
                     else
                       throw new GenericaExcepcion(70); 
                  break;
           case "imprimir" : 
                  listaLibros = obtenerListaLibros();
                  if (!listaLibros.isEmpty())
                       new LibrosNegocio().imprimir(listaLibros, (ParametrosListado)controller.getRepositorio()[5]); 
                     else
                       throw new GenericaExcepcion(70); 
                  break;                   
        }
        
    }  
    
    
    private List<Libro> obtenerListaLibros() throws Exception {
        
        List<Libro> listaLibros;
        LimitesListado limitesListado = new LimitesListado();
        switch (((Integer)componentesJPanel[10]))
        {
           case 1: limitesListado.setLimiteInferior(jComboBoxSeleccionLimiteInferior.getSelectedItem().toString().substring(0, 5));
                   limitesListado.setLimiteSuperior(jComboBoxSeleccionLimiteSuperior.getSelectedItem().toString().substring(0, 5));
                   break;
           case 2: limitesListado.setLimiteInferior(jComboBoxSeleccionLimiteInferior.getSelectedItem().toString().substring(10));
                   limitesListado.setLimiteSuperior(jComboBoxSeleccionLimiteSuperior.getSelectedItem().toString().substring(10));
                   break;   
           case 3: limitesListado.setLimiteInferior(jComboBoxSeleccionLimiteInferior.getSelectedItem().toString().substring(0, 1));
                   limitesListado.setLimiteSuperior(jComboBoxSeleccionLimiteSuperior.getSelectedItem().toString().substring(0, 1));
                   break;                     
        }
              
        listaLibros = new LibrosNegocio().consultarTodos((BaseDatos)controller.getRepositorio()[0], ((Integer)componentesJPanel[10]).intValue(), null, limitesListado);      
        
        return listaLibros;
    }  

    
    private String seleccionDirectorioCreacionPDFs() {
                
        JFileChooser jFileChooser = new JFileChooser("D:\\PDFs_creados");
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jFileChooser.showDialog(this, "Selección directorio creación PDFs");

        return jFileChooser.getSelectedFile().getAbsolutePath();
    }    
}


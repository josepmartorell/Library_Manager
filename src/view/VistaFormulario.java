/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author jtech
 */

import controller.Controller;
import model.Libro;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import persistence.BaseDatos;
import persistence.LibrosNegocio;

public class VistaFormulario extends PantallaOpcion {
  
      private ListSelectionModel listSelectionModel;
      private JComboBox jComboBoxLibros;
      private JLabel jLabelCodigo;
      private JTextField jTextFieldCodigo;
      private JLabel jLabelTitulo;
      private JTextField jTextFieldTitulo;
      private JLabel jLabelFechaEdicion;
      private JTextField jTextFieldFechaEdicion;
      private JCheckBox jCheckBoxPremiado;
      private JLabel jLabelGenero;
      private JList jListGeneros;
      private JSlider jSLiderNumeroPaginas;
      private JLabel jLabelNumeroPaginas;
      private JButton jButtonNuevoLibro;
      private JButton jButtonAplicarCambios;
      private JButton jButtonEliminar;
      private int actualizaciones;
            
    public VistaFormulario() {   
        ubicarComponentes();
    }
    
      
    private void ubicarComponentes() {
        setLayout(null);
  
        jComboBoxLibros = new JComboBox();
        jComboBoxLibros.setBounds(20, 100, 450, 20);
        jComboBoxLibros.setBackground(Color.white);
        jComboBoxLibros.setActionCommand("comboLibros");
        add(jComboBoxLibros);    
        
        jLabelCodigo = new JLabel("Código");
        jLabelCodigo.setBounds(550, 100, 45, 20);
        add(jLabelCodigo);
        
        jTextFieldCodigo = new JTextField();
        jTextFieldCodigo.setBounds(600, 100, 50, 20);
        jTextFieldCodigo.setEditable(false);
        add(jTextFieldCodigo);

        jLabelTitulo = new JLabel("Título");
        jLabelTitulo.setBounds(550, 1400, 45, 20);
        add(jLabelTitulo);      
        
        jTextFieldTitulo = new JTextField();
        jTextFieldTitulo.setBounds(600, 140, 400, 20);
        add(jTextFieldTitulo);
        componentesJPanel[2] = jTextFieldTitulo;
 
        jLabelFechaEdicion = new JLabel("Fecha edición (dd-mm-aaaa)");
        jLabelFechaEdicion.setBounds(550, 180, 170, 20);
        add(jLabelFechaEdicion);
        
        jTextFieldFechaEdicion = new JTextField();
        jTextFieldFechaEdicion.setBounds(730, 180, 80, 20);
        add(jTextFieldFechaEdicion);
        componentesJPanel[3] = jTextFieldFechaEdicion;
        
        jCheckBoxPremiado = new JCheckBox("Premiado");
        jCheckBoxPremiado.setBounds(550, 220, 150, 20);
        add(jCheckBoxPremiado);
        componentesJPanel[4] = jCheckBoxPremiado;
        
        jLabelGenero = new JLabel("Genero");
        jLabelGenero.setBounds(550, 260, 50, 20);
        add(jLabelGenero);               
        
        jSLiderNumeroPaginas = new JSlider(JSlider.HORIZONTAL,1,2000,300);
        jSLiderNumeroPaginas.setBounds(20, 430, 1070, 48);
        jSLiderNumeroPaginas.setBorder(new TitledBorder("Número páginas"));
        jSLiderNumeroPaginas.setPaintTicks(true);
        jSLiderNumeroPaginas.setMajorTickSpacing(200);
        jSLiderNumeroPaginas.setMinorTickSpacing(10);
        add(jSLiderNumeroPaginas);
        componentesJPanel[6] = jSLiderNumeroPaginas;
        
        jLabelNumeroPaginas = new JLabel();
        jLabelNumeroPaginas.setBounds(1095, 450, 80, 20);
        add(jLabelNumeroPaginas);
       
        jButtonNuevoLibro = new JButton("Nuevo libro");
        jButtonNuevoLibro.setBounds(475, 550, 200, 40);
        jButtonNuevoLibro.setActionCommand("nuevoLibro");
        add(jButtonNuevoLibro);            
        
        jButtonAplicarCambios = new JButton("Aplicar cambios");
        jButtonAplicarCambios.setBounds(475, 630, 200, 40);
        jButtonAplicarCambios.setActionCommand("aplicarCambios");
        add(jButtonAplicarCambios);            
        
        jButtonEliminar = new JButton("Eliminar");
        jButtonEliminar.setBounds(475, 710, 200, 40);
        jButtonEliminar.setActionCommand("eliminarLibro");
        add(jButtonEliminar);                   
    }   
    
    
    @Override
    public void inicializarPostInstanciar(Controller controller) throws Exception {
        
        this.controller = controller;
        
        jListGeneros = new JList(new LibrosNegocio().consultarGeneros((BaseDatos)controller.getRepositorio()[0]));
        JScrollPane jScrollPaneGenero = new JScrollPane(jListGeneros);
        jScrollPaneGenero.setBounds(550, 290, 250, 115);
        add(jScrollPaneGenero);
        jListGeneros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listSelectionModel = jListGeneros.getSelectionModel();
        listSelectionModel.addListSelectionListener(controller);
        componentesJPanel[12] = listSelectionModel;
        componentesJPanel[5] = jListGeneros;
 
        jTextFieldTitulo.addCaretListener(controller);
        jTextFieldFechaEdicion.addCaretListener(controller);
        jCheckBoxPremiado.addItemListener(controller);
        jSLiderNumeroPaginas.addChangeListener(controller);  
        jButtonNuevoLibro.addActionListener(controller);
        jButtonAplicarCambios.addActionListener(controller);
        jButtonEliminar.addActionListener(controller);
    }
    
    
    @Override
    public void inicializarPantalla() throws Exception {
        
        jTextFieldCodigo.setText("");
        jTextFieldTitulo.setText("");
        jTextFieldFechaEdicion.setText("");
        jCheckBoxPremiado.setSelected(false);
        jSLiderNumeroPaginas.setValue(1);
        jListGeneros.removeSelectionInterval(jListGeneros.getSelectedIndex(), jListGeneros.getSelectedIndex());
             
        if (jComboBoxLibros.getActionListeners().length  > 0)        
            jComboBoxLibros.removeActionListener(controller);
            
        jComboBoxLibros.removeAllItems();
                             
        List<Libro> listaLibros = new LibrosNegocio().consultarTodos((BaseDatos)controller.getRepositorio()[0], 2, null, null);
        for (int i=0; i<listaLibros.size(); i++)
        {
           Libro libro = listaLibros.get(i);
           jComboBoxLibros.addItem(libro.getIdLibro()+"  -  "+libro.getTitulo());
        }
        jComboBoxLibros.addActionListener(controller);
        
        actualizaciones = 0;
    }    
    
    
    @Override
    public void responderAController(String actionCommand) throws Exception {
        Libro libro;
        
        switch(actionCommand)
        {   
             case "comboLibros" :                  
                    visualizarLibroSeleccionado(jComboBoxLibros.getSelectedItem().toString().substring(0, 5));                         
                    actualizaciones = 0;
                    break;     
             case "nuevoLibro" :
                    inicializarPantalla();
                    break;           
             case "aplicarCambios" :                                                                 
                    Filtros.filtrarDatosNulos( new String[]{ jTextFieldTitulo.getText(),
                                                             jTextFieldFechaEdicion.getText()
                                                           }                         
                                              );
                    Filtros.filtrarFecha(jTextFieldFechaEdicion.getText());
                    // Filtros.filtrarNumeroPaginasLibro(jSLiderNumeroPaginas.getValue());    // Innecesario porque el JSlider tiene el rango de valores limitado      
                    Filtros.filtrarSeleccionJList((jListGeneros));
                    libro = new Libro();
                    libro.setTitulo(jTextFieldTitulo.getText());
                    libro.setGenero(((String)(jListGeneros.getSelectedValue())).substring(0, 1));
                    libro.setFechaEdicion(java.sql.Date.valueOf(jTextFieldFechaEdicion.getText().substring(6, 10) +"-"+
                                                                jTextFieldFechaEdicion.getText().substring(3, 5) +"-"+
                                                                jTextFieldFechaEdicion.getText().substring(0, 2)
                                                               ));
                    libro.setNumeroPaginas(jSLiderNumeroPaginas.getValue());
                    libro.setPremiado(jCheckBoxPremiado.isSelected());
                       
                    if (jTextFieldCodigo.getText().compareTo("") == 0)    
                       { 
                          new LibrosNegocio().insertar((BaseDatos)controller.getRepositorio()[0], libro);   
                       }
                      else
                       { 
                          if (actualizaciones > 0)
                          {
                             libro.setIdLibro(jTextFieldCodigo.getText());
                             new LibrosNegocio().actualizar((BaseDatos)controller.getRepositorio()[0], libro, actualizaciones);
                          }
                       }    
                    inicializarPantalla();                                 
                    break; 
             case "eliminarLibro" :
                    if (jTextFieldCodigo.getText().compareTo("") != 0)    
                     {  
                       libro = new Libro();
                       libro.setIdLibro(jTextFieldCodigo.getText());
                       new LibrosNegocio().eliminar((BaseDatos)controller.getRepositorio()[0], libro);    
                       inicializarPantalla();
                     }                                                           
                    break;  
             case "actualizadoTitulo" :
                    actualizaciones|=1;
                    break;    
             case "actualizadoGenero" :
                    actualizaciones|=2;
                    break;                        
             case "actualizadoFechaEdicion" :
                    actualizaciones|=4;
                    break;         
             case "actualizadoNumeroPaginas" :
                    actualizaciones|=8;
                    jLabelNumeroPaginas.setText(Integer.toString(jSLiderNumeroPaginas.getValue())+"  páginas");
                    break;                    
             case "actualizadoPremiado" :
                    actualizaciones|=16;
                    break;                   
        }   
        
        System.out.println("Valor acumulado en actualizaciones :  "+actualizaciones);
    }  
    
    
    private void visualizarLibroSeleccionado(String idLibro) throws Exception {
        
        Libro libro = new Libro();
        libro.setIdLibro(idLibro);
        Libro libroObtenido = new LibrosNegocio().consultarPorIdLibro((BaseDatos)controller.getRepositorio()[0], libro);
        jTextFieldCodigo.setText(libroObtenido.getIdLibro());
        jTextFieldTitulo.setText(libroObtenido.getTitulo());
        jTextFieldFechaEdicion.setText(new SimpleDateFormat("dd-MM-yyyy").format(libroObtenido.getFechaEdicion()));               
        jCheckBoxPremiado.setSelected(libroObtenido.isPremiado());
        jListGeneros.setSelectedValue(libroObtenido.getGenero()+"  -  "+libroObtenido.getDescripcion(), true);   
        jSLiderNumeroPaginas.setValue(libroObtenido.getNumeroPaginas());
    }    
}


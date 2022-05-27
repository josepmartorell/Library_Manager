/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.Controller;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.Libro;
import persistence.BaseDatos;
import persistence.LibrosNegocio;

/**
 *
 * @author jtech
 */
public class EdicionNodo extends PantallaOpcion {

      private VistaArbol vistaArbol;
      private JPanel panelInterior;
      private JLabel jLabelCodigo;
      private JTextField jTextFieldCodigo;
      private JLabel jLabelTitulo;
      private JTextField jTextFieldTitulo;
      private JLabel jLabelFechaEdicion;
      private JTextField jTextFieldFechaEdicion;
      private JCheckBox jCheckBoxPremiado;
      private JLabel jLabelNumeroPaginas;
      private JTextField jTextFieldNumeroPaginas;
      private JButton jButtonAplicarCambios;
      private JButton jButtonCancelarEdicion;
      private DefaultMutableTreeNodeHeredada  nodoGeneroSeleccion; 
      private DefaultMutableTreeNodeHeredada  nodoLibroSeleccion;      
      private int actualizaciones = 0;    
            
    public EdicionNodo() {   
        ubicarComponentes();
    }
  
    
    private void ubicarComponentes() {
        setLayout(null);  
 
        panelInterior = new JPanel();
        panelInterior.setLayout(null);
        
        jLabelCodigo = new JLabel("Código");
        jLabelCodigo.setBounds(20, 20, 45, 20);
        panelInterior.add(jLabelCodigo);
        
        jTextFieldCodigo = new JTextField();
        jTextFieldCodigo.setBounds(65, 20, 48, 20);;
        jTextFieldCodigo.setEditable(false);
        panelInterior.add(jTextFieldCodigo);
        
        jLabelTitulo = new JLabel("Título");
        jLabelTitulo.setBounds(20, 60, 43, 20);
        panelInterior.add(jLabelTitulo);      
        
        jTextFieldTitulo = new JTextField();
        jTextFieldTitulo.setBounds(65, 60 ,300, 20);
        panelInterior.add(jTextFieldTitulo);
        componentesJPanel[2] = jTextFieldTitulo;
 
        jLabelFechaEdicion = new JLabel("Fecha edición (dd-mm-aaaa)");
        jLabelFechaEdicion.setBounds(20, 100 ,170, 20);
        panelInterior.add(jLabelFechaEdicion);
        
        jTextFieldFechaEdicion = new JTextField();
        jTextFieldFechaEdicion.setBounds(190, 100, 80, 20);
        panelInterior.add(jTextFieldFechaEdicion);
        componentesJPanel[3] = jTextFieldFechaEdicion;
        
        jCheckBoxPremiado = new JCheckBox("Premiado");
        jCheckBoxPremiado.setBounds(20, 140 ,150, 20);
        panelInterior.add(jCheckBoxPremiado);
        componentesJPanel[4] = jCheckBoxPremiado;
        
        jLabelNumeroPaginas = new JLabel("Número páginas");
        jLabelNumeroPaginas.setBounds(20, 180 ,100, 20);
        panelInterior.add(jLabelNumeroPaginas);
        
        jTextFieldNumeroPaginas = new JTextField();
        jTextFieldNumeroPaginas.setBounds(124, 180, 43, 20);
        panelInterior.add(jTextFieldNumeroPaginas);
        componentesJPanel[6] = jTextFieldNumeroPaginas;                    
        
        jButtonAplicarCambios = new JButton("Aplicar cambios");
        jButtonAplicarCambios.setBounds(125, 340, 130, 40);
        jButtonAplicarCambios.setActionCommand("aplicarCambios");
        panelInterior.add(jButtonAplicarCambios);    
        
        jButtonCancelarEdicion = new JButton("Cancelar");
        jButtonCancelarEdicion.setBounds(125, 400, 130, 40);
        jButtonCancelarEdicion.setActionCommand("cancelarEdicionNodo");
        panelInterior.add(jButtonCancelarEdicion);         
        
        panelInterior.setBounds(1, 1, 760, 450);
        add(panelInterior);     
    }   
    

    @Override
    public void inicializarPostInstanciar(Controller controller) throws Exception {
        
        this.controller = controller;
        
        jTextFieldTitulo.addCaretListener(controller);
        jTextFieldFechaEdicion.addCaretListener(controller);
        jCheckBoxPremiado.addItemListener(controller);
        jTextFieldNumeroPaginas.addCaretListener(controller);
        jButtonAplicarCambios.addActionListener(controller);
        jButtonCancelarEdicion.addActionListener(controller);
    }
   
 
    public void setVistaArbol(VistaArbol vistaArbol) {
        this.vistaArbol = vistaArbol;
    }   
    
    
    public JPanel getPanelInterior(){
        return panelInterior;
    }
       

    public void inicializarPantalla(DefaultMutableTreeNodeHeredada nodoGeneroSeleccion, DefaultMutableTreeNodeHeredada nodoLibroSeleccion) throws Exception {
         
        this.nodoGeneroSeleccion = nodoGeneroSeleccion;
        this.nodoLibroSeleccion = nodoLibroSeleccion;                             
                                                                     
        if (nodoLibroSeleccion != null)   
          {        //   EDITAR NODO YA EXISTENTE
              visualizarLibroSeleccionado(nodoLibroSeleccion.getIdentificativo());
              actualizaciones = 0;
          }                
           else
          {        //   EDITAR NUEVO NODO
             inicializarComponentes();
          }
                                    
                   
    }    
    
    
    private void visualizarLibroSeleccionado(String idLibro) throws Exception {
         
        Libro libro = new Libro();
        libro.setIdLibro(idLibro);
        Libro libroObtenido = new LibrosNegocio().consultarPorIdLibro((BaseDatos)controller.getRepositorio()[0], libro);
        jTextFieldCodigo.setText(libroObtenido.getIdLibro());
        jTextFieldTitulo.setText(libroObtenido.getTitulo());
        jTextFieldFechaEdicion.setText(new SimpleDateFormat("dd-MM-yyyy").format(libroObtenido.getFechaEdicion()));    
        jCheckBoxPremiado.setSelected(libroObtenido.isPremiado());
        jTextFieldNumeroPaginas.setText(Integer.toString(libroObtenido.getNumeroPaginas()));
    }        
    
    
    private void inicializarComponentes() {
        
        jTextFieldCodigo.setText("");
        jTextFieldTitulo.setText("");
        jTextFieldFechaEdicion.setText("");               
        jCheckBoxPremiado.setSelected(false);
        jTextFieldNumeroPaginas.setText("");
    }       
 
 
    @Override
    public void responderAController(String actionCommand) throws Exception {
        
        switch(actionCommand)
        {   
             case "actualizadoTitulo" :
                    actualizaciones|=1;
                    break; 
             case "actualizadoFechaEdicion" :
                    actualizaciones|=4;
                    break;            
             case "actualizadoNumeroPaginas" :
                    actualizaciones|=8;
                    break;                     
             case "actualizadoPremiado" :
                    actualizaciones|=16;
                    break; 
             case "aplicarCambios" :                                                                 
                    Filtros.filtrarDatosNulos( new String[]{ jTextFieldTitulo.getText(),
                                                             jTextFieldFechaEdicion.getText(),
                                                             jTextFieldNumeroPaginas.getText()
                                                           }                         
                                              );
                    Filtros.filtrarFecha(jTextFieldFechaEdicion.getText());
                    Filtros.filtrarNumeroPaginasLibro(Integer.parseInt(jTextFieldNumeroPaginas.getText()));         
                    Libro libro = new Libro();
                    libro.setTitulo(jTextFieldTitulo.getText());
                    libro.setGenero(nodoGeneroSeleccion.getIdentificativo().substring(0, 1));
                    libro.setFechaEdicion(java.sql.Date.valueOf(jTextFieldFechaEdicion.getText().substring(6, 10) +"-"+
                                                                jTextFieldFechaEdicion.getText().substring(3, 5) +"-"+
                                                                jTextFieldFechaEdicion.getText().substring(0, 2)
                                                               ));
                    libro.setNumeroPaginas(Integer.parseInt(jTextFieldNumeroPaginas.getText()));
                    libro.setPremiado(jCheckBoxPremiado.isSelected());
                       
                    if (jTextFieldCodigo.getText().compareTo("") == 0)    
                       { 
                          libro.setIdLibro(new LibrosNegocio().insertar((BaseDatos)controller.getRepositorio()[0], libro));   
                          vistaArbol.insertarNodoLibro(libro);
                       }
                      else
                       { 
                          if (actualizaciones > 0)
                          {
                             libro.setIdLibro(jTextFieldCodigo.getText());
                             new LibrosNegocio().actualizar((BaseDatos)controller.getRepositorio()[0], libro, actualizaciones);
                             vistaArbol.modificarNodoLibro(libro, actualizaciones);
                          }
                       }    
                                        
                    vistaArbol.ocultarEdicionNodo();
                    break;                     
             case "cancelarEdicionNodo" :
                    vistaArbol.ocultarEdicionNodo();
                    break; 
        }        
    }
}


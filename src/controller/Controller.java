/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import application.GenericaExcepcion;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import persistence.BaseDatos;
import model.Contexto;
import application.GestorIncidencias;
import persistence.IncidenciasNegocio;
import persistence.RepositorioNegocio;
import view.ModeloDatos;
import view.PantallaOpcion;
import view.Menu;

/**
 *
 * @author jtech
 */
public class Controller extends WindowAdapter implements ActionListener, TableModelListener, ListSelectionListener, CaretListener, ItemListener, ChangeListener, MouseInputListener, ClipboardOwner{
    
        private Object[] repositorio;
        private Menu menu;
        private PantallaOpcion pantallaOpcion;
        private Contexto usuarioAutenticado = null;
        HashMap<String, Integer> actividadesCodificadas;
        private boolean registrarActualizadoNumeroPaginas = true;
        
      public Controller() {         
          try { 
                usuarioAutenticado = new Contexto(null, null, obtenerIP());
                repositorio = new RepositorioNegocio().cargarRepositorio();
                actividadesCodificadas = new IncidenciasNegocio().consultarCodificacionActividades((BaseDatos)repositorio[0]);              
       } catch (Exception exception)
           { new GestorIncidencias().gestionarExcepcion(exception, usuarioAutenticado); }       
      }
      

    private String obtenerIP() throws UnknownHostException {
        String[] cadenasIP = InetAddress.getLocalHost().getHostAddress().split("\\.");
        StringBuffer procesoIP = new StringBuffer(16);
        for (int i=0; i<cadenasIP.length; i++)
        {
           if (i>0)
               procesoIP.append(".");
           procesoIP.append(String.format("%03d", Integer.parseInt(cadenasIP[i])));
        }
    
        return new String(procesoIP);    
    }
    
           // MÉTODO DE WindowAdapter  
      public void windowClosing(WindowEvent e) { 
          centralizar("op_menu - CierreVentana");           
      }         
    
      
           // MÉTODO DE ActionListener    
      public void actionPerformed(ActionEvent e) {   

          //  Los actionCommand han de tener un mínimo de ocho posiciones para tener mayor longitud que "op_menu".
          String componenteFuente = e.getActionCommand();
          if (e.getSource() instanceof JMenuItem) 
              componenteFuente = ((JMenuItem) e.getSource()).getActionCommand();
        
          switch(componenteFuente)
          {                                    
              case "Conexion" :                                               
              case "ConexionEfectuada" :  
              case "Volcado a BD":     
              case "Vista Formulario": 
              case "Estadisticas Actividad": 
              case "Vista Unica Tabla": 
              case "Vista Paginada Tabla":  
              case "Vista Arbol": 
              case "Configurar Documento":
              case "Desconexion":
              case "CierreVentana":   
                      centralizar("op_menu - "+componenteFuente);                
                      break;                   
              case "autenticacion" :                     
              case "volcarIncidencias" :
              case "consultarEstadistica" : 
              case "copiarAlPortapapeles":
              case "comboLibros" : 
              case "nuevoLibro" :  
              case "aplicarCambios" :
              case "eliminarLibro" :
              case "insertarFila" :       
              case "cancelarInsercionFila" :    
              case "guardarFilaInsertada" : 
              case "eliminarFilaSeleccionada" :
              case "Nuevo nodo" :
              case "Editar nodo" :
              case "Eliminar nodo" :
              case "aplicarCambiosNodo" :
              case "cancelarEdicionNodo" :
              case "generarPDF" :
              case "imprimir" :    
                      centralizar(componenteFuente);                
                      break;                     
              case "ordenarPorIdentificador" :
                     pantallaOpcion.setComponenteJPanel(1, 10);
                     centralizar("reordenar");
                     break;                    
              case "ordenarPorTitulo" :   
                     pantallaOpcion.setComponenteJPanel(2, 10);
                     centralizar("reordenar");                  
                     break;                     
              case "ordenarPorGenero" :
                     pantallaOpcion.setComponenteJPanel(3, 10);
                     centralizar("reordenar");                   
                     break;                                                                                                                      
              case "botonPaginacionAnterior" :
                     centralizar(componenteFuente); 
                     break;           
              case "botonPaginacionSiguiente" :
                     centralizar(componenteFuente); 
                     break; 
              case "botonPaginacionNumerica" :
                     pantallaOpcion.setComponenteJPanel(new Integer(((JButton)e.getSource()).getText()), 16);
                     centralizar(componenteFuente); 
                     break;                     
          }
      }    
    
 
    
           // MÉTODO DE TableModelListener      
     public void tableChanged( TableModelEvent e ) 
      {    
           //   La invocación a estos métodos es efectiva cuando en el método setValueAt(Object valor,int fila, int columna )  
           //   de ModeloDatos se activa el evento mediante la invocación a fireTableCellUpdated(fila, columna);
           pantallaOpcion.setComponenteJPanel(e.getFirstRow(), 13);    //  Fila de celda actualizada en JTable. 
           pantallaOpcion.setComponenteJPanel(e.getColumn(), 14);      //  Columna de celda actualizada en JTable. 
           
           centralizar("actualizadaColumnaJTable"); 
    }
   
   
           // MÉTODO DE ListSelectionListener   
    public void valueChanged(ListSelectionEvent e)
    {
       //   Cuando se efectúa botón del ratón sobre una fila se intercepta el evento. Y cuando se suelta dicho botón, vuelve a interceptarse el evento.
       ListSelectionModel listSelectionModel = (ListSelectionModel)e.getSource(); 
     
      switch(pantallaOpcion.getClass().getName())
       {  case "view.VistaUnicaTabla": 
          case "view.VistaPaginadaTabla": 
                  if (!(e.getValueIsAdjusting()))
                  {
                      pantallaOpcion.setComponenteJPanel(listSelectionModel.getMinSelectionIndex(), 2);  // Modifica valor de fila seleccionada en JTable  
                      System.out.println("fila seleccionada en JTable  "+pantallaOpcion.getComponenteJPanel(2));
                  }
                  break;        
          case "view.VistaFormulario": 
                  if (e.getSource() == (pantallaOpcion.getComponenteJPanel(12)))
                    { 
                       centralizar("actualizadoGenero");               
                    }             
                  System.out.println("fila seleccionada en JList  "+listSelectionModel.getMinSelectionIndex());
                  System.out.println("dato actualizado  "+((String)((JList)pantallaOpcion.getComponenteJPanel(5)).getSelectedValue()));       
                  break;                          
       }
    }
  

          // MÉTODO DE CaretListener   
    public void caretUpdate(CaretEvent e)
    {          
      if (e.getSource() == (pantallaOpcion.getComponenteJPanel(2)))
        { 
           centralizar("actualizadoTitulo");            
        }
       else
         if (e.getSource() == (pantallaOpcion.getComponenteJPanel(3)))
           { 
              centralizar("actualizadoFechaEdicion");              
           }
          else
            if (e.getSource() == (pantallaOpcion.getComponenteJPanel(6)))
              { 
                 centralizar("actualizadoNumeroPaginas");              
              }      
      System.out.println("Dato actualizado :  "+((JTextField)e.getSource()).getText());         
    }

  
          // MÉTODO DE ItemListener 
    public void itemStateChanged(ItemEvent e)
    { 
      if (e.getItemSelectable() == (pantallaOpcion.getComponenteJPanel(4)))
        { 
           { 
              centralizar("actualizadoPremiado");      
           }           
        }
      System.out.println("Dato actualizado :  "+((JCheckBox)pantallaOpcion.getComponenteJPanel(4)).isSelected());
    }
  
 
          // MÉTODO DE ChangeListener  
    public void stateChanged(ChangeEvent e)
    {     
      if (e.getSource() == (pantallaOpcion.getComponenteJPanel(6)))
        { 
           centralizar("actualizadoNumeroPaginas");                           
        }
    
      System.out.println("Dato actualizado :  "+((JSlider)pantallaOpcion.getComponenteJPanel(6)).getValue());
    }
      
          // MÉTODO DE ClipboardOwner 
    public void lostOwnership(Clipboard clipb, Transferable transferable) {
      JOptionPane.showMessageDialog(null, "Otra aplicación ha reemplazado el contenido del portapapeles que había sido depositado al pulsar el Botón destinado a ello de esta aplicación", "AVISO", JOptionPane.INFORMATION_MESSAGE);
    }
     
          // MÉTODOS DE MouseInputListener    
    public void mouseClicked(MouseEvent e) {    
        pantallaOpcion.setComponenteJPanel(e.getX(), 13);    //  Fila de la posición en que se ha pulsado botón de ratón
        pantallaOpcion.setComponenteJPanel(e.getY(), 14);    //  Columna de la posición en que se ha pulsado botón de ratón  
        pantallaOpcion.setComponenteJPanel(e.getComponent(), 15);         //  Componente sobre el que se hace click con el ratón
        pantallaOpcion.setComponenteJPanel(e, 16);                        //  MouseEvent
        centralizar("ratonClicked");
    }
    
    public void mouseMoved(MouseEvent e) { 
    } 
    
    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }
    
    
    public void centralizar(String actionCommand) {
       
        try {
              //  El objetivo de la siguiente implementación es que el movimiento del JSlider solamante genere un registro en indicencias.
              if (actionCommand.compareTo("actualizadoNumeroPaginas") != 0  ||  registrarActualizadoNumeroPaginas)
              { 
                 usuarioAutenticado.setFechaHora(new java.util.Date());
                 new GestorIncidencias().gestionarActividad(usuarioAutenticado, actividadesCodificadas.get(actionCommand).intValue(), actionCommand);
              }
              
              if (actionCommand.compareTo("actualizadoNumeroPaginas") != 0)
                     registrarActualizadoNumeroPaginas = true;
                 else
                     registrarActualizadoNumeroPaginas = false;                  

              if (actionCommand.substring(0, 7).compareTo("op_menu") == 0)   // OPCION DE MENU
                {      
                   switch (actionCommand)
                   {
                       case "op_menu - Conexion":   
                       case "op_menu - ConexionEfectuada": 
                       case "op_menu - Volcado a BD":     
                       case "op_menu - Vista Formulario": 
                       case "op_menu - Estadisticas Actividad": 
                       case "op_menu - Vista Unica Tabla": 
                       case "op_menu - Vista Paginada Tabla":  
                       case "op_menu - Vista Arbol": 
                       case "op_menu - Configurar Documento":      
                              actionCommand = actionCommand.substring(10);
                              menu.responderAController(actionCommand);                          
                              break;
                       case "op_menu - Desconexion":
                              //  DESCONECTAR SESION DE USUARIO Y PRESENTAR PANTALLA DE CONEXION
                              usuarioAutenticado.setIdentificadorUsuario(null);
                              usuarioAutenticado.setPassword(null);
                              InvocacionAutomaticaMenu invocacionAutomaticaMenu = new InvocacionAutomaticaMenu();  
                              invocacionAutomaticaMenu.addEventoOpcionMenuListener(this);
                              invocacionAutomaticaMenu.fireEventoOpcionMenu("Conexion");   
                              break;
                       case "op_menu - CierreVentana":
                              System.exit(0);
                              break;                              
                   }
                }
              else    // RESPUESTA A EVENTO DISPARADO POR COMPONENTE U OPCION DE MENU DE Nodo DE VistaArbol
                { 
                   pantallaOpcion.responderAController(actionCommand);
                }            
            } catch (Exception exception)
               {       
                  usuarioAutenticado.setFechaHora(new java.util.Date());
                  new GestorIncidencias().gestionarExcepcion(exception, usuarioAutenticado);
                  if (actionCommand.compareTo("actualizadaColumnaJTable") == 0)
                  {
                     if (exception instanceof GenericaExcepcion)      
                     {
                        GenericaExcepcion genericaExcepcion = (GenericaExcepcion)exception;
                        if (genericaExcepcion.getCodigoError() <= 16)   //  CODIGOS DE ERROS DE GenericaExcepcion GENERADOS POR LOS METODOS filtrarFecha() O filtrarNumeroPaginasLibro() QUE IMPLICAN RESTAURAR A LA CELDA EL VALOR ANTERIOR
                        {  
                           ((ModeloDatos)pantallaOpcion.getComponenteJPanel(15)).getDatos()[((Integer)pantallaOpcion.getComponenteJPanel(13)).intValue()][((Integer)pantallaOpcion.getComponenteJPanel(14)).intValue()] = ((ModeloDatos)pantallaOpcion.getComponenteJPanel(15)).getCopiaReservaDato();
                           ((JTable)pantallaOpcion.getComponenteJPanel(0)).repaint();
                        }
                     }
                  
                  }
               }                
    }    
    
    
    public Object[] getRepositorio() {
        return repositorio;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public PantallaOpcion getPantallaOpcion() {
        return pantallaOpcion;
    }

    public void setPantallaOpcion(PantallaOpcion pantallaOpcion) {
        this.pantallaOpcion = pantallaOpcion;
    }

    public Contexto getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    public void setUsuarioAutenticado(Contexto usuarioAutenticado) {
        this.usuarioAutenticado = usuarioAutenticado;
    } 
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.Controller;
import java.awt.Color;
import java.awt.Font;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import model.ActividadUsuario;
import model.ParametrosSeleccionAgrupacion;
import persistence.BaseDatos;
import persistence.IncidenciasNegocio;

/**
 *
 * @author jtech
 */
public class EstadisticasActividad extends PantallaOpcion {

      private JLabel jLabelSeleccionAgrupaciones;
      private JCheckBox jCheckBoxSeleccionUsuario;
      private JCheckBox jCheckBoxSeleccionFecha;
      private JCheckBox jCheckBoxSeleccionActividad;
      private JButton jButtonConsultar;
      private JButton jButtonCopiarAlPortapapeles;
      private JLabel jLabelSeleccionUsuario;
      private JComboBox jComboBoxSeleccionUsuario;
      private JLabel jLabelSeleccionFechaInferior;
      private JComboBox jComboBoxSeleccionFechaInferior;
      private JLabel jLabelSeleccionFechaSuperior;
      private JComboBox jComboBoxSeleccionFechaSuperior;      
      private JTextArea jTextArea;
      private JScrollPane jScrollPaneTextarea; 
      private Clipboard clipboard;
      
    public EstadisticasActividad() {
        ubicarComponentes();
    }
  
    private void ubicarComponentes() {
        setLayout(null);
        
        jLabelSeleccionAgrupaciones = new JLabel("SELECCIONE AGRUPACIONES :");
        jLabelSeleccionAgrupaciones.setBounds(80, 50, 200, 20);
        add(jLabelSeleccionAgrupaciones);
        
        jCheckBoxSeleccionUsuario = new JCheckBox("Usuario");
        jCheckBoxSeleccionUsuario.setBounds(100,100,150,20);
        add(jCheckBoxSeleccionUsuario);
        componentesJPanel[0] = jCheckBoxSeleccionUsuario;
        
        jCheckBoxSeleccionFecha = new JCheckBox("Fecha");
        jCheckBoxSeleccionFecha.setBounds(100,150,150,20);
        add(jCheckBoxSeleccionFecha);
        componentesJPanel[1] = jCheckBoxSeleccionFecha;
        
        jCheckBoxSeleccionActividad = new JCheckBox("Actividad");
        jCheckBoxSeleccionActividad.setBounds(100,200,150,20);
        add(jCheckBoxSeleccionActividad);     
        componentesJPanel[2] = jCheckBoxSeleccionActividad;
        
        jLabelSeleccionUsuario = new JLabel("USUARIO :");
        jLabelSeleccionUsuario.setBounds(80, 350, 120, 20);
        add(jLabelSeleccionUsuario);
        
        jComboBoxSeleccionUsuario = new JComboBox();
        jComboBoxSeleccionUsuario.setBounds(210,350,370,20);
        jComboBoxSeleccionUsuario.setBackground(Color.white);
        add(jComboBoxSeleccionUsuario);
        componentesJPanel[3] = jComboBoxSeleccionUsuario;
        
        jLabelSeleccionFechaInferior = new JLabel("FECHA INFERIOR :");
        jLabelSeleccionFechaInferior.setBounds(80, 400, 120, 20);
        add(jLabelSeleccionFechaInferior);
        
        jComboBoxSeleccionFechaInferior = new JComboBox();
        jComboBoxSeleccionFechaInferior.setBounds(210,400,370,20);
        jComboBoxSeleccionFechaInferior.setBackground(Color.white);
        add(jComboBoxSeleccionFechaInferior);
        componentesJPanel[4] = jComboBoxSeleccionFechaInferior;        
        
        jLabelSeleccionFechaSuperior = new JLabel("FECHA SUPERIOR :");
        jLabelSeleccionFechaSuperior.setBounds(80, 450, 120, 20);
        add(jLabelSeleccionFechaSuperior);
        
        jComboBoxSeleccionFechaSuperior = new JComboBox();
        jComboBoxSeleccionFechaSuperior.setBounds(210,450,370,20);
        jComboBoxSeleccionFechaSuperior.setBackground(Color.white);
        add(jComboBoxSeleccionFechaSuperior);
        componentesJPanel[5] = jComboBoxSeleccionFechaSuperior;
        
        jTextArea = new JTextArea();
        jScrollPaneTextarea=new JScrollPane(jTextArea);
        jScrollPaneTextarea.setBounds(600, 50, 550, 730);
        add(jScrollPaneTextarea);
        jTextArea.setEditable(false);
        jTextArea.setFont(new Font("TimesRoman",Font.PLAIN,11));   
               
        jButtonConsultar = new JButton("Consultar estadística");
        jButtonConsultar.setBounds(200,580,180,40);
        jButtonConsultar.setActionCommand("consultarEstadistica");
        add(jButtonConsultar);       
        
        jButtonCopiarAlPortapapeles = new JButton("Copiar al portapapeles");
        jButtonCopiarAlPortapapeles.setBounds(200,640,180,40);
        jButtonCopiarAlPortapapeles.setActionCommand("copiarAlPortapapeles");
        add(jButtonCopiarAlPortapapeles);   
        
        clipboard = getToolkit().getSystemClipboard();
    }   

    @Override
    public void inicializarPostInstanciar(Controller controller) throws Exception {
        
        this.controller = controller;     

        jComboBoxSeleccionUsuario.removeAllItems();
        jComboBoxSeleccionUsuario.addItem(" ");       
        List<String> listaUsuariosConActividad = new IncidenciasNegocio().consultarUsuariosConActividad((BaseDatos)controller.getRepositorio()[0]);
        for (int i=0; i<listaUsuariosConActividad.size(); i++)
            jComboBoxSeleccionUsuario.addItem(listaUsuariosConActividad.get(i));
        jComboBoxSeleccionUsuario.setSelectedIndex(0);
      
        List<String> listaFechasConActividad;
        jComboBoxSeleccionFechaInferior.removeAllItems();
        jComboBoxSeleccionFechaInferior.addItem(" ");
        listaFechasConActividad = new IncidenciasNegocio().consultarFechasConActividad((BaseDatos)controller.getRepositorio()[0]);
        for (int i=0; i<listaFechasConActividad.size(); i++)
            jComboBoxSeleccionFechaInferior.addItem(listaFechasConActividad.get(i));      
        jComboBoxSeleccionFechaInferior.setSelectedIndex(0);  
            
        jComboBoxSeleccionFechaSuperior.removeAllItems();
        jComboBoxSeleccionFechaSuperior.addItem(" ");
        listaFechasConActividad = new IncidenciasNegocio().consultarFechasConActividad((BaseDatos)controller.getRepositorio()[0]);
        for (int i=0; i<listaFechasConActividad.size(); i++)
            jComboBoxSeleccionFechaSuperior.addItem(listaFechasConActividad.get(i));
        jComboBoxSeleccionFechaSuperior.setSelectedIndex(0); 
       
        jButtonCopiarAlPortapapeles.setEnabled(false);
        
        jButtonConsultar.addActionListener(controller);   
        jButtonCopiarAlPortapapeles.addActionListener(controller);
    }    
    
    
    @Override
    public void responderAController(String actionCommand) throws Exception {
          switch(actionCommand)
          {   
             case "consultarEstadistica" :
                    //   RECOGER PARAMETROS DE AGRUPACION Y SELECCION DESDEL EL JPanel
                    ParametrosSeleccionAgrupacion parametrosSeleccionAgrupacion = new ParametrosSeleccionAgrupacion(); 
                    //   Recoger parámetros de agrupación
                    for (int i=0; i<parametrosSeleccionAgrupacion.getCriteriosAgrupacion().length; i++)
                    {  
                        parametrosSeleccionAgrupacion.setCriterioAgrupacion(((JCheckBox)componentesJPanel[i]).isSelected(), i); 
                    }
                    //   Recoger parámetros de selección
                    for (int i=0; i<parametrosSeleccionAgrupacion.getCriteriosSeleccion().length; i++)
                    {                           
                        if (((JComboBox)componentesJPanel[i+3]).getSelectedItem().toString().compareTo(" ") == 0)
                                parametrosSeleccionAgrupacion.setCriterioSeleccion(null, i);
                            else
                                parametrosSeleccionAgrupacion.setCriterioSeleccion(((JComboBox)componentesJPanel[i+3]).getSelectedItem().toString(), i);
                    }
                                             
                    visualizarConsultaEnJTextArea(new IncidenciasNegocio().consultarAplicandoCriteriosSeleccion((BaseDatos)controller.getRepositorio()[0], parametrosSeleccionAgrupacion),  parametrosSeleccionAgrupacion);      
                    jButtonCopiarAlPortapapeles.setEnabled(true);
                  break;   
             case "copiarAlPortapapeles" : 
                    clipboard.setContents(new StringSelection(jTextArea.getText()), controller); 
                  break;                 
          }
    }
    
    
    private void visualizarConsultaEnJTextArea(List<ActividadUsuario> listaActividadUsuario, ParametrosSeleccionAgrupacion parametrosSeleccionAgrupacion) {
        
        boolean conAgrupaciones = false;
        for (int i=0; i<parametrosSeleccionAgrupacion.getCriteriosAgrupacion().length; i++)
        {
           if (parametrosSeleccionAgrupacion.isCriterioAgrupacion(i))
               conAgrupaciones = true;
        }    
                           
        jTextArea.setText("");
        for (int i=0; i<listaActividadUsuario.size(); i++)
        {
           ActividadUsuario actividadUsuario = listaActividadUsuario.get(i);
           int contadorCriteriosAgrupacion = 0;
           if (parametrosSeleccionAgrupacion.isCriterioAgrupacion(contadorCriteriosAgrupacion)  ||  !conAgrupaciones)
           {
              jTextArea.append("USUARIO:     "+String.format("%-20s",actividadUsuario.getIdentificadorUsuario())+"\n");                             
           }
           contadorCriteriosAgrupacion ++; 
           if (parametrosSeleccionAgrupacion.isCriterioAgrupacion(contadorCriteriosAgrupacion)  ||  !conAgrupaciones)
           {
              jTextArea.append("FECHA:     "+String.format("%-20s",actividadUsuario.getCadenaFechaHora())+"\n");
           }
           contadorCriteriosAgrupacion ++; 
           if (parametrosSeleccionAgrupacion.isCriterioAgrupacion(contadorCriteriosAgrupacion)  ||  !conAgrupaciones)
           {
              jTextArea.append("CODIGO ACTIVIDAD:     "+String.format("%10s",actividadUsuario.getCodigoActividad())+"\n");
              jTextArea.append("DESCRIPCION:     "+String.format("%-52s",actividadUsuario.getDescripcion())+"\n");
           }
           if (conAgrupaciones)
           {
              jTextArea.append("NUMERO DE ACTUACIONES:     "+String.format("%10s",actividadUsuario.getNumeroIncidencias())+"\n");
           }
           jTextArea.append("----------------------------------------------------------------------------\n");
        }    
    }    
}


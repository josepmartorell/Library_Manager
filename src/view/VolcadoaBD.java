/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.Controller;
import javax.swing.JButton;
import persistence.BaseDatos;
import persistence.IncidenciasNegocio;

/**
 *
 * @author jtech
 */
public class VolcadoaBD extends PantallaOpcion {

      private JButton jButtonVolcar;
      
    public VolcadoaBD() {
        ubicarComponentes();
    }
  
    private void ubicarComponentes() {
        setLayout(null);
        jButtonVolcar = new JButton("Volcar a BD");
        jButtonVolcar.setBounds(340,280,120,40);
        jButtonVolcar.setActionCommand("volcarIncidencias");
        add(jButtonVolcar); 
    }   
    

    @Override
    public void inicializarPostInstanciar(Controller controller) throws Exception {
        this.controller = controller;        
        jButtonVolcar.addActionListener(controller);        
    }    
    
    
    @Override
    public void responderAController(String actionCommand) throws Exception {
          switch(actionCommand)
          {   
               case "volcarIncidencias" :
                      new IncidenciasNegocio().volcarFichero((BaseDatos)controller.getRepositorio()[0]);
                      break;       
          }
    }
}


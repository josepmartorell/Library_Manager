/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.Controller;
import javax.swing.JPanel;

/**
 *
 * @author jtech
 */
public abstract class PantallaOpcion extends JPanel{
    
    protected Controller controller;
    protected Object[] componentesJPanel = new Object[20];

     public abstract void inicializarPostInstanciar(Controller controller) throws Exception;
     public abstract void responderAController(String actionCommand) throws Exception;
    
     public void inicializarPantalla() throws Exception {
     }

     public Object getComponenteJPanel(int component) {
         return componentesJPanel[component];
     }

     public void setComponenteJPanel(Object object, int component) {
         componentesJPanel[component] = object;
     }
     
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import controller.Controller;
import java.awt.Font;
import javax.swing.JLabel;

/**
 *
 * @author jtech
 */
    
public class ConexionEfectuada extends PantallaOpcion {
      
       private JLabel jLabelUsuarioConectado;
       
    public ConexionEfectuada() {
        ubicarComponentes();
    }
  
    private void ubicarComponentes() {
        setLayout(null);
        jLabelUsuarioConectado = new JLabel();
        jLabelUsuarioConectado.setBounds(400, 300, 3000, 20);
        jLabelUsuarioConectado.setFont(new Font("TimesRoman", Font.BOLD, 20));
        add(jLabelUsuarioConectado);
    }   
    

    public void inicializarPostInstanciar(Controller controller) throws Exception { 
        this.controller = controller;
        jLabelUsuarioConectado.setText("Está conectado como usuario "+controller.getUsuarioAutenticado().getIdentificadorUsuario());
    }    
    
    
    public void responderAController(String actionCommand) throws Exception {
    }    
}
    


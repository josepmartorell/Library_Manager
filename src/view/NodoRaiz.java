/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author jtech
 */
public class NodoRaiz extends DefaultMutableTreeNodeHeredada {
    
     private String identificativo = "";

  public NodoRaiz(String cadenaVisualizada)
   {
     super(cadenaVisualizada);
   }  

     @Override
  public void setIdentificativo(String identificativo)
   {
     this.identificativo = identificativo;
   }

     @Override
  public String getIdentificativo()
   {
     return identificativo;
   }     
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author jtech
 */
public class NodoGenero extends DefaultMutableTreeNodeHeredada {

     private String identificativo = "";

  public NodoGenero(String cadenaVisualizada)
   {
     super(cadenaVisualizada);
   }  

  public void setIdentificativo(String identificativo)
   {
     this.identificativo = identificativo;
   }

  public String getIdentificativo()
   {
     return identificativo;
   }     
}

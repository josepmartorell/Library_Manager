/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author jtech
 */
public class NodoLibro extends DefaultMutableTreeNodeHeredada {
    
     private String identificativo = "";
     private String titulo = "";

  public NodoLibro(String cadenaVisualizada)
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

  public void setTitulo(String titulo)
   {
     this.titulo = titulo;
   }

  public String getTitulo()
   {
     return titulo;
   }
}

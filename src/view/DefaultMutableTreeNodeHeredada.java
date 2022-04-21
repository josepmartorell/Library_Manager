/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package view;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author jtech
 */
public abstract class DefaultMutableTreeNodeHeredada extends DefaultMutableTreeNode {
    
  DefaultMutableTreeNodeHeredada(String cadenaVisualizada)
   {
     super(cadenaVisualizada);
   }

  abstract void setIdentificativo(String identificativo);
  abstract String getIdentificativo();    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application;

/**
 *
 * @author jtech
 */
public class GenericaExcepcion extends Exception{
    
        private final int codigoError;
         
    public GenericaExcepcion (int codigoError){
         this.codigoError = codigoError;
    }
    
    public int getCodigoError(){
         return codigoError;
    }
}
    


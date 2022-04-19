/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistence;

/**
 *
 * @author jtech
 */
public class RepositorioNegocio {
        public Object[] cargarRepositorio() throws Exception
    {
        return new RepositorioXML().cargar();
    } 
    
}

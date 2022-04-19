/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistence;

import application.GenericaExcepcion;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author jtech
 */
public class ConexionBaseDatos {
    public Connection abrirConexion(BaseDatos baseDatos) throws Exception 
    {
        Connection connection = null;  
        try {        
             Class.forName(baseDatos.getClassDriver());
             connection = java.sql.DriverManager.getConnection(baseDatos.getUrlConexion(), baseDatos.getUsuario(), baseDatos.getPassword());  
        } catch (SQLException excepcion) {          
            throw new GenericaExcepcion(25);
        }          
        return connection;         
    }
    
    public  void cerrarConexion(Connection connection) throws GenericaExcepcion
    {
        try {
             if (connection!= null) 
                connection.close();    
        } catch (SQLException excepcion) {          
            throw new GenericaExcepcion(25);
        }    
    } 
    
}

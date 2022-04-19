/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistence;

import application.GenericaExcepcion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author jtech
 */
public class LibrosSecuencia {
    public String consultarValorSecuencia(Connection connection, BaseDatos baseDatos) throws Exception
    {
        String valorSecuencia = null;
        ResultSet resultSet = null;
        Statement statement = null; 

        String sql = "";

        switch(baseDatos.getNombre())
        {
            case "Oracle": sql = "SELECT REPLACE(TO_CHAR(secuencia_libros.NEXTVAL,'09999'),' ') FROM DUAL";
                           break;
            case "MySQL":  sql = "SELECT LPAD(FORMAT(secuencia_next_valor(\"secuencia_libros\"),0),5,'0')"; 
                           break;                       
        }           

        try {
            statement = connection.createStatement(); 
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
               valorSecuencia = resultSet.getString(1);
                }   
            } catch (SQLException excepcion) {
                throw new GenericaExcepcion(100);
            } finally
            {
                if (resultSet != null) resultSet.close(); 
                if (statement != null) statement.close();
            }    
        return valorSecuencia;
    }       
    
}

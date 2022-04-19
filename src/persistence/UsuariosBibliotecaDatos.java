/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistence;

import application.GenericaExcepcion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Contexto;

/**
 *
 * @author jtech
 */
public class UsuariosBibliotecaDatos {
    
    public Contexto buscarPorIdUsuario(Connection connection, Contexto contexto) throws Exception
    {
        Contexto usuarioObtenido = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
                String sql = "SELECT * FROM usuarios_biblioteca WHERE id_usuario = CAST(? AS CHAR(15))";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, contexto.getIdentificadorUsuario());
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                   usuarioObtenido = new Contexto();
                   usuarioObtenido.setIdentificadorUsuario(resultSet.getString(1));
                   usuarioObtenido.setPassword(resultSet.getString(2));      
                }
            } catch (SQLException excepcion) {
                throw new GenericaExcepcion(60);
            } finally
            {
                if (resultSet != null) resultSet.close(); 
                if (preparedStatement != null) preparedStatement.close();
            }

        return usuarioObtenido;
    } 
    
}

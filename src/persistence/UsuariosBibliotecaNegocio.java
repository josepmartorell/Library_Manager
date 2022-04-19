/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistence;

import application.GenericaExcepcion;
import java.sql.Connection;
import model.Contexto;

/**
 *
 * @author jtech
 */
public class UsuariosBibliotecaNegocio {
    
        public Contexto autenticar(BaseDatos baseDatos, Contexto usuarioIntroducido) throws Exception
    {
            Connection connection = null;
            Contexto usuarioAutenticado = null;
            ConexionBaseDatos conexionBaseDatos = new ConexionBaseDatos();      
      try {         
            connection = conexionBaseDatos.abrirConexion(baseDatos);  
            usuarioAutenticado = new UsuariosBibliotecaDatos().buscarPorIdUsuario(connection, usuarioIntroducido);
            if (usuarioAutenticado == null)
                   throw new GenericaExcepcion(20);      

            if (usuarioAutenticado.getPassword().compareTo(usuarioIntroducido.getPassword()) != 0)
                   throw new GenericaExcepcion(21);  

            usuarioAutenticado.setIpCliente(usuarioIntroducido.getIpCliente());
          } catch (Exception excepcion)
              { 
                throw excepcion; 
              }   
            finally
              {
                 conexionBaseDatos.cerrarConexion(connection);         
              }     

        return usuarioAutenticado;
    } 
    
}

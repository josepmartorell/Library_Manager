/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistence;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import model.ActividadUsuario;
import model.Contexto;
import model.ParametrosSeleccionAgrupacion;

/**
 *
 * @author jtech
 */
public class IncidenciasNegocio {
    public void escribirFichero(int codigo, String mensaje, String nombreFichero, Contexto contexto) throws IOException {
       new IncidenciasDatos().escribirFichero(codigo, mensaje, nombreFichero, contexto);
    }     
    
    
    public void volcarFichero(BaseDatos baseDatos) throws Exception {
        
        IncidenciasDatos incidenciasDatos = new IncidenciasDatos();
        
        Connection connection = null;
        ConexionBaseDatos conexionBaseDatos = new ConexionBaseDatos(); 
        ActividadUsuariosDatos actividadUsuariosDatos = new ActividadUsuariosDatos();
  try {         
        connection = conexionBaseDatos.abrirConexion(baseDatos);
        
        if (incidenciasDatos.comprobarExistenciaFichero())
        {
           List<String> listaLineas = incidenciasDatos.leerFichero();
        
           for (int i=0; i<listaLineas.size(); i++)
           { 
               String lineaLeida = listaLineas.get(i);
               ActividadUsuario actividadUsuario = new ActividadUsuario();            
               actividadUsuario.setFechaHora(java.sql.Timestamp.valueOf(lineaLeida.substring(0, 19)));
               actividadUsuario.setIdentificadorUsuario(lineaLeida.substring(21, 36));
               actividadUsuario.setIpCliente(lineaLeida.substring(39, 55));
               //   Acumulación solamente de caracteres numéricos desde la subcadena correspondiente al codigo
               StringBuffer stringBufferCodigo = new StringBuffer();
               for (int k=0; k<lineaLeida.substring(59, 62).length(); k++) 
               {
                   if (lineaLeida.substring(59, 62).charAt(k) != ' ')
                       stringBufferCodigo.append(lineaLeida.substring(59, 62).charAt(k)); 
               }            
               actividadUsuario.setCodigoActividad(Integer.parseInt(new String(stringBufferCodigo)));   
               actividadUsuariosDatos.insertar(connection, actividadUsuario); 
           }   
        
           incidenciasDatos.eliminarFichero();
        }
      } catch (Exception excepcion)
          { 
            throw excepcion; 
          }   
        finally
          {
             conexionBaseDatos.cerrarConexion(connection);         
          }            
    }   
    
    
public HashMap<String, Integer> consultarCodificacionActividades(BaseDatos baseDatos) throws Exception
 {
    Connection connection=null;       
    ConexionBaseDatos conexionBaseDatos = new ConexionBaseDatos();
    HashMap<String, Integer> actividadesCodificadas = null;
    
  try {
        connection = conexionBaseDatos.abrirConexion(baseDatos);                              
        actividadesCodificadas = new ActividadUsuariosDatos().consultarCodificacionActividades(connection);
      } catch (Exception excepcion)
          {  
            throw excepcion; 
          }   
        finally
          {
             conexionBaseDatos.cerrarConexion(connection);         
          }   
  
     return actividadesCodificadas;                                 
 }  

    
public List<ActividadUsuario> consultarAplicandoCriteriosSeleccion(BaseDatos baseDatos, ParametrosSeleccionAgrupacion parametrosSeleccionAgrupacion) throws Exception
 {
    Connection connection=null;       
    ConexionBaseDatos conexionBaseDatos = new ConexionBaseDatos();
    List<ActividadUsuario> listaActividadUsuario = null;
    
  try {
        connection = conexionBaseDatos.abrirConexion(baseDatos);                              
        listaActividadUsuario = new ActividadUsuariosDatos().consultarConCriteriosSeleccionAgrupacion(connection, parametrosSeleccionAgrupacion, baseDatos);                  
      } catch (Exception excepcion)
          {  
            throw excepcion; 
          }   
        finally
          {
             conexionBaseDatos.cerrarConexion(connection);         
          }   
  
     return listaActividadUsuario;                                 
 }  


public List<String> consultarUsuariosConActividad(BaseDatos baseDatos) throws Exception
 {
    Connection connection=null;       
    ConexionBaseDatos conexionBaseDatos = new ConexionBaseDatos();
    List<String> listaUsuarios = null;
    
  try {
        connection = conexionBaseDatos.abrirConexion(baseDatos);                              
        listaUsuarios = new ActividadUsuariosDatos().consultarUsuariosConActividad(connection);                  
      } catch (Exception excepcion)
          {  
            throw excepcion; 
          }   
        finally
          {
             conexionBaseDatos.cerrarConexion(connection);         
          }   
  
     return listaUsuarios;                                 
 }  


public List<String> consultarFechasConActividad(BaseDatos baseDatos) throws Exception
 {
    Connection connection=null;       
    ConexionBaseDatos conexionBaseDatos = new ConexionBaseDatos();
    List<String> listaFechas = null;
    
  try {
        connection = conexionBaseDatos.abrirConexion(baseDatos);                              
        listaFechas = new ActividadUsuariosDatos().consultarFechasConActividad(connection, baseDatos);                  
      } catch (Exception excepcion)
          {  
            throw excepcion; 
          }   
        finally
          {
             conexionBaseDatos.cerrarConexion(connection);         
          }   
  
     return listaFechas;                                 
 }
    
}

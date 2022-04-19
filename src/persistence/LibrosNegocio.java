/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistence;

import printer.LibrosPDF;
import presentacion.ParametrosPaginacion;
import printer.ParametrosListado;
import printer.LibrosImpresora;
import java.sql.Connection;
import java.util.List;
import model.Genero;
import model.Libro;

/**
 *
 * @author jtech
 */
public class LibrosNegocio {
    
    public String insertar(BaseDatos baseDatos, Libro libro) throws Exception{
            Connection connection = null;
            ConexionBaseDatos conexionBaseDatos = new ConexionBaseDatos();
      try {         
            connection = conexionBaseDatos.abrirConexion(baseDatos); 
            libro.setIdLibro(new LibrosSecuencia().consultarValorSecuencia(connection, baseDatos));
            new LibrosDatos().insertar(connection, libro);
          } catch (Exception excepcion)
              { 
                throw excepcion; 
              }   
            finally
              {
                 conexionBaseDatos.cerrarConexion(connection);         
              }    

      return libro.getIdLibro();
    }   



    public void eliminar(BaseDatos baseDatos, Libro libro) throws Exception{
            Connection connection = null;
            ConexionBaseDatos conexionBaseDatos = new ConexionBaseDatos();
      try {         
            connection = conexionBaseDatos.abrirConexion(baseDatos);  
            new LibrosDatos().eliminar(connection, libro);
          } catch (Exception excepcion)
              { 
                throw excepcion; 
              }   
            finally
              {
                 conexionBaseDatos.cerrarConexion(connection);         
              }                                
        }   


    public void actualizar(BaseDatos baseDatos, Libro libro, int actualizaciones) throws Exception{
            Connection connection = null;
            ConexionBaseDatos conexionBaseDatos = new ConexionBaseDatos();
      try {         
            connection = conexionBaseDatos.abrirConexion(baseDatos); 
            if (actualizaciones == -1)
                  new LibrosDatos().actualizarColumna(connection, libro);
               else
                  new LibrosDatos().actualizar(connection, libro, actualizaciones);
          } catch (Exception excepcion)
              { 
                throw excepcion; 
              }   
            finally
              {
                 conexionBaseDatos.cerrarConexion(connection);         
              }                                
        }   


    public Libro consultarPorIdLibro(BaseDatos baseDatos, Libro libro) throws Exception
    {
            Connection connection = null;
            Libro libroObtenido = null;
            ConexionBaseDatos conexionBaseDatos = new ConexionBaseDatos();      
      try {         
            connection = conexionBaseDatos.abrirConexion(baseDatos);  
            libroObtenido = new LibrosDatos().consultarPorIdLibro(connection, libro);
          } catch (Exception excepcion)
              { 
                throw excepcion; 
              }   
            finally
              {
                 conexionBaseDatos.cerrarConexion(connection);         
              }     

        return libroObtenido;
    }       


    public List<Libro> consultarTodos(BaseDatos baseDatos, int criterioOrdenacion, String genero, LimitesListado limitesListado) throws Exception
     { 
        Connection connection=null;       
        ConexionBaseDatos conexionBaseDatos = new ConexionBaseDatos();
        List<Libro> listaLibros = null;

      try {
            connection = conexionBaseDatos.abrirConexion(baseDatos);                              
            listaLibros = new LibrosDatos().consultarTodos(connection, criterioOrdenacion, genero, limitesListado);
          } catch (Exception excepcion)
              {  
                throw excepcion; 
              }   
            finally
              {
                 conexionBaseDatos.cerrarConexion(connection);         
              }   

         return listaLibros;                                 
     }  


    public List<Libro> consultarPagina(BaseDatos baseDatos, int criterioOrdenacion, ParametrosPaginacion parametrosPaginacion) throws Exception
     {
        Connection connection=null;       
        ConexionBaseDatos conexionBaseDatos = new ConexionBaseDatos();
        List<Libro> listaLibros = null;

      try {
            connection = conexionBaseDatos.abrirConexion(baseDatos);                              
            listaLibros = new LibrosDatos().consultarPagina(connection, criterioOrdenacion, parametrosPaginacion, baseDatos);
          } catch (Exception excepcion)
              {  
                throw excepcion; 
              }   
            finally
              {
                 conexionBaseDatos.cerrarConexion(connection);         
              }   

         return listaLibros;                                 
     }  


    public Integer consultarNumeroFilas(BaseDatos baseDatos) throws Exception
     {
        Connection connection=null;       
        ConexionBaseDatos conexionBaseDatos = new ConexionBaseDatos();
        Integer numFilas = null;

      try {
            connection = conexionBaseDatos.abrirConexion(baseDatos);                           
            numFilas = new LibrosDatos().consultarNumeroFilas(connection);
          } catch (Exception excepcion)
              {  
                throw excepcion; 
              }   
            finally
              {
                 conexionBaseDatos.cerrarConexion(connection);         
              }   

         return numFilas;                                 
     }      


    public String[] consultarGeneros(BaseDatos baseDatos) throws Exception
     {
        Connection connection=null;       
        ConexionBaseDatos conexionBaseDatos = new ConexionBaseDatos();
        List<Genero> listaGeneros = null;
        String[] generos = null;

      try {
            connection = conexionBaseDatos.abrirConexion(baseDatos);                              
            listaGeneros = new LibrosDatos().consultarGeneros(connection);
            generos = new String[listaGeneros.size()];
            for (int i=0; i<listaGeneros.size(); i++)
            {
               Genero genero = listaGeneros.get(i);
               generos[i] = genero.getCodigo()+"  -  "+genero.getDescripcion();
            }
          } catch (Exception excepcion)
              {  
                throw excepcion; 
              }   
            finally
              {
                 conexionBaseDatos.cerrarConexion(connection);         
              }   

         return generos;                                 
     }  


    public void generarPDF(List<Libro> listaLibros, ParametrosListado parametrosListado, String directorioCreacionPDFs) throws Exception
     {
         new LibrosPDF().generarPDF(listaLibros, parametrosListado, directorioCreacionPDFs);             
     }   


    public void imprimir(List<Libro> listaLibros, ParametrosListado parametrosListado) throws Exception
     {
         new LibrosImpresora().imprimir(listaLibros, parametrosListado);             
     }
    
}

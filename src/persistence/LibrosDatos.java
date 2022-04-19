/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistence;

import view.ParametrosPaginacion;
import application.GenericaExcepcion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Genero;
import model.Libro;

/**
 *
 * @author jtech
 */
public class LibrosDatos {
    
    public void insertar(Connection connection, Libro libro) throws Exception
    {   PreparedStatement preparedStatement = null;
        try {    
                String sql = "INSERT INTO libros VALUES(?,?,?,?,?,?)"; 
                preparedStatement = connection.prepareStatement(sql); 
                preparedStatement.setString(1, libro.getIdLibro());
                preparedStatement.setString(2, libro.getTitulo());
                preparedStatement.setString(3, libro.getGenero());
                preparedStatement.setDate(4, libro.getFechaEdicion());
                preparedStatement.setInt(5, libro.getNumeroPaginas());
                byte premiado = 0;
                if (libro.isPremiado())
                    premiado = 1;               
                preparedStatement.setByte(6, premiado);
                preparedStatement.executeUpdate();
            } catch (SQLException excepcion) {           
                throw new GenericaExcepcion(50);
            } finally
            {
                if (preparedStatement != null) preparedStatement.close();
            }    
    }       


    public void eliminar(Connection connection, Libro libro) throws Exception
    {   PreparedStatement preparedStatement = null;
        try {    
                String sql = "DELETE FROM libros WHERE id_libro = CAST(? AS CHAR(5))"; 
                preparedStatement = connection.prepareStatement(sql);                   
                preparedStatement.setString(1, libro.getIdLibro()); 
                preparedStatement.executeUpdate();
            } catch (SQLException excepcion) {           
                throw new GenericaExcepcion(51);
            } finally
            {
                if (preparedStatement != null) preparedStatement.close();
            }    
    }       


    public void actualizarColumna(Connection connection, Libro libro) throws Exception
    {   PreparedStatement preparedStatement = null;
        try {    
                String sql = "UPDATE libros SET ";
                switch(libro.getColumnaActualizada())
                {  case 1: sql += "titulo = ?"; 
                           break;
                   case 2: sql += "genero = ?";
                           break;                       
                   case 3: sql += "fecha_edicion = ?";
                           break;                        
                   case 4: sql += "numero_paginas = ?";
                           break; 
                   case 5: sql += "premiado = ?";
                           break;                        
                }

                sql += " WHERE id_libro = CAST(? AS CHAR(5))";

                preparedStatement = connection.prepareStatement(sql);   

                switch(libro.getColumnaActualizada())
                {  case 1: preparedStatement.setString(1, (String)libro.getDatoActualizado());
                           break;
                   case 2: preparedStatement.setString(1, (String)libro.getDatoActualizado());
                           break;    
                   case 3: preparedStatement.setDate(1, java.sql.Date.valueOf(((String)libro.getDatoActualizado()).substring(6, 10) +"-"+ ((String)libro.getDatoActualizado()).substring(3, 5) +"-"+ ((String)libro.getDatoActualizado()).substring(0, 2)));
                           break;                        
                   case 4: preparedStatement.setInt(1, (Integer)libro.getDatoActualizado());
                           break; 
                   case 5: byte premiado = 0;
                           if (((Boolean)libro.getDatoActualizado()).booleanValue())
                               premiado = 1;  
                           preparedStatement.setByte(1, premiado);                         
                           break;                        
                }

                preparedStatement.setString(2, libro.getIdLibro());            
                preparedStatement.executeUpdate();  
            } catch (SQLException excepcion) {           
                throw new GenericaExcepcion(52);
            } finally
            {
                if (preparedStatement != null) preparedStatement.close();
            }    
    }     


    public String concatenarSeparador(boolean iniciado) {
        String separador = "";
        if (iniciado)
            separador = ", ";
        return separador;
    }


    public void actualizar(Connection connection, Libro libro, int actualizaciones) throws Exception
    {   PreparedStatement preparedStatement = null;
        try {    
                boolean iniciado = false;
                String sql = "UPDATE libros SET ";

                for (int i=1; i<=16384; i*=2)
                 { int pesoDelBit = (int) actualizaciones & i;
                   switch (i)
                     { case 1 : if (pesoDelBit > 0)
                                 {  sql += concatenarSeparador(iniciado);                            
                                    sql += "titulo = ?";
                                    iniciado = true;                                                              
                                 }
                                break;
                       case 2 : if (pesoDelBit > 0)
                                 {  sql += concatenarSeparador(iniciado);                            
                                    sql += "genero = ?";
                                    iniciado = true;                                                              
                                 }
                                break;
                       case 4 : if (pesoDelBit > 0)
                                 {  sql += concatenarSeparador(iniciado);                            
                                    sql += "fecha_edicion = ?";
                                    iniciado = true;                                                              
                                 }
                                break;                            
                       case 8 : if (pesoDelBit > 0)
                                 {  sql += concatenarSeparador(iniciado);                            
                                    sql += "numero_paginas = ?";
                                    iniciado = true;                                                              
                                 }
                                break;                            
                       case 16 : if (pesoDelBit > 0)
                                 {  sql += concatenarSeparador(iniciado);                            
                                    sql += "premiado = ?";
                                    iniciado = true;                                                              
                                 }
                                break;   
                     }
                 }

                sql += " WHERE id_libro = CAST(? AS CHAR(5))";
       System.out.println(sql);                            
                preparedStatement = connection.prepareStatement(sql);   

                int contadorActualizaciones = 1;

                for (int i=1; i<=16384; i*=2)
                 { int pesoDelBit = (int) actualizaciones & i;
                   switch (i)
                     { case 1 : if (pesoDelBit > 0)
                                 {  preparedStatement.setString(contadorActualizaciones, libro.getTitulo());
                                    contadorActualizaciones++;                                                              
                                 }
                                break;
                       case 2 : if (pesoDelBit > 0)
                                 {  preparedStatement.setString(contadorActualizaciones, libro.getGenero());
                                    contadorActualizaciones++;                                                              
                                 }
                                break;
                       case 4 : if (pesoDelBit > 0)
                                 {  preparedStatement.setDate(contadorActualizaciones, libro.getFechaEdicion());
                                    contadorActualizaciones++;                                                              
                                 }
                                break;                            
                       case 8 : if (pesoDelBit > 0)
                                 {  preparedStatement.setInt(contadorActualizaciones, libro.getNumeroPaginas());
                                    contadorActualizaciones++;                                                              
                                 }
                                break;                            
                       case 16 : if (pesoDelBit > 0)
                                 {  byte premiado = 0;
                                    if (libro.isPremiado())
                                        premiado = 1;               
                                    preparedStatement.setByte(contadorActualizaciones, premiado);
                                    contadorActualizaciones++;                                                              
                                 }
                                break;   
                     }
                 }

                preparedStatement.setString(contadorActualizaciones, libro.getIdLibro());            
                preparedStatement.executeUpdate();  
            } catch (SQLException excepcion) {           
                throw new GenericaExcepcion(53);
            } finally
            {
                if (preparedStatement != null) preparedStatement.close();
            }    
    }     


    public Libro consultarPorIdLibro(Connection connection, Libro libro) throws Exception
    {
        Libro libroObtenido = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
                String sql = "SELECT id_libro, titulo, generos.codigo, generos.descripcion, fecha_edicion, numero_paginas, premiado FROM generos INNER JOIN libros ON generos.codigo = libros.genero WHERE id_libro = CAST(? AS CHAR(5))";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, libro.getIdLibro());
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                   libroObtenido = new Libro();
                   libroObtenido.setIdLibro(resultSet.getString(1));
                   libroObtenido.setTitulo(resultSet.getString(2));
                   libroObtenido.setGenero(resultSet.getString(3));
                   libroObtenido.setDescripcion(resultSet.getString(4));
                   libroObtenido.setFechaEdicion(resultSet.getDate(5));
                   libroObtenido.setNumeroPaginas(resultSet.getInt(6));
                   byte premiado = resultSet.getByte(7);                  
                   if (premiado == 1)
                       libroObtenido.setPremiado(true);
                   else
                       libroObtenido.setPremiado(false);                               
                }
            } catch (SQLException excepcion) {
                throw new GenericaExcepcion(54);
            } finally
            {
                if (resultSet != null) resultSet.close(); 
                if (preparedStatement != null) preparedStatement.close();
            }

        return libroObtenido;
    }      


    public List<Libro> consultarTodos(Connection connection, int criterioOrdenacion, String genero, LimitesListado limitesListado) throws Exception
    {
        List<Libro> listaLibros = new ArrayList();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
                String sql = "SELECT id_libro, titulo, generos.codigo, generos.descripcion, fecha_edicion, numero_paginas, premiado FROM generos INNER JOIN libros ON generos.codigo = libros.genero ";

                if (genero != null)
                    sql += "WHERE generos.codigo = ? ";

                if (limitesListado != null)
                {
                    if (genero == null)
                        sql += "WHERE ";
                      else
                        sql += "AND ";

                    switch (criterioOrdenacion)
                    {
                       case 1 : sql += "id_libro >= ? AND id_libro <= ? ";
                                break;
                       case 2 : sql += "titulo >= ? AND titulo <= ? ";
                                break;
                       case 3 : sql += "genero >= ? AND genero <= ? ";
                                break;                        
                    }                  
                }

                switch (criterioOrdenacion)
                {
                   case 1 : sql += "ORDER BY id_libro";
                            break;
                   case 2 : sql += "ORDER BY titulo";
                            break;
                   case 3 : sql += "ORDER BY genero, titulo";
                            break;                        
                }

                preparedStatement = connection.prepareStatement(sql);

                int contadorParametros = 1;
                if (genero != null)
                {
                    preparedStatement.setString(contadorParametros, genero);
                    contadorParametros++;
                }

                if (limitesListado != null)
                {
                    preparedStatement.setString(contadorParametros, limitesListado.getLimiteInferior());
                    contadorParametros++;
                    preparedStatement.setString(contadorParametros, limitesListado.getLimiteSuperior());
                    // contadorParametros++;                
                }            

                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                   Libro libro = new Libro();
                   libro.setIdLibro(resultSet.getString(1));
                   libro.setTitulo(resultSet.getString(2));
                   libro.setGenero(resultSet.getString(3));
                   libro.setDescripcion(resultSet.getString(4));
                   libro.setFechaEdicion(resultSet.getDate(5));
                   libro.setNumeroPaginas(resultSet.getInt(6));
                   if (resultSet.getInt(7) == 1)
                        libro.setPremiado(true);
                     else
                        libro.setPremiado(false);
                   listaLibros.add(libro);
                }
            } catch (SQLException excepcion) {
                throw new GenericaExcepcion(55);
            } finally
            {
                if (resultSet != null) resultSet.close(); 
                if (preparedStatement != null) preparedStatement.close();
            }

        return listaLibros;
    }  


    public List<Libro> consultarPagina(Connection connection, int criterioOrdenacion, ParametrosPaginacion parametrosPaginacion, BaseDatos baseDatos) throws Exception
    {
        List<Libro> listaLibros = new ArrayList();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
                String clausulaORDERBY = "";
                switch (criterioOrdenacion)
                {
                   case 1 : clausulaORDERBY = "ORDER BY id_libro";
                            break;
                   case 2 : clausulaORDERBY = "ORDER BY titulo";
                            break;
                   case 3 : clausulaORDERBY = "ORDER BY genero";
                            break;                        
                }            

                String sql = "";

                switch(baseDatos.getNombre())
                {
                   case "Oracle": sql = "SELECT id_libro, titulo, generos_codigo, generos_descripcion, fecha_edicion, numero_paginas, premiado, num_fila FROM (SELECT id_libro, titulo, generos_codigo, generos_descripcion, fecha_edicion, numero_paginas, premiado, rownum AS num_fila FROM (SELECT id_libro, titulo, generos.codigo AS generos_codigo, generos.descripcion AS generos_descripcion, fecha_edicion, numero_paginas, premiado FROM generos INNER JOIN libros ON generos.codigo = libros.genero "+ clausulaORDERBY +" )) WHERE num_fila>=((?-1)*?)+1 AND num_fila<=(?*?)";
                                  break;
                   case "MySQL":  sql = "SELECT id_libro, titulo, generos.codigo, generos.descripcion, fecha_edicion, numero_paginas, premiado FROM generos INNER JOIN libros ON generos.codigo = libros.genero "+ clausulaORDERBY +" LIMIT ?,?";  
                                  break;                       
                }

                preparedStatement = connection.prepareStatement(sql);

                switch(baseDatos.getNombre())
                {
                    case "Oracle": preparedStatement.setInt(1, parametrosPaginacion.getNumeroPagina());
                                   preparedStatement.setInt(2, parametrosPaginacion.getNumeroFilasPagina()); 
                                   preparedStatement.setInt(3, parametrosPaginacion.getNumeroPagina());
                                   preparedStatement.setInt(4, parametrosPaginacion.getNumeroFilasPagina());
                                   break;
                    case "MySQL":  preparedStatement.setInt(1, (parametrosPaginacion.getNumeroPagina() - 1) * (parametrosPaginacion.getNumeroFilasPagina()));
                                   preparedStatement.setInt(2, parametrosPaginacion.getNumeroFilasPagina()); 
                                   break;                       
                }            

                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                   Libro libro = new Libro();
                   libro.setIdLibro(resultSet.getString(1));
                   libro.setTitulo(resultSet.getString(2));
                   libro.setGenero(resultSet.getString(3));
                   libro.setDescripcion(resultSet.getString(4));
                   libro.setFechaEdicion(resultSet.getDate(5));
                   libro.setNumeroPaginas(resultSet.getInt(6));
                   if (resultSet.getInt(7) == 1)
                        libro.setPremiado(true);
                     else
                        libro.setPremiado(false);
                   listaLibros.add(libro);
                }
            } catch (SQLException excepcion) {
                throw new GenericaExcepcion(56);
            } finally
            {
                if (resultSet != null) resultSet.close(); 
                if (preparedStatement != null) preparedStatement.close();
            }

        return listaLibros;
    }  


    public Integer consultarNumeroFilas(Connection connection) throws Exception
    {
        Integer numFilas = null;
        ResultSet resultSet = null;
        Statement statement = null; 
        String sql = "SELECT COUNT(*) FROM libros";

        try {
                statement = connection.createStatement(); 
                resultSet = statement.executeQuery(sql);
                if (resultSet.next()) {
                   numFilas = resultSet.getInt(1);          
                }   
            } catch (SQLException excepcion) {
                throw new GenericaExcepcion(57);
            } finally
            {
                if (resultSet != null) resultSet.close(); 
                if (statement != null) statement.close();
            }

        return numFilas;
    }    


    public List<Genero> consultarGeneros(Connection connection) throws Exception
    {
        List<Genero> listaGeneros = new ArrayList();
        ResultSet resultSet = null;
        Statement statement = null;
        try {
                String sql = "SELECT * FROM generos ORDER BY codigo ";
                statement = connection.createStatement();
                resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                   Genero genero = new Genero();
                   genero.setCodigo(resultSet.getString(1));
                   genero.setDescripcion(resultSet.getString(2));
                   listaGeneros.add(genero);
                }
            } catch (SQLException excepcion) {
                throw new GenericaExcepcion(58);
            } finally
            {
                if (resultSet != null) resultSet.close(); 
                if (statement != null) statement.close();
            }

        return listaGeneros;
    } 
    
}

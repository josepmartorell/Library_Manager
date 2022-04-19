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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.ActividadUsuario;
import model.ParametrosSeleccionAgrupacion;

/**
 *
 * @author jtech
 */
public class ActividadUsuariosDatos {
    
    public void insertar(Connection connection, ActividadUsuario actividadUsuario) throws Exception
    {   PreparedStatement preparedStatement = null;
        try {    
                String sql = "INSERT INTO actividad_usuarios VALUES(?,?,?,?)"; 
                preparedStatement = connection.prepareStatement(sql);                   
                preparedStatement.setTimestamp(1, actividadUsuario.getFechaHora()); 
                preparedStatement.setString(2, actividadUsuario.getIdentificadorUsuario());
                preparedStatement.setString(3, actividadUsuario.getIpCliente());
                preparedStatement.setInt(4, actividadUsuario.getCodigoActividad());

                preparedStatement.executeUpdate();
            } catch (SQLException excepcion) {           
                throw new GenericaExcepcion(40);
            } finally
            {
                if (preparedStatement != null) preparedStatement.close();
            }    
    }    


    public HashMap<String, Integer> consultarCodificacionActividades(Connection connection) throws Exception
    {
        HashMap<String, Integer> actividadesCodificadas = new HashMap();
        ResultSet resultSet = null;
        Statement statement = null;
        try {
                String sql = "SELECT descripcion, codigo FROM codificacion_actividades";

                statement = connection.createStatement();
                resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                   actividadesCodificadas.put(resultSet.getString(1), resultSet.getInt(2));
                }
            } catch (SQLException excepcion) {
                throw new GenericaExcepcion(55);
            } finally
            {
                if (resultSet != null) resultSet.close(); 
                if (statement != null) statement.close();
            }

        return actividadesCodificadas;
    }  


    public List<String> consultarUsuariosConActividad(Connection connection) throws Exception
    {
        List<String> listaUsuarios = new ArrayList();
        ResultSet resultSet = null;
        Statement statement = null;
        try {
                String sql = "SELECT DISTINCT id_usuario FROM actividad_usuarios ORDER BY id_usuario";
                statement = connection.createStatement();
                resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                   listaUsuarios.add(resultSet.getString(1));
                }
            } catch (SQLException excepcion) {
                throw new GenericaExcepcion(41);
            } finally
            {
                if (resultSet != null) resultSet.close(); 
                if (statement != null) statement.close();
            }

        return listaUsuarios;
    }  


    public List<String> consultarFechasConActividad(Connection connection, BaseDatos baseDatos) throws Exception
    {
        List<String> listaFechas = new ArrayList();
        ResultSet resultSet = null;
        Statement statement = null;
        try {
                String sql = "";

                switch(baseDatos.getNombre())
                {
                    case "Oracle": sql = "SELECT DISTINCT TO_CHAR(fecha_hora,'YYYY-MM-DD') FROM actividad_usuarios ORDER BY TO_CHAR(fecha_hora,'YYYY-MM-DD')";
                                   break;
                    case "MySQL":  sql = "SELECT DISTINCT DATE_FORMAT(fecha_hora, '%Y-%m-%d') FROM actividad_usuarios ORDER BY DATE_FORMAT(fecha_hora, '%Y-%m-%d')";
                                   break;                       
                }                 

                statement = connection.createStatement();
                resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                   listaFechas.add(resultSet.getString(1));
                }
            } catch (SQLException excepcion) {
                throw new GenericaExcepcion(42);
            } finally
            {
                if (resultSet != null) resultSet.close(); 
                if (statement != null) statement.close();
            }

        return listaFechas;
    }  



    private boolean detectarExistenciaAgrupaciones(ParametrosSeleccionAgrupacion parametrosSeleccionAgrupacion) {
        boolean conAgrupaciones = false;

        for (int i=0; i<parametrosSeleccionAgrupacion.getCriteriosAgrupacion().length; i++)
        {
            if (parametrosSeleccionAgrupacion.isCriterioAgrupacion(i))
                conAgrupaciones = true;
        }

        return conAgrupaciones;    
    }


    private String construirExpresionesADevolver(ParametrosSeleccionAgrupacion parametrosSeleccionAgrupacion, BaseDatos baseDatos) 
    {
        String[][] expresionesADevolver = {{"id_usuario", "TO_CHAR(fecha_hora,'YYYY-MM-DD')", "codificacion_actividades.codigo", "descripcion"},
                                           {"id_usuario", "DATE_FORMAT(fecha_hora, '%Y-%m-%d')", "codificacion_actividades.codigo", "descripcion"}
                                          };

        int indiceBD = 0;
        switch(baseDatos.getNombre())
        {
            case "Oracle": indiceBD = 0;
                           break;
            case "MySQL":  indiceBD = 1;
                           break;                       
        }           

        StringBuffer construccionSQL = new StringBuffer(120); 

        boolean conAgrupaciones = detectarExistenciaAgrupaciones(parametrosSeleccionAgrupacion);

        int contadorExpresiones = 0;
        for (int i=0; i<(expresionesADevolver[indiceBD].length-1); i++ )        
        {    
            if (parametrosSeleccionAgrupacion.isCriterioAgrupacion(i)  ||  !conAgrupaciones)
            {
               if (contadorExpresiones > 0)
                   construccionSQL.append(",");
               construccionSQL.append(expresionesADevolver[indiceBD][i]); 
               contadorExpresiones ++;
            }
        }
        if (parametrosSeleccionAgrupacion.isCriterioAgrupacion((parametrosSeleccionAgrupacion.getCriteriosAgrupacion().length-1))  ||  !conAgrupaciones)
        {
           construccionSQL.append(",");
           construccionSQL.append(expresionesADevolver[indiceBD][(expresionesADevolver[indiceBD].length-1)]);
        }         
        if (conAgrupaciones)
          {       
              construccionSQL.append(",");   
              construccionSQL.append("COUNT(*)");          
          }

        return new String(construccionSQL);
    }


    private String construirClausulaWHERE(ParametrosSeleccionAgrupacion parametrosSeleccionAgrupacion, BaseDatos baseDatos) 
    { 
        String[][] condicionesConsulta = {{"id_usuario = CAST(? AS CHAR(15))", "TO_CHAR(fecha_hora,'YYYY-MM-DD') >= ?", "TO_CHAR(fecha_hora,'YYYY-MM-DD') <= ?"},
                                          {"id_usuario = CAST(? AS CHAR(15))", "DATE_FORMAT(fecha_hora, '%Y-%m-%d') >= ?", "DATE_FORMAT(fecha_hora, '%Y-%m-%d') <= ?"}
                                         };

        int indiceBD = 0;
        switch(baseDatos.getNombre())
        {
            case "Oracle": indiceBD = 0;
                           break;
            case "MySQL":  indiceBD = 1;
                           break;                       
        }      

        StringBuffer construccionSQL = new StringBuffer(120);            

        int contadorSelecciones = 0;

        for (int i=0; i<condicionesConsulta[indiceBD].length; i++)
        {
            if (parametrosSeleccionAgrupacion.getCriterioSeleccion(i) != null)
            {
               construccionSQL.append(obtenerOperador(contadorSelecciones));
               construccionSQL.append(condicionesConsulta[indiceBD][i]);           
               contadorSelecciones++;
            }
        }

        return new String(construccionSQL);
    }


    private String obtenerOperador(int contadorSelecciones)
    { 
        String operadorDevuelto="";

        if (contadorSelecciones == 0)
            operadorDevuelto = " WHERE ";
          else
            operadorDevuelto = " AND ";

        return operadorDevuelto;
    }


    private String construirClausulaGROUPBY(ParametrosSeleccionAgrupacion parametrosSeleccionAgrupacion, BaseDatos baseDatos) 
    {
        String[][] expresionesADevolver = {{"id_usuario", "TO_CHAR(fecha_hora,'YYYY-MM-DD')", "codificacion_actividades.codigo", "descripcion"},
                                           {"id_usuario", "DATE_FORMAT(fecha_hora, '%Y-%m-%d')", "codificacion_actividades.codigo", "descripcion"}
                                          };

        int indiceBD = 0;
        switch(baseDatos.getNombre())
        {
            case "Oracle": indiceBD = 0;
                           break;
            case "MySQL":  indiceBD = 1;
                           break;                       
        }       

        StringBuffer construccionSQL = new StringBuffer(120); 

        boolean conAgrupaciones = detectarExistenciaAgrupaciones(parametrosSeleccionAgrupacion);

        if (conAgrupaciones)
          {
              boolean ubicarSeparador = false;
              construccionSQL.append(" GROUP BY ");
              for (int i=0; i<(expresionesADevolver[indiceBD].length-1); i++ )        
              {    
                  if (parametrosSeleccionAgrupacion.isCriterioAgrupacion(i))
                  {
                     if (i>0 && ubicarSeparador)
                         construccionSQL.append(",");
                     construccionSQL.append(expresionesADevolver[indiceBD][i]); 
                     ubicarSeparador = true;
                  }
              }
              if (parametrosSeleccionAgrupacion.isCriterioAgrupacion((parametrosSeleccionAgrupacion.getCriteriosAgrupacion().length-1)))
              {
                 construccionSQL.append(",");
                 construccionSQL.append(expresionesADevolver[indiceBD][(expresionesADevolver[indiceBD].length-1)]);
              };          
          }

        return new String(construccionSQL);
    }


    private String construirClausulaORDERBY(ParametrosSeleccionAgrupacion parametrosSeleccionAgrupacion, BaseDatos baseDatos) 
    {
        String[][] expresionesADevolver = {{"id_usuario", "TO_CHAR(fecha_hora,'YYYY-MM-DD')", "codificacion_actividades.codigo"},
                                           {"id_usuario", "DATE_FORMAT(fecha_hora, '%Y-%m-%d')", "codificacion_actividades.codigo"}
                                          };

        int indiceBD = 0;
        switch(baseDatos.getNombre())
        {
            case "Oracle": indiceBD = 0;
                           break;
            case "MySQL":  indiceBD = 1;
                           break;                       
        }       

        StringBuffer construccionSQL = new StringBuffer(120); 

        construccionSQL.append(" ORDER BY ");

        boolean conAgrupaciones = detectarExistenciaAgrupaciones(parametrosSeleccionAgrupacion);

        int contadorExpresiones = 0;
        for (int i=0; i<(expresionesADevolver[indiceBD].length); i++ )        
        {    
            if (parametrosSeleccionAgrupacion.isCriterioAgrupacion(i)  ||  !conAgrupaciones)
            {
               if (contadorExpresiones > 0)
                   construccionSQL.append(",");
               construccionSQL.append(expresionesADevolver[indiceBD][i]); 
               contadorExpresiones ++;
            }
        }

        return new String(construccionSQL);
    }


    public List<ActividadUsuario> consultarConCriteriosSeleccionAgrupacion(Connection connection, ParametrosSeleccionAgrupacion parametrosSeleccionAgrupacion, BaseDatos baseDatos) throws Exception
    { 
        List<ActividadUsuario> listaActividadUsuario = new ArrayList();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;  

        String sql = "SELECT "+construirExpresionesADevolver(parametrosSeleccionAgrupacion, baseDatos)+" FROM codificacion_actividades INNER JOIN actividad_usuarios ON codificacion_actividades.codigo = actividad_usuarios.codigo " + construirClausulaWHERE(parametrosSeleccionAgrupacion, baseDatos) + construirClausulaGROUPBY(parametrosSeleccionAgrupacion, baseDatos) + construirClausulaORDERBY(parametrosSeleccionAgrupacion, baseDatos) ;
        System.out.println(sql);  
        try {        
                preparedStatement = connection.prepareStatement(sql);

                int contadorSelecciones = 1;            

                for (int i=0; i<parametrosSeleccionAgrupacion.getCriteriosSeleccion().length; i++)
                {
                    if (parametrosSeleccionAgrupacion.getCriterioSeleccion(i) != null)
                    {
                       preparedStatement.setString(contadorSelecciones, parametrosSeleccionAgrupacion.getCriterioSeleccion(i));
                       contadorSelecciones ++;
                    }
                }            

                resultSet = preparedStatement.executeQuery();  

                boolean conAgrupaciones = false;
                for (int i=0; i<parametrosSeleccionAgrupacion.getCriteriosAgrupacion().length; i++)
                {
                    if (parametrosSeleccionAgrupacion.isCriterioAgrupacion(i))
                        conAgrupaciones = true;
                }

                ActividadUsuario actividadUsuario = null;
                while (resultSet.next()) {      
                   int contadorCriteriosAgrupacion = 0;
                   int contadorColumnasDevueltas = 1;                

                   actividadUsuario = new ActividadUsuario();
                   if (parametrosSeleccionAgrupacion.isCriterioAgrupacion(contadorCriteriosAgrupacion)  ||  !conAgrupaciones)
                   {  
                      actividadUsuario.setIdentificadorUsuario(resultSet.getString(contadorColumnasDevueltas));
                      contadorColumnasDevueltas ++;
                   }
                   contadorCriteriosAgrupacion ++;
                   if (parametrosSeleccionAgrupacion.isCriterioAgrupacion(contadorCriteriosAgrupacion)  ||  !conAgrupaciones)
                   {
                      actividadUsuario.setCadenaFechaHora(resultSet.getString(contadorColumnasDevueltas));
                      contadorColumnasDevueltas ++;
                   }
                   contadorCriteriosAgrupacion ++;               
                   if (parametrosSeleccionAgrupacion.isCriterioAgrupacion(contadorCriteriosAgrupacion)  ||  !conAgrupaciones)
                   {
                      actividadUsuario.setCodigoActividad(resultSet.getInt(contadorColumnasDevueltas));
                      contadorColumnasDevueltas ++;
                      actividadUsuario.setDescripcion(resultSet.getString(contadorColumnasDevueltas));
                      contadorColumnasDevueltas ++;
                   }
                   if (conAgrupaciones)
                        actividadUsuario.setNumeroIncidencias(resultSet.getInt(contadorColumnasDevueltas));

                   listaActividadUsuario.add(actividadUsuario);         
                }          

            } catch (SQLException excepcion) {
                throw new GenericaExcepcion(43);
            } finally
            {
                if (resultSet != null) resultSet.close(); 
                if (preparedStatement != null) preparedStatement.close();
            }

        return listaActividadUsuario;
    }  
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import application.GenericaExcepcion;
import javax.swing.JList;

/**
 *
 * @author jtech
 */
public class Filtros {
    public static void filtrarNumeroPaginasLibro(int numeroPaginas) throws GenericaExcepcion {   
      if (numeroPaginas < 1 || numeroPaginas > 2000)
           throw new GenericaExcepcion(16);
    }    
 
    
    
    public static void filtrarFecha(String cadenaFecha) throws GenericaExcepcion { 
              
      int codigoError = 0;      
      int dia = 0; 
      int mes = 0;
      int año = 0;

      if (cadenaFecha.length() == 10  &&  cadenaFecha.charAt(2) == '-'  &&  cadenaFecha.charAt(5) == '-')
      {   
        try {  
              dia = Integer.parseInt(cadenaFecha.substring(0, 2));
              mes = Integer.parseInt(cadenaFecha.substring(3, 5));          
              año = Integer.parseInt(cadenaFecha.substring(6, cadenaFecha.length()));
            } catch (NumberFormatException excepcion)
              { codigoError |= 1; }              // dígito no numérico en fecha
      }
      else
      {   
          codigoError |= 1;                     // formato fecha erróneo
      }
        
      if (codigoError == 0)
      {
         if (Integer.toString(año).length() != 4)            // año erróneo
             codigoError |= 2;    
         
         if (mes < 1 || mes > 12)                            // mes erróneo
             codigoError |= 4;
          
         if (dia < 1 || dia > getDiaMaximoMes(mes, año))    // dia erróneo
             codigoError |= 8;
      }     
        System.out.println("codigo error fecha "+codigoError);           
      if (codigoError > 0)
          throw new GenericaExcepcion(codigoError);           
    }

    
    private static int getDiaMaximoMes(int mes, int año) { 
        int diaMaximoMes = 31;

        switch(mes)
        {  case 1:
           case 3:
           case 5:
           case 7:
           case 8:
           case 10:
           case 12: diaMaximoMes = 31;
                    break;
           case 4:
           case 6:
           case 9:
           case 11: diaMaximoMes = 30;
                    break;
           case 2: if (((año % 4 == 0) && !(año % 100 == 0)) || (año % 400 == 0))
                       diaMaximoMes = 29;
                     else
                       diaMaximoMes = 28;
                   break;
        }

       return diaMaximoMes; 
    }


    
    public static void filtrarDatosNulos(String[] datosAFiltrar) throws GenericaExcepcion {   
        
       boolean existenDatosNulos = false;
       
        for (String datosAFiltrar1 : datosAFiltrar) {
            if (datosAFiltrar1.compareTo("") == 0) {
                existenDatosNulos = true;
            }
        }
        
      if (existenDatosNulos)
           throw new GenericaExcepcion(17);
    }     
    
    
    public static void filtrarSeleccionJList(JList jList) throws GenericaExcepcion {   
        
       boolean noExisteFilaSeleccionada = false;
       
       if (jList.isSelectionEmpty())
            noExisteFilaSeleccionada = true;

       if (noExisteFilaSeleccionada)
            throw new GenericaExcepcion(18);
    }
    
}

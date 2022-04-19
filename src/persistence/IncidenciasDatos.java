/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.Contexto;

/**
 *
 * @author jtech
 */
public class IncidenciasDatos {
    
    public void escribirFichero(int codigo, String mensaje, String nombreFichero, Contexto contexto) throws IOException {
                  
        File file = new File("log/"+nombreFichero+".txt");
        if (!file.exists())
            file.createNewFile();
        FileWriter fileWriter = new FileWriter(file, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        
        String identificadorUsuario = " ";
        if (contexto.getIdentificadorUsuario() != null)
            identificadorUsuario = contexto.getIdentificadorUsuario();
             
        printWriter.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(contexto.getFechaHora()) +"  "+ String.format("%-18s", identificadorUsuario) + String.format("%-18s",contexto.getIpCliente()) + String.format("%5s",codigo)+ "  "+String.format("%-110s",mensaje));
             
        if (printWriter != null)
             printWriter.close();             
        if (fileWriter != null)
             fileWriter.close();    
    }    
    
    
    public List<String> leerFichero() throws IOException {
        
        List<String> listaLineas = new ArrayList();
        String cadenaLeida;
        FileReader fileReader = new FileReader(new File("log/actividad.txt"));
        BufferedReader bufferedReader = new BufferedReader(fileReader);         
        while ((cadenaLeida = bufferedReader.readLine()) != null) {
            listaLineas.add(cadenaLeida);
        }
        if (fileReader != null)
             fileReader.close();
             
        return listaLineas;
    }  
    
    
    public boolean comprobarExistenciaFichero() {
        boolean existeFichero = false;
        File file = new File("log/actividad.txt");
        if (file.exists())
            existeFichero = true;
        
        return existeFichero;        
    }       
    
    
    
    public void eliminarFichero() throws IOException {
        
        new File("log/actividad.txt").delete();         
    }
    
}

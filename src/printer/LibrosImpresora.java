/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package printer;

import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.List;
import model.Libro;

/**
 *
 * @author jtech
 */
public class LibrosImpresora {
    
    public void imprimir(List<Libro> listaLibros, ParametrosListado parametrosListado) throws PrinterException {    
        
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        Book book = new Book();
        PageFormat pageFormat = new PageFormat(); 
       // pageFormat.setOrientation(PageFormat.LANDSCAPE);      //  CASO DE REQUERIRSE FORMATO HORIZONTAL
        Paper paper = new Paper();
        //      SI SE REQUIRIESE UN MAYOR TAMAÑO DE PAPEL, APLICARIAMOS:
        //  paper.setSize(750.0,850.0);  
        paper.setImageableArea(1, 1, 610, 790);  //  ESTABLECEMOS ESTOS VALORES DADO QUE EL TAMAÑO POR DEFECTO DEL PAPEL ES:  612, 792
        pageFormat.setPaper(paper);          
     
        Libro[] lineasPagina = new Libro[parametrosListado.getNumeroFilasPagina()];
        int numeroPagina = 1;
        int contadorLineas = 0;
      
        inicializarLineasPagina(lineasPagina);
        
        for (int i=0; i<listaLibros.size(); i++)
        {
            lineasPagina[contadorLineas] = listaLibros.get(i);
            contadorLineas++;
            if (contadorLineas == parametrosListado.getNumeroFilasPagina())
            {  
               book.append(new ImpresorPagina(lineasPagina, numeroPagina++), pageFormat); 
               inicializarLineasPagina(lineasPagina);
               contadorLineas = 0;
            }        
        }
        
        if (contadorLineas > 0)
             book.append(new ImpresorPagina(lineasPagina, numeroPagina++), pageFormat); 
        
        printerJob.setPageable(book);
        if (printerJob.printDialog())
             printerJob.print();
    } 
    
    
    private void inicializarLineasPagina(Libro[] lineasPagina) {
        for (int i=0; i<lineasPagina.length; i++)
            lineasPagina[i] = null;
    }
    
}

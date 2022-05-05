/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package printer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import model.Libro;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 *
 * @author jtech
 */
public class LibrosPDF {
    
    public void generarPDF(List<Libro> listaLibros, ParametrosListado parametrosListadoPDF, String directorioCreacionPDFs) throws IOException{    
        
        PDDocument pDDocument = new PDDocument();     
        Libro[] lineasPagina = new Libro[parametrosListadoPDF.getNumeroFilasPagina()];
        int numeroPagina = 1;
        int contadorLineas = 0;
      
        inicializarLineasPagina(lineasPagina);
        
        for (int i=0; i<listaLibros.size(); i++)
        {
            lineasPagina[contadorLineas] = listaLibros.get(i);
            contadorLineas++;
            if (contadorLineas == parametrosListadoPDF.getNumeroFilasPagina())
            {  
               escribirPagina(pDDocument, lineasPagina, numeroPagina++);
               inicializarLineasPagina(lineasPagina);
               contadorLineas = 0;
            }        
        }
        
        if (contadorLineas > 0)
             escribirPagina(pDDocument, lineasPagina, numeroPagina++);; 
        
        String nombreArchivoPDF = directorioCreacionPDFs + "\\Listado_libros"+ new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()) + ".pdf";
        pDDocument.save(nombreArchivoPDF);
        pDDocument.close();                         
    }
    

    private void inicializarLineasPagina(Libro[] lineasPagina) {
        for (int i=0; i<lineasPagina.length; i++)
            lineasPagina[i] = null;
    }

    
    private void escribirPagina(PDDocument pDDocument, Libro[] lineasPagina, int numeroPagina) throws IOException  {
        PDPage pDPage = new PDPage();
        pDDocument.addPage(pDPage);
        PDPageContentStream pDPageContentStream = new PDPageContentStream(pDDocument, pDPage);
        
        escribirCabecera(pDDocument, pDPageContentStream);
        int contadorLineas = escribirCuerpo(pDPageContentStream, lineasPagina);
        escribirPie(pDPageContentStream, numeroPagina, contadorLineas);
        
        pDPageContentStream.close();
    }

    
    private void escribirCabecera(PDDocument pDDocument, PDPageContentStream pDPageContentStream) throws IOException {

               //  LOGO          
        PDImageXObject imagen = PDImageXObject.createFromFile("imgs/logo1.jpg", pDDocument);          
        pDPageContentStream.drawImage(imagen, 50, 690);
        escribirTexto(pDPageContentStream, PDType1Font.TIMES_BOLD, 12, 150, 730, "BIBLIOTECA MUNICIPAL DE VILLAR DEL MONTE");
        escribirTexto(pDPageContentStream, PDType1Font.TIMES_BOLD, 10, 460, 705, "Fecha :");
        escribirTexto(pDPageContentStream, PDType1Font.TIMES_ROMAN, 10, 500, 705, new java.text.SimpleDateFormat("dd-MM-yyyy").format(new java.util.Date())); 
        escribirLinea(pDPageContentStream, 30, 680, 580, 680);
        escribirTexto(pDPageContentStream, PDType1Font.TIMES_BOLD, 10, 52, 662, "Código");
        escribirTexto(pDPageContentStream, PDType1Font.TIMES_BOLD, 10, 93, 662, "Título");
        escribirTexto(pDPageContentStream, PDType1Font.TIMES_BOLD, 10, 382, 662, "Género");
        escribirLinea(pDPageContentStream, 30, 650, 580, 650);
    }
    

    private int escribirCuerpo(PDPageContentStream pDPageContentStream, Libro[] lineasPagina) throws IOException {

      int i;
      for (i=0; i<lineasPagina.length; i++)
         if (lineasPagina[i] != null)
             escribirTexto(pDPageContentStream, PDType1Font.COURIER, 8, 55, 635-(15*(i)), String.format("%-8s", lineasPagina[i].getIdLibro()) + String.format("%-60s", lineasPagina[i].getTitulo()) + lineasPagina[i].getGenero() + " - " + String.format("%-32s", lineasPagina[i].getDescripcion()));                
      
       return i;
    }
         
    
     private void escribirPie(PDPageContentStream pDPageContentStream, int numeroPagina, int contadorLineas) throws IOException {

        escribirLinea(pDPageContentStream, 30, 640-(15*(contadorLineas)), 580, 640-(15*(contadorLineas)));
        escribirTexto(pDPageContentStream, PDType1Font.TIMES_ROMAN, 8, 520, 625-(15*(contadorLineas)), "pág. "+numeroPagina);
    }
 
     
    private void escribirTexto(PDPageContentStream pDPageContentStream, PDFont pdFont, float sizeFuente, float inicioH, float inicioV,  String texto) throws IOException {
               
        pDPageContentStream.beginText();   
        pDPageContentStream.setFont(pdFont, sizeFuente);
        pDPageContentStream.newLineAtOffset(inicioH, inicioV);
        pDPageContentStream.showText(texto);
        pDPageContentStream.endText();
    }
       
    private void escribirLinea(PDPageContentStream pDPageContentStream, float inicioH, float inicioV, float finH, float finV) throws IOException {
        pDPageContentStream.moveTo(inicioH, inicioV);
        pDPageContentStream.lineTo(finH, finV);
        pDPageContentStream.stroke();
    }        
    
    private void protegerDocumento(PDDocument pDDocument) throws IOException{
            AccessPermission accessPermission = new AccessPermission();
            accessPermission.setCanPrint(false);
            accessPermission.setCanPrintDegraded(false);
            accessPermission.setCanExtractContent(false);
            accessPermission.setCanExtractForAccessibility(false);
            accessPermission.setCanFillInForm(true);
            accessPermission.setCanModify(false);
            accessPermission.setReadOnly();
                                                                                 //      PASSWORD    PASSWORD
                                                                                 //      PROPIETARIO USUARIO              
            StandardProtectionPolicy standardProtectionPolicy = new StandardProtectionPolicy("123", "12345", accessPermission);
            standardProtectionPolicy.setEncryptionKeyLength(128);
            standardProtectionPolicy.setPreferAES(true);
            standardProtectionPolicy.setPermissions(accessPermission);
            pDDocument.protect(standardProtectionPolicy);               
    } 
    
}

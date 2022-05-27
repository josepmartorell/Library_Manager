/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package printer;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.PAGE_EXISTS;
import model.Libro;

/**
 *
 * @author jtech
 */  
public class ImpresorPagina extends Component implements Printable {

    private final Libro[] lineasPagina;
    private final int numeroPagina; 

    public ImpresorPagina(Libro[] lineasPaginaTransferido, int numeroPagina) {     

       this.numeroPagina = numeroPagina;          
       lineasPagina = new Libro[lineasPaginaTransferido.length];
       for (int i=0; i<lineasPagina.length; i++)
           lineasPagina[i] = lineasPaginaTransferido[i];  
    }


    @Override
    public int print(Graphics g, PageFormat pageFormat, int n) {       
       //     CABECERA DEL LISTADO        
       MediaTracker mediaTracker = new MediaTracker(this);
       Image image = Toolkit.getDefaultToolkit().getImage("imgs/logo1.jpg");
       mediaTracker.addImage(image,0);
       try{ 
             mediaTracker.waitForAll();
          } catch (InterruptedException exception)
            { System.out.println("Error en metodo print de ImpresorPagina  "+exception.getMessage()); }
       g.drawImage(image, 70, 30, this); 
       g.setFont(new Font("TimesRoman", Font.PLAIN + Font.BOLD, 15));
       g.drawString("BIBLIOTECA MUNICIPAL DE VILLAR DEL MONTE", 160, 55); 
       g.setFont(new Font("TimesRoman", Font.PLAIN + Font.BOLD, 10));
       g.drawString("Fecha : ", 455, 90);
       g.setFont(new Font("TimesRoman", Font.PLAIN, 10));
       g.drawString(new java.text.SimpleDateFormat("dd-MM-yyyy").format(new java.util.Date()), 495, 90);
       g.drawLine(50, 120, 580, 120);
       g.setFont(new Font("TimesRoman",Font.BOLD,12));
       g.drawString("Código", 50, 135);
       g.drawString("Título", 105, 135);
       g.drawString("Género", 415, 135);
       g.drawLine(50, 140, 580, 140);
       //     CUERPO DEL LISTADO          
       g.setFont(new Font("TimesRoman",Font.PLAIN,10));
       int i;
       for (i=0; i<lineasPagina.length; i++)
          if (lineasPagina[i] != null)
          {
             g.drawString(lineasPagina[i].getIdLibro(), 50, 160+(i*15));
             g.drawString(lineasPagina[i].getTitulo(), 105, 160+(i*15));
             g.drawString(lineasPagina[i].getGenero() + " - " + lineasPagina[i].getDescripcion(), 415, 160+(i*15));
          }        
       //     PIE DEL LISTADO 
       g.setFont(new Font("TimesRoman", Font.BOLD,12));
       g.drawLine(50, 160+(i*15) ,580, 160+(i*15));
       g.setFont(new Font("Dialog", Font.ITALIC,10));     
       g.drawString("pág. "+numeroPagina, 520, 180+(i*15));

       return (PAGE_EXISTS);
    } 
}
    


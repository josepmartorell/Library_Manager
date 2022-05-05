/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.Controller;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import model.Libro;
import persistence.BaseDatos;
import persistence.LibrosNegocio;

/**
 *
 * @author jtech
 */
public class VistaArbol extends PantallaOpcion {

      private JScrollPane jScrollPane;
      private JTree jTree;
      private DefaultTreeModel modelo;
      private NodoRaiz raiz;
      private JSplitPane splitPane;
      private EdicionNodo edicionNodo;
      private JPopupMenu popupMenu1; 
      private JPopupMenu popupMenu2;
      private JMenuItem nuevoNodo;
      private JMenuItem editarNodo;
      private JMenuItem eliminarNodo;                       
      private String[] generos; 
      private DefaultMutableTreeNodeHeredada  nodoGeneroSeleccion; 
      private DefaultMutableTreeNodeHeredada  nodoLibroSeleccion;
      
    
    public VistaArbol() {
        componentesJPanel[13] = -1;   //  Fila de la posición en que se ha pulsado botón de ratón
        componentesJPanel[14] = -1;   //  Columna de la posición en que se ha pulsado botón de ratón
        componentesJPanel[15] = "";                //  Componente sobre el que se hace click con el ratón
        componentesJPanel[16] = "";                //  MouseEvent
        ubicarComponentes();
    }
  
    
    private void ubicarComponentes() {
        setLayout(null);

        raiz = new NodoRaiz("Libros");
        jTree = new JTree( raiz );
        modelo = (DefaultTreeModel)jTree.getModel();

        popupMenu1 = new JPopupMenu();
        nuevoNodo = new JMenuItem("Nuevo nodo");
        popupMenu1.add(nuevoNodo);   
        
        popupMenu2 = new JPopupMenu();
        editarNodo = new JMenuItem("Editar nodo");
        popupMenu2.add(editarNodo);
        eliminarNodo = new JMenuItem("Eliminar nodo");
        popupMenu2.add(eliminarNodo);    
    }   
    

    public void inicializarPostInstanciar(Controller controller) throws Exception {
        
        this.controller = controller;        

        edicionNodo = new EdicionNodo();
        edicionNodo.inicializarPostInstanciar(controller);  
        edicionNodo.setVistaArbol(this);
        
        jScrollPane = new JScrollPane(jTree);       
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jScrollPane, edicionNodo);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(385);
        splitPane.setBounds(1,1,775,460);
        add(splitPane);        
        
        jTree.addMouseListener(controller);
        nuevoNodo.addActionListener(controller);
        editarNodo.addActionListener(controller);
        eliminarNodo.addActionListener(controller);        
    
        ocultarEdicionNodo();
        cargarArbol();
    }    
    
    
    private void mostrarEdicionNodo() throws Exception { 
        controller.setPantallaOpcion(edicionNodo);       
        edicionNodo.inicializarPantalla(nodoGeneroSeleccion, nodoLibroSeleccion);
        edicionNodo.getPanelInterior().setVisible(true);
     }

   
    public void ocultarEdicionNodo() {
        controller.setPantallaOpcion(this);
        edicionNodo.getPanelInterior().setVisible(false);
    }
  

    private void cargarArbol() throws Exception {   
        while ( modelo.getChildCount(raiz) > 0 )
           modelo.removeNodeFromParent((DefaultMutableTreeNode)modelo.getChild(raiz,0));

        generos = new LibrosNegocio().consultarGeneros((BaseDatos)controller.getRepositorio()[0]);
       for ( int i=0 ; i<generos.length ; i++)
          {
            NodoGenero nodoGenero = new NodoGenero( generos[i] );
            nodoGenero.setIdentificativo(generos[i]);
            modelo.insertNodeInto( nodoGenero, raiz, modelo.getChildCount(raiz) );
            List<Libro> listaLibros = new LibrosNegocio().consultarTodos((BaseDatos)controller.getRepositorio()[0], 2, generos[i].substring(0, 1), null);
            for (int k=0; k<listaLibros.size(); k++)
            {
               Libro libro = listaLibros.get(k);
               NodoLibro nodoLibro = new NodoLibro(libro.getIdLibro()+" - "+libro.getTitulo());
               nodoLibro.setIdentificativo(libro.getIdLibro());
               nodoLibro.setTitulo(libro.getTitulo());
               nodoGenero.add(nodoLibro);
            }
          }
    }

 
    public void insertarNodoLibro(Libro libro) {
        int i;
        boolean encontradaPosicion;

        NodoLibro nodoLibro = new NodoLibro(libro.getIdLibro()+" - "+libro.getTitulo() );
        nodoLibro.setIdentificativo(libro.getIdLibro());
        nodoLibro.setTitulo(libro.getTitulo());
      
        encontradaPosicion = false;
        i = 0;

        while ( !encontradaPosicion  &&  i < modelo.getChildCount( nodoGeneroSeleccion ) )
        {
           if ( libro.getTitulo().compareTo(((NodoLibro)modelo.getChild(nodoGeneroSeleccion, i)).getTitulo()) < 0)
                 encontradaPosicion = true;
              else 
                 i++;
        }
     
        modelo.insertNodeInto(nodoLibro, nodoGeneroSeleccion, i); 
    }

    
   private void eliminarNodoLibro() {
        modelo.removeNodeFromParent((DefaultMutableTreeNode)modelo.getChild(nodoGeneroSeleccion, modelo.getIndexOfChild(nodoGeneroSeleccion, nodoLibroSeleccion))); 
    }
   
    
   public void modificarNodoLibro(Libro libro, int actualizaciones) {
       
       if (((int)actualizaciones & 1) > 0)   //  PROCEDEMOS A ELIMINAR Y A INSERTAR NUEVAMENTE EL NODO SI SE HA MODIFICADO EL TITULO
       {
          eliminarNodoLibro(); 
          insertarNodoLibro(libro);
       }
    }
    
    
    public void responderAController(String actionCommand) throws Exception {
          switch(actionCommand)
          {   
               case "Nuevo nodo" :
               case "Editar nodo" :  
                      mostrarEdicionNodo();                     
                      break;                     
               case "Eliminar nodo" :
                      Libro libro = new Libro();
                      libro.setIdLibro(nodoLibroSeleccion.getIdentificativo());
                      new LibrosNegocio().eliminar((BaseDatos)controller.getRepositorio()[0], libro);
                      eliminarNodoLibro(); 
                      break;                      
               case "ratonClicked" :    
                      nodoGeneroSeleccion = null;
                      nodoLibroSeleccion = null;
                      TreePath seleccionEvento = jTree.getPathForLocation(((Integer)componentesJPanel[13]).intValue(), ((Integer)componentesJPanel[14]).intValue());

                      if (seleccionEvento != null)
                      { 
                         DefaultMutableTreeNodeHeredada nodoSeleccionado = (DefaultMutableTreeNodeHeredada)jTree.getLastSelectedPathComponent();
                         DefaultMutableTreeNodeHeredada nodoSeleccionEvento = (DefaultMutableTreeNodeHeredada)seleccionEvento.getLastPathComponent();

                         if ( nodoSeleccionado != null )
                         { 
                             if (seleccionEvento.getLastPathComponent().getClass().getName().compareTo("view.NodoGenero")==0 && nodoSeleccionado.getClass().getName().compareTo("presentacion.NodoGenero")==0 && nodoSeleccionado.getIdentificativo().compareTo(nodoSeleccionEvento.getIdentificativo())==0 && SwingUtilities.isRightMouseButton((MouseEvent)componentesJPanel[16]) )
                               {
                                 nodoGeneroSeleccion = nodoSeleccionado;
                                 popupMenu1.show( (Component)componentesJPanel[15], ((Integer)componentesJPanel[13]).intValue(), ((Integer)componentesJPanel[14]).intValue() ); 
                               }
                              else
                               {
                                 if (seleccionEvento.getLastPathComponent().getClass().getName().compareTo("view.NodoLibro")==0 && nodoSeleccionado.getClass().getName().compareTo("presentacion.NodoLibro")==0 && nodoSeleccionado.getIdentificativo().compareTo(nodoSeleccionEvento.getIdentificativo())==0 && SwingUtilities.isRightMouseButton((MouseEvent)componentesJPanel[16]) )
                                 { 
                                    nodoGeneroSeleccion = (DefaultMutableTreeNodeHeredada)nodoSeleccionado.getParent();
                                    nodoLibroSeleccion =  nodoSeleccionado ;                                
                                    popupMenu2.show( (Component)componentesJPanel[15], ((Integer)componentesJPanel[13]).intValue(), ((Integer)componentesJPanel[14]).intValue() ); 
                                 }
                               }
                         }
                      }                      
                      break;                      
          }
    }    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author jtech
 */
public class ParametrosSeleccionAgrupacion {
    
    private String[] criteriosSeleccion = {null, null, null};
    private boolean[] criteriosAgrupacion = new boolean[3];

    public String getCriterioSeleccion(int componente) {
        return criteriosSeleccion[componente];
    }

    public void setCriterioSeleccion(String criterioSeleccion, int componente) {
        this.criteriosSeleccion[componente] = criterioSeleccion;
    }
     
    public String[] getCriteriosSeleccion() {
        return criteriosSeleccion;
    }

    public void setCriteriosSeleccion(String[] criteriosSeleccion) {
        this.criteriosSeleccion = criteriosSeleccion;
    }
    
    public boolean isCriterioAgrupacion(int componente) {
        return criteriosAgrupacion[componente];
    }

    public void setCriterioAgrupacion(boolean criterioAgrupacion, int componente) {
        this.criteriosAgrupacion[componente] = criterioAgrupacion;
    }
 
    public boolean[] getCriteriosAgrupacion() {
        return criteriosAgrupacion;
    }

    public void setCriteriosAgrupacion(boolean[] criteriosAgrupacion) {
        this.criteriosAgrupacion = criteriosAgrupacion;
    } 
    
    
    
}

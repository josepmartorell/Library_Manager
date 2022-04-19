/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;

/**
 *
 * @author jtech
 */
public class ActividadUsuario {
   private Timestamp fechaHora;
   private String cadenaFechaHora;
   private String identificadorUsuario;   
   private String ipCliente;
   private int codigoActividad;
   private String descripcion;
   private int numeroIncidencias;

    public Timestamp getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Timestamp fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getCadenaFechaHora() {
        return cadenaFechaHora;
    }

    public void setCadenaFechaHora(String cadenaFechaHora) {
        this.cadenaFechaHora = cadenaFechaHora;
    }

    public String getIdentificadorUsuario() {
        return identificadorUsuario;
    }

    public void setIdentificadorUsuario(String identificadorUsuario) {
        this.identificadorUsuario = identificadorUsuario;
    }

    public String getIpCliente() {
        return ipCliente;
    }

    public void setIpCliente(String ipCliente) {
        this.ipCliente = ipCliente;
    }

    public int getCodigoActividad() {
        return codigoActividad;
    }

    public void setCodigoActividad(int codigoActividad) {
        this.codigoActividad = codigoActividad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNumeroIncidencias() {
        return numeroIncidencias;
    }

    public void setNumeroIncidencias(int numeroIncidencias) {
        this.numeroIncidencias = numeroIncidencias;
    }
   
   
    
}

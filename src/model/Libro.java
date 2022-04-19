/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author jtech
 */
public class Libro {
    private String idLibro;
    private String titulo;
    private String genero;
    private String descripcion;
    private Date fechaEdicion;
    private int numeroPaginas;
    private boolean premiado;
    private Object datoActualizado;
    private int columnaActualizada;

    public String getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(String idLibro) {
        this.idLibro = idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaEdicion() {
        return fechaEdicion;
    }

    public void setFechaEdicion(Date fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public boolean isPremiado() {
        return premiado;
    }

    public void setPremiado(boolean premiado) {
        this.premiado = premiado;
    }

    public Object getDatoActualizado() {
        return datoActualizado;
    }

    public void setDatoActualizado(Object datoActualizado) {
        this.datoActualizado = datoActualizado;
    }

    public int getColumnaActualizada() {
        return columnaActualizada;
    }

    public void setColumnaActualizada(int columnaActualizada) {
        this.columnaActualizada = columnaActualizada;
    }
    
    
}

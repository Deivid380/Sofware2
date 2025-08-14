package co.edu.poli.Modelo;


import java.io.*;
import java.util.*;

/**
 * 
 */
public class Titular {

    /**
     * Default constructor
     */
    public Titular(String id, String nombre, String fecNac) {
        this.id = id;
        this.nombre = nombre;
        this.fecNac = fecNac;
    }

    /**
     * 
     */
    private String id;

    /**
     * 
     */
    private String nombre;

    /**
     * 
     */
    private String fecNac;

    public String getId() {
        return id;
    }   

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecNac() {
        return fecNac;
    }

    public void setFecNac(String fecNac) {
        this.fecNac = fecNac;
    }

    @Override
    public String toString() {  
        return "Titular{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", fecNac='" + fecNac + '\'' +
                '}';
    }

}
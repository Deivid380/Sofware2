package co.edu.poli.modelo;

/*modifica */
import java.io.*;
import java.util.*;

/**
 * 
 */
public class Ciudad {

    /**
     * Default constructor
     */
    public Ciudad(String codigo, String nombre) {
        this.codigo = codigo;   
        this.nombre = nombre;   
    }

    /**
     * 
     */
    private String codigo;

    /**
     * 
     */
    private String nombre;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Ciudad{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';        
    }

}
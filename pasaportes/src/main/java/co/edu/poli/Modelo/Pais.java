package co.edu.poli.Modelo;


import java.io.*;
import java.util.*;

/**
 * 
 */
public class Pais {

    /**
     * Default constructor
     */
    public Pais(String codigo, String nombre, List<Ciudad> ciudad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.ciudad = ciudad;
    }

    /**
     * 
     */
    private String codigo;

    /**
     * 
     */
    private String nombre;

    /**
     * 
     */
    private List<Ciudad> ciudad;

    public List<Ciudad> getCiudad() {
        return ciudad;
    }

    public void setCiudad(List<Ciudad> ciudad) {
        this.ciudad = ciudad;
    }

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
        return "Pais{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", ciudad=" + ciudad +
                '}';   
    }

}
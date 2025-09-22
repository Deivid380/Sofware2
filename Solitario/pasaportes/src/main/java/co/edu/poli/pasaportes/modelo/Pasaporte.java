package co.edu.poli.pasaportes.modelo;


import java.io.*;
import java.util.*;


public abstract class Pasaporte {


    public Pasaporte(String id, String fechaExp, Titular titular, Pais pais) {
        this.id = id;
        this.fechaExp = fechaExp;
        this.titular = titular;
        this.pais = pais;
    }


    private String id;

    /**
     * 
     */
    private String fechaExp;

    /**
     * 
     */
    private Titular titular;

    /**
     * 
     */
    public Pais pais;

    /**
     * 
     */
    private String tipo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }     

    public String getFechaExp() {
        return fechaExp;
    }

    public void setFechaExp(String fechaExp) {
        this.fechaExp = fechaExp;
    }

    public Titular getTitular() {
        return titular;
    }

    public void setTitular(Titular titular) {
        this.titular = titular;
    }   

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Pasaporte{" +
                "id='" + id + '\'' +
                ", fechaExp='" + fechaExp + '\'' +
                ", titular=" + titular +
                ", pais=" + pais + 
                ", tipo='" + tipo + '\'' +
                '}';   
                
    }
}
package co.edu.poli.pasaportes.modelo;


import java.io.*;
import java.util.*;

/**
 * 
 */
public class Visa {

 
    public Visa(String num, Pais pais, int mulEntry, Pasaporte pasaporte) {
        this.num = num;
        this.pais = pais;
        this.mulEntry = mulEntry;
        this.pasaporte = pasaporte;
    }

    /**
     * 
     */
    private String num;

    /**
     * 
     */
    private Pais pais;

    /**
     * 
     */
    private int mulEntry;

    /**
     * 
     */
    public Pasaporte pasaporte;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public int getMulEntry() {
        return mulEntry;
    }

    public void setMulEntry(int mulEntry) {
        this.mulEntry = mulEntry;
    }

    public Pasaporte getPasaporte() {
        return pasaporte;
    }

    public void setPasaporte(Pasaporte pasaporte) {
        this.pasaporte = pasaporte;
    }

    @Override
    public String toString() {
        return "Visa{" +
                "num='" + num + '\'' +
                ", pais=" + pais +
                ", mulEntry=" + mulEntry +
                ", pasaporte=" + pasaporte +
                '}';
    }

}
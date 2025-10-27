package co.edu.poli.pasaportes.servicios;

import co.edu.poli.pasaportes.modelo.*;
import co.edu.poli.pasaportes.repositorio.PasaporteRepo;


public class Fachada {

    private VerificacionID verifId;
    private VerificacionANT verifAnti;
    private GeneracionPasaporte generador;

    public Fachada(PasaporteRepo repo) {
        this.verifId = new VerificacionID(repo);
        this.verifAnti = new VerificacionANT();
        this.generador = new GeneracionPasaporte(repo);
    }

    public boolean verificarIdFormatoYDisponibilidad(String numero) {
        return verifId.validarParaCreacion(numero);
    }

    public boolean verificarAntiFraude(String numero, String nombreTitular) {
        return verifAnti.verificar(numero, nombreTitular);
    }

}   


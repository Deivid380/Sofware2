package co.edu.poli.pasaportes.servicios;

import co.edu.poli.pasaportes.repositorio.PasaporteRepo;
import co.edu.poli.pasaportes.modelo.Pasaporte;

public class VerificacionID {
    private final PasaporteRepo repo;

    public VerificacionID(PasaporteRepo repo) {
        this.repo = repo;
    }

    // Validación sintáctica básica
    public boolean validarFormato(String numero) {
        if (numero == null) return false;
        String n = numero.trim();
        return n.matches("[A-Za-z0-9\\-]{3,30}");
    }

    // Comprueba si ya existe en la BD
    public boolean existe(String numero) {
        try {
            // Intentamos con tipos conocidos; tu repo puede exponer un método mejor
            Pasaporte p = repo.read(numero, "Ordinario");
            if (p != null) return true;
            p = repo.read(numero, "Diplomático");
            if (p != null) return true;
            p = repo.read(numero, "Emergencia");
            return p != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retorna true si formato correcto y NO existe (listo para crear)
    public boolean validarParaCreacion(String numero) {
        return validarFormato(numero) && !existe(numero);
    }
}

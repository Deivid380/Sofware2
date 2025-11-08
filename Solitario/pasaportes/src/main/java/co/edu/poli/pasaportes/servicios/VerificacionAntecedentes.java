package co.edu.poli.pasaportes.servicios;

public class VerificacionAntecedentes {

    public VerificacionAntecedentes() {}

    /**
     * Verificación dummy:
     *  - Reglas simples que puedes reemplazar por llamadas externas.
     *  - Devuelve true si pasa.
     */
    public boolean verificar(String numero, String nombreTitular) {
        if (numero == null || nombreTitular == null) return false;
        // ejemplo simple: si contiene "X" lo consideramos sospechoso
        if (numero.toUpperCase().contains("X")) return false;
        if (nombreTitular.trim().length() < 3) return false;
        return true;
    }
}

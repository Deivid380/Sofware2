package co.edu.poli.pasaportes.servicios;

import co.edu.poli.pasaportes.modelo.Titular;

public class TitularAdapter implements ITitular {

    private Titular titular;

    public TitularAdapter(Titular titular) {
        this.titular = titular;
    }

    @Override
    public void mostrarDatos() {
        System.out.println("Titular: " + titular.getNombre() +
                           " (" + titular.getNacionalidad() + ")");
    }

    public Titular getTitular() {
        return titular;
    }
}

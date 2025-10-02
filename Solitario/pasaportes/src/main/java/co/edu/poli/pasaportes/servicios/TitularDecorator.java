package co.edu.poli.pasaportes.servicios;

public abstract class TitularDecorator implements ITitular {
    protected ITitular titular;

    public TitularDecorator(ITitular titular) {
        this.titular = titular;
    }

    @Override
    public void mostrarDatos() {
        titular.mostrarDatos();
    }
}

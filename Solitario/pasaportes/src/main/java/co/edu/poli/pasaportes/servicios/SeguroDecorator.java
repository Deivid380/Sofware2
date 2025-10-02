package co.edu.poli.pasaportes.servicios;

// Decorador Seguro
public class SeguroDecorator extends TitularDecorator {
    private String tipoSeguro;

    public SeguroDecorator(ITitular titular, String tipoSeguro) {
        super(titular);
        this.tipoSeguro = tipoSeguro;
    }

    @Override
    public void mostrarDatos() {
        super.mostrarDatos();
        System.out.println(" → Seguro: " + tipoSeguro);
    }
}

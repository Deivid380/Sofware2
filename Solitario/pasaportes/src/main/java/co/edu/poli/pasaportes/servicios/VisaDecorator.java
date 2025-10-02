package co.edu.poli.pasaportes.servicios;

public class VisaDecorator extends TitularDecorator {
    private String paisVisa;

    public VisaDecorator(ITitular titular, String paisVisa) {
        super(titular);
        this.paisVisa = paisVisa;
    }

    @Override
    public void mostrarDatos() {
        super.mostrarDatos();
        System.out.println(" → Visa para: " + paisVisa);
    }
}

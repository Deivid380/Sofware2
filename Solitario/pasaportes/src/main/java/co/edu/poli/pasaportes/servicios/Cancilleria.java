package co.edu.poli.pasaportes.servicios;

public class Cancilleria implements Personal {

    private String nombre;

    public Cancilleria(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void recibirMensaje(String mensaje) {
        System.out.println("Cancillería recibió: " + mensaje);
    }

    @Override
    public String getNombre() {
        return nombre;
    }
}


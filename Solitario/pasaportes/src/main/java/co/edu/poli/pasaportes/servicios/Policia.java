package co.edu.poli.pasaportes.servicios;

public class Policia implements Personal {

    private String nombre;

    public Policia(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void recibirMensaje(String mensaje) {
        System.out.println("Policía recibió: " + mensaje);
    }

    @Override
    public String getNombre() {
        return nombre;
    }
}


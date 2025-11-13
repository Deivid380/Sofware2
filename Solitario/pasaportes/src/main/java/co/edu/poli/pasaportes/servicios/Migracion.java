package co.edu.poli.pasaportes.servicios;

public class Migracion implements Personal {

    private String nombre;

    public Migracion(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void recibirMensaje(String mensaje) {
        System.out.println("Migración recibió: " + mensaje);
    }

    @Override
    public String getNombre() {
        return nombre;
    }
}

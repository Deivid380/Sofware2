package co.edu.poli.pasaportes.servicios;

public class Mediator {

    private Policia policia;
    private Migracion migracion;
    private Cancilleria cancilleria;

    public Mediator() {
        this.policia = new Policia("Policía Nacional");
        this.migracion = new Migracion("Migración Colombia");
        this.cancilleria = new Cancilleria("Cancillería");
    }

    // Cuando se cambia un pasaporte, notifica a Policía y Migración
    public String notificarCambioPasaporte(String id) {
        String mensaje = "Se modificó el pasaporte con ID: " + id;
        policia.recibirMensaje(mensaje);
        migracion.recibirMensaje(mensaje);
        return "Notificación enviada a Policía y Migración.";
    }

    // Cuando Policía realiza un cambio
    public String notificarCambioPolicia(String cambio) {
        String mensaje = "Cambio reportado por Policía: " + cambio;
        migracion.recibirMensaje(mensaje);
        cancilleria.recibirMensaje(mensaje);
        return "Notificación enviada a Migración y Cancillería.";
    }

    // Cuando Cancillería envía mensaje a Policía
    public String enviarDeCancilleriaAPolicia(String mensaje) {
        policia.recibirMensaje(mensaje);
        return "Mensaje enviado a Policía: " + mensaje;
    }

    // Cuando Migración envía mensaje a Cancillería
    public String enviarDeMigracionACancilleria(String mensaje) {
        cancilleria.recibirMensaje(mensaje);
        return "Mensaje enviado a Cancillería: " + mensaje;
    }
}

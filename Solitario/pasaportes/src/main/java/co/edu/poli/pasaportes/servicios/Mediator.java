package co.edu.poli.pasaportes.servicios;

import java.util.ArrayList;
import java.util.List;

public class Mediator {

    private List<Personal> personalList = new ArrayList<>();

    public Mediator() {
        personalList.add(new Policia("Policía Nacional"));
        personalList.add(new Migracion("Migración Colombia"));
        personalList.add(new Cancilleria("Cancillería"));
    }

    //Método auxiliar para obtener un tipo específico de Personal
    private <T extends Personal> T getPersonal(Class<T> tipo) {
        for (Personal p : personalList) {
            if (tipo.isInstance(p)) {
                return tipo.cast(p);
            }
        }
        return null;
    }

    //Cuando se cambia un pasaporte → notificar a Policía y Migración
    public String notificarCambioPasaporte(String id) {
        String mensaje = "Se modificó el pasaporte con ID: " + id;
        for (Personal p : personalList) {
            if (p instanceof Policia || p instanceof Migracion) {
                p.recibirMensaje(mensaje);
            }
        }
        return "Notificación enviada a Policía y Migración.";
    }

    //Cuando Policía realiza un cambio → notificar a Cancillería y Migración
    public String notificarCambioPolicia(String cambio) {
        String mensaje = "Cambio reportado por Policía: " + cambio;
        for (Personal p : personalList) {
            if (p instanceof Cancilleria || p instanceof Migracion) {
                p.recibirMensaje(mensaje);
            }
        }
        return "Notificación enviada a Migración y Cancillería.";
    }

    //Cancillería → mensaje directo a Policía
    public String enviarDeCancilleriaAPolicia(String mensaje) {
        Policia policia = getPersonal(Policia.class);
        if (policia != null) {
            policia.recibirMensaje("Mensaje de Cancillería: " + mensaje);
            return "Mensaje enviado a Policía: " + mensaje;
        }
        return "Error: no se encontró Policía.";
    }

    //Migración → mensaje directo a Cancillería
    public String enviarDeMigracionACancilleria(String mensaje) {
        Cancilleria cancilleria = getPersonal(Cancilleria.class);
        if (cancilleria != null) {
            cancilleria.recibirMensaje("Mensaje de Migración: " + mensaje);
            return "Mensaje enviado a Cancillería: " + mensaje;
        }
        return "Error: no se encontró Cancillería.";
    }
}

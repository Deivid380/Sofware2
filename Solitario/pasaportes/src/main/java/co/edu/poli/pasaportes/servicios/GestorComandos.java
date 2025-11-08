package co.edu.poli.pasaportes.servicios;

import java.util.Stack;

public class GestorComandos {

    private Stack<Comando> historial = new Stack<>();

    public void ejecutarComando(Comando comando) {
        comando.ejecutar();
        historial.push(comando);
    }

    public boolean puedeDeshacer() {
        return !historial.isEmpty();
    }

    public void deshacerUltimo() {
        if (!historial.isEmpty()) {
            Comando comando = historial.pop();
            comando.deshacer();
        }
    }
}

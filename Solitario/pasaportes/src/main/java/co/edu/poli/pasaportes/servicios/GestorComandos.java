package co.edu.poli.pasaportes.servicios;

import java.util.ArrayList;
import java.util.List;

public class GestorComandos {

    private List<Comando> historial = new ArrayList<>();

    public void ejecutarComando(Comando comando) {
        comando.ejecutar();
        historial.add(comando);
    }

    public List<Comando> getHistorial() {
        return historial;
    }
}

package co.edu.poli.pasaportes.modelo;

public class PasaporteOrdinario extends Pasaporte {

    public PasaporteOrdinario(String id, String fechaExp, Titular titular, Pais pais) {
        super(id, fechaExp, titular, pais);
    }

    @Override
    public String toString() {
        return "PasaporteOrdinario{" +
                "id='" + getId() + '\'' +
                ", fechaExp='" + getFechaExp() + '\'' +
                ", titular=" + getTitular() +
                ", pais=" + getPais() +
                '}';
    }
}

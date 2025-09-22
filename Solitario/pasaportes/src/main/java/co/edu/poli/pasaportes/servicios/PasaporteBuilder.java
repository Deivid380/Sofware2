package co.edu.poli.pasaportes.servicios;

import co.edu.poli.pasaportes.modelo.PasaporteOrdinario;
import co.edu.poli.pasaportes.modelo.Titular;
import co.edu.poli.pasaportes.modelo.Pais;

public class PasaporteBuilder {

    private String id;
    private String fechaExp;
    private Titular titular;
    private Pais pais;

    public PasaporteBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public PasaporteBuilder setFechaExp(String fechaExp) {
        this.fechaExp = fechaExp;
        return this;
    }

    public PasaporteBuilder setTitular(Titular titular) {
        this.titular = titular;
        return this;
    }

    public PasaporteBuilder setPais(Pais pais) {
        this.pais = pais;
        return this;
    }

    public PasaporteOrdinario build() {
        return new PasaporteOrdinario(id, fechaExp, titular, pais);
    }
}

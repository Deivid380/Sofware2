package co.edu.poli.pasaportes.servicios;

import co.edu.poli.pasaportes.modelo.*;

public class PasaporteBuilder {
    private String tipo;
    private String id;
    private String fechaExp;
    private Titular titular;
    private Pais pais;
    private ElementoSeguridad seguridad;

    public PasaporteBuilder setTipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

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

    public PasaporteBuilder setSeguridad(ElementoSeguridad seguridad) {
        this.seguridad = seguridad;
        return this;
    }

    public Pasaporte build() {
        if ("Ordinario".equalsIgnoreCase(tipo)) {
            return new PasaporteOrdinario(id, fechaExp, titular, pais, seguridad);
        } else if ("Diplomatico".equalsIgnoreCase(tipo)) {
            return new PasaporteDiplomatico(id, fechaExp, titular, pais, seguridad);
        } else {
            throw new IllegalArgumentException("Tipo de pasaporte no válido: " + tipo);
        }
    }
}

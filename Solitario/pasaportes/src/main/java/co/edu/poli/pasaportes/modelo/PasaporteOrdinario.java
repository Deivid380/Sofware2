package co.edu.poli.pasaportes.modelo;

public class PasaporteOrdinario extends Pasaporte { 
    private PasaporteOrdinario(String id, String fechaExp, Titular titular, Pais pais) {
         super(id, fechaExp, titular, pais); 
        } 
        public static class Builder { 
            private String id;
             private String fechaExp; 
             private Titular titular; 
             private Pais pais; 
             public Builder() {
              } 
              public Builder getid(String id) {
        this.id = id; return this; 
    } 
        public Builder getfechaExp(String fechaExp) { 
            this.fechaExp = fechaExp; return this; 
        } 
            public Builder gettitular(Titular titular) {
                 this.titular = titular; return this; 
                } 
                 public Builder getpais(Pais pais) 
                 { 
                    this.pais = pais; return this; } 
                    public PasaporteOrdinario build() {
                         return new PasaporteOrdinario(id, fechaExp, titular, pais); 
                        } 
                    } 
                }
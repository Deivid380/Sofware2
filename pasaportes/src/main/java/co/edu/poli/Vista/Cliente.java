package co.edu.poli.Vista;

import java.util.ArrayList;
import java.util.Collections;

import co.edu.poli.Modelo.*;


public class Cliente {
    public static void main(String[] args) {
    
        // Example usage of the classes
        Ciudad ciudad = new Ciudad("BOG", "Bogotá");
        Titular titular = new Titular("123456", "John Doe", "1990-01-01");
        Pais pais = new Pais("CO", "Colombia", new ArrayList<>(Collections.singletonList(ciudad)));
        Pasaporte pasaporte = new Pasaporte("PAS123456", "2025-12-31", titular, pais);
        Visa visa = new Visa("VISA123456", pais, 1, pasaporte);
        

        System.out.println(pasaporte);

        

}
}

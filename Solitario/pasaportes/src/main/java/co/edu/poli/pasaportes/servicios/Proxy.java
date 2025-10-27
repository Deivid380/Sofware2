package co.edu.poli.pasaportes.servicios;

public class Proxy implements ServiceInterface {

    private AdapterPasaporte servicioReal;
    private String rol;

    public Proxy(AdapterPasaporte servicioReal, String rol) {
        this.servicioReal = servicioReal;
        this.rol = rol;
    }

    @Override
    public void accederInformacion(String idPasaporte) {
        System.out.println("Intento de acceso por rol: " + rol);

        if (rol.equalsIgnoreCase("ADMIN") || rol.equalsIgnoreCase("OFICIAL")) {
            servicioReal.accederInformacion(idPasaporte);
        } else {
            System.out.println("Acceso denegado. Rol no autorizado.");
        }
    }
}


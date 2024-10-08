package repositorios;

import elementos.Tarjeta;
import pruebas.IdGenerator;

import java.util.ArrayList;
import java.util.List;

public class RepositorioTarjetas {
    private static RepositorioTarjetas instancia = null;
    private String ultimoID;

    private static List<Tarjeta> tarjetas;

    public RepositorioTarjetas() {
        tarjetas = new ArrayList<Tarjeta>();
        this.ultimoID = "00000000000";
    }

    public static RepositorioTarjetas getInstancia() {
        if(instancia == null) {
            instancia = new RepositorioTarjetas();
        }
        return instancia;
    }

    public String generarIdTarjeta() {
        String nuevoID = IdGenerator.generateNextId(this.ultimoID);
        this.ultimoID = nuevoID;
        return nuevoID;
    }
}

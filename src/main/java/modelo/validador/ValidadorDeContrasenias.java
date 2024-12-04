package modelo.validador;

import modelo.validador.condiciones.Credencial;
import modelo.validador.condiciones.Longitud;
import modelo.validador.condiciones.TopPeores10000;
import persistencia.RepositorioUsuarios;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ValidadorDeContrasenias {
    private static ValidadorDeContrasenias instancia = null;
    private List<Condicion> condicionesContrasenia = Arrays.asList(new Longitud(), new Credencial(), new TopPeores10000());

    public static ValidadorDeContrasenias getInstancia() {
        if(instancia == null) {
            instancia = new ValidadorDeContrasenias();
        }
        return instancia;
    }

    public Boolean validarContrasenia(String username, String contrasenia) {
        String password = SanitizadorDeContrasenias.eliminarMultiplesEspacios(contrasenia);
        return this.condicionesContrasenia.stream().allMatch(
                    condicion -> condicion.verificarContrasenia(username, password));

    }

    public void agregarCondiciones (Condicion ... condiciones) {
        Collections.addAll(this.condicionesContrasenia, condiciones);
    }
}
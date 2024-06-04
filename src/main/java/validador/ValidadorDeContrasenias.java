package validador;

import validador.condiciones.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ValidadorDeContrasenias {
    private List<Condicion> condicionesContrasenia = Arrays.asList(new Longitud(), new Credencial(), new TopPeores10000());

    public boolean validarContrasenia(String username, String contrasenia) {
        String password = SanitizadorDeContrasenias.eliminarMultiplesEspacios(contrasenia);
        boolean esValida =
                this.condicionesContrasenia.stream().allMatch(
                        condicion -> condicion.verificarContrasenia(username, password));

        if (esValida) {
            System.out.printf("Su contrasenia es valida.");
        }
        return esValida;
    }

    public void agregarCondiciones (Condicion ... condiciones) {
        Collections.addAll(this.condicionesContrasenia, condiciones);
    }
}
// Esta clase se encarga de validar la contraseña pasada por parámetro según cada una de las condiciones establecidas.
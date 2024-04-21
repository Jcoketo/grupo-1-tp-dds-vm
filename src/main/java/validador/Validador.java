package validador;

import validador.condiciones.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Validador {
    List<Condicion> validaciones = Arrays.asList(new Longitud(), new Credencial(), new Top10000());

    public boolean validarContrasenia(String username, String contrasenia) {
        String password = Sanitizador.borrarEspacios(contrasenia);
        boolean esValida =
                this.validaciones.stream().allMatch(
                        condicion -> condicion.verificar(username, password));

        if (esValida) {
            System.out.printf("Su contrasenia es valida.");
        }
        return esValida;
    }

    public void agregarCondiciones (Condicion ... condiciones) {
        Collections.addAll(this.validaciones, condiciones);
    }
}

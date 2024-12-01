package modelo.validador.condiciones;

import modelo.excepciones.ExcepcionValidacion;
import modelo.validador.Condicion;

public class Credencial implements Condicion {

    public boolean verificarContrasenia(String username, String constrasenia){
        boolean esValida = !(constrasenia.contains(username));
        if(!esValida){
            throw new ExcepcionValidacion("La contraseña es similar a su nombre de usuario. Intente con otra contraseña");
        }
        return esValida;
    }
}
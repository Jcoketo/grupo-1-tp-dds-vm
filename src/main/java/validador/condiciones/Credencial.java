package validador.condiciones;

import validador.Condicion;

public class Credencial implements Condicion {

    public boolean verificar(String username, String constrasenia){
        boolean esValida = !(constrasenia.contains(username));
        if(!esValida){
            System.out.println(
                    "La contraseña es similar a su nombre de usuario. Intente con otra contraseña");
        }
        return esValida;
    }
}

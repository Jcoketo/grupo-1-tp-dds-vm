package validador.condiciones;

import validador.Condicion;

public class Longitud implements Condicion {
    private int minValueInclusive = 8;
    int maxValueInclusive = 64;

    public boolean between(int variable, int minValueInclusive, int maxValueInclusive) {
        return variable >= minValueInclusive && variable <= maxValueInclusive;
    }

    @Override
    public boolean verificarContrasenia(String username, String constrasenia){
        boolean esValida =
                this.between(constrasenia.length(), minValueInclusive, maxValueInclusive);

        if(!esValida){
            System.out.println(
                    "La contraseña debe tener entr 8 y 64 caracteres. Intente con otra contraseña");
        }

        return esValida;
    }
}

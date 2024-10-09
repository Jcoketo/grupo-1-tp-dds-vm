package modelo.validador.condiciones;

import modelo.validador.Condicion;

public class Longitud implements Condicion {
    private int minValueInclusive = 8;
    private int maxValueInclusive = 64;

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

    public boolean between(int variable, int minValueInclusive, int maxValueInclusive) {
        return variable >= minValueInclusive && variable <= maxValueInclusive;
    }
}
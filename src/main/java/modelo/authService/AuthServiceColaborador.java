package modelo.authService;

import modelo.excepciones.ExcepcionValidacion;
import modelo.personas.TipoDocumento;
import persistencia.RepositorioColaboradores;

public class AuthServiceColaborador {

    private static RepositorioColaboradores repoColab = RepositorioColaboradores.getInstancia();

    public static void registrarColaboradorFisico(TipoDocumento tipoDoc, String nroDoc, String nombre, String apellido, String mail, String telefono, String direccion, String fechaNacimiento) {
        if (repoColab.existePersonaFisica(nroDoc, tipoDoc) != null) {
            throw new ExcepcionValidacion("El colaborador ya existe");
        }
        /* NO HACE FALTA, YA SE VALIDO CUANDO SE CREO EL USUARIO
        if (repoColab.existeMail(mail) != null) {
            throw new ExcepcionValidacion("El mail ya existe");
        }*/

        repoColab.registrarColaboradorFisico(tipoDoc, nroDoc, nombre, apellido, mail, telefono, direccion, fechaNacimiento);

    }


}
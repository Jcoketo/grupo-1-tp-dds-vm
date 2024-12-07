package modelo.authService;

import modelo.elementos.Areas;
import modelo.excepciones.ExcepcionValidacion;
import modelo.personas.TipoDocumento;
import persistencia.RepositorioColaboradores;
import persistencia.RepositoriosTecnicos;

public class AuthServiceTecnico {
    private static RepositorioColaboradores repoColab = RepositorioColaboradores.getInstancia();
    private static RepositoriosTecnicos repoTecnicos = RepositoriosTecnicos.getInstancia();

    public static void registrarTecnico(String nombre, String apellido, TipoDocumento tipoDoc, String numeroDoc, String cuil, String mail, String telefono, Areas areaCobertura) {
        if(repoColab.existePersonaFisica(numeroDoc, tipoDoc) != null) {
            throw new ExcepcionValidacion("El técnico ya existe!");
        }

        repoTecnicos.registrarTecnico(nombre, apellido, tipoDoc, numeroDoc, cuil, mail, telefono, areaCobertura);
    }

}

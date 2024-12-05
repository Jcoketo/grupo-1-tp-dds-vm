package modelo.authService;

import modelo.excepciones.ExcepcionValidacion;
import modelo.personas.Rubro;
import modelo.personas.TipoDocumento;
import modelo.personas.TipoJuridico;
import persistencia.RepositorioColaboradores;

public class AuthServiceColaborador {

    private static RepositorioColaboradores repoColab = RepositorioColaboradores.getInstance(); // pasar em?

    public static void registrarColaboradorFisico(TipoDocumento tipoDoc, String nroDoc, String nombre, String apellido, String mail, String telefono, String direccion, String fechaNacimiento) {
        if (repoColab.existePersonaFisica(nroDoc, tipoDoc) != null) {
            throw new ExcepcionValidacion("El colaborador ya existe!");
        }
        repoColab.registrarColaboradorFisico(tipoDoc, nroDoc, nombre, apellido, mail, telefono, direccion, fechaNacimiento);

    }

    public static void registrarColaboradorJuridico(String razonSocial, TipoJuridico tipoJuridico, Rubro rubro, Integer cuit, String telefono, String email) {
        if (repoColab.existePersonaJuridica(cuit) != null) {
            throw new ExcepcionValidacion("El colaborador ya existe!");
        }
        repoColab.registrarColaboradorJuridico(razonSocial, tipoJuridico, rubro, cuit, telefono, email);
    }


}
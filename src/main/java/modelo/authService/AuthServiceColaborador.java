package modelo.authService;

import modelo.excepciones.ExcepcionValidacion;
import modelo.personas.Rubro;
import modelo.personas.TipoDocumento;
import modelo.personas.TipoJuridico;
import modelo.validador.Usuario;
import persistencia.RepositorioColaboradores;

public class AuthServiceColaborador {

    private static RepositorioColaboradores repoColab = RepositorioColaboradores.getInstancia(); // pasar em?

    public static void registrarColaboradorFisico(Usuario usuario, TipoDocumento tipoDoc, String nroDoc, String nombre, String apellido, String mail, String telefono, String direccion, String fechaNacimiento) {
        if (repoColab.existePersonaFisica(nroDoc, tipoDoc) != null) {
            throw new ExcepcionValidacion("El TIPO y NUMERO DE DOC ya existe!");
        }
        repoColab.registrarColaboradorFisico(usuario, tipoDoc, nroDoc, nombre, apellido, mail, telefono, direccion, fechaNacimiento);

    }

    public static void registrarColaboradorJuridico(Usuario usuario, String razonSocial, TipoJuridico tipoJuridico, Rubro rubro, String cuit, String telefono, String email) {
        if (repoColab.existePersonaJuridica(cuit) != null) {
            throw new ExcepcionValidacion("El CUIT ingresado ya existe!");
        }
        repoColab.registrarColaboradorJuridico(usuario, razonSocial, tipoJuridico, rubro, cuit, telefono, email);
    }

}
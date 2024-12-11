package modelo.authService;

import accessManagment.Roles;
import modelo.colaboracion.DonarDinero;
import modelo.contrasenia.PasswordGenerator;
import modelo.excepciones.ExcepcionValidacion;
import modelo.notificador.Notificador;
import modelo.personas.*;
import modelo.validador.Usuario;
import persistencia.RepositorioColaboradores;
import persistencia.RepositorioUsuarios;

import java.time.format.DateTimeFormatter;

public class AuthServiceColaborador {

    private static RepositorioColaboradores repoColab = RepositorioColaboradores.getInstancia(); // pasar em?
    private static RepositorioUsuarios repoUsuarios = RepositorioUsuarios.getInstancia();

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

    public static Colaborador procesarXCargaCSV(TipoDocumento tipoDoc, String nroDocumento, String nombre, String apellido, String mail, String fecha, String formaColaboracion, String cantidad){

        Integer idPersonaAux = repoColab.devolverIdPersona(tipoDoc, nroDocumento);
        if (idPersonaAux != null) {
            return repoColab.buscarColaboradorXIdPersona(idPersonaAux);
        }

            MedioDeContacto medioDeContacto = new MedioDeContacto(TipoMedioDeContacto.MAIL, mail);
            PersonaHumana persona = new PersonaHumana(tipoDoc, nroDocumento, nombre, apellido, medioDeContacto);
            Colaborador colaborador = new Colaborador(persona, "CSV");

            String password = PasswordGenerator.generatePassword();
            repoUsuarios.persistirUsuario(mail, nombre, password, Roles.USUARIO);
            repoColab.agregar(colaborador);

            String mensajeMasCredenciales = "Bienvenido a la plataforma. Su mail de ingreso es: " + mail + " y su contraseña es: " + password;

            Notificador.notificarXNuevoUsuario(mensajeMasCredenciales, medioDeContacto);

            return colaborador;



    }


}

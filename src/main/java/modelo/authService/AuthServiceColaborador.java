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

        MedioDeContacto medioContactoMail = new MedioDeContacto(TipoMedioDeContacto.MAIL, mail);
        PersonaHumana persona = new PersonaHumana(tipoDoc, nroDoc, nombre, apellido, medioContactoMail, direccion, fechaNacimiento);

        if(!telefono.equals("")){
            MedioDeContacto medioContactoTelefono = new MedioDeContacto(TipoMedioDeContacto.TELEFONO, telefono);
            persona.agregarMediosDeContacto(medioContactoTelefono);
        }
        Colaborador colaborador = new Colaborador(persona);

        repoColab.registrarColaboradorFisico(usuario, persona, colaborador);

        String mensajeBienvenida = "Bienvenido a la plataforma " + nombre + ". Ojala que te diviertas";
        Notificador.notificarXNuevoUsuario(mensajeBienvenida, medioContactoMail);

    }

    public static void registrarColaboradorJuridico(Usuario usuario, String razonSocial, TipoJuridico tipoJuridico, Rubro rubro, String cuit, String telefono, String email, String direccion) {
        if (repoColab.existePersonaJuridica(cuit) != null) {
            throw new ExcepcionValidacion("El CUIT ingresado ya existe!");
        }

        MedioDeContacto medioContactoMail = new MedioDeContacto(TipoMedioDeContacto.MAIL, email);
        PersonaJuridica persona = new PersonaJuridica(cuit, razonSocial, tipoJuridico, rubro, medioContactoMail, direccion);

        if(!telefono.equals("")){
            MedioDeContacto medioContactoTelefono = new MedioDeContacto(TipoMedioDeContacto.TELEFONO, telefono);
            persona.agregarMediosDeContacto(medioContactoTelefono);
        }

        Colaborador colaborador = new Colaborador(persona);

        repoColab.registrarColaboradorJuridico(usuario, persona, colaborador);

        String mensajeBienvenida = "Bienvenido a la plataforma " + razonSocial + ". Ojala que te diviertas";
        Notificador.notificarXNuevoUsuario(mensajeBienvenida, medioContactoMail);
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
            String passwordAux = AuthServiceUsuario.hashPassword(password);
            repoUsuarios.persistirUsuario(mail, nombre, passwordAux, Roles.USUARIO);
            repoColab.actualizarColaborador(colaborador);

            String mensajeMasCredenciales = "Bienvenido a la plataforma. Su mail de ingreso es: " + mail + " y su contraseña es: " + password;

            Notificador.notificarXNuevoUsuario(mensajeMasCredenciales, medioDeContacto);

            return colaborador;



    }


}

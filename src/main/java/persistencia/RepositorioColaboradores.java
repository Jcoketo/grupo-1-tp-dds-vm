package persistencia;

import accessManagment.Roles;
import lombok.Getter;
import modelo.contraseña.PasswordGenerator;
import modelo.notificador.Notificador;
import modelo.personas.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RepositorioColaboradores {
    private static RepositorioColaboradores instancia = null;

    @Getter
    private static List<Colaborador> colaboradores;

    private RepositorioColaboradores() {
        colaboradores = new ArrayList<Colaborador>();
    }
    public static RepositorioColaboradores getInstancia() {
        if(instancia == null) {
            instancia = new RepositorioColaboradores();
        }
        return instancia;
    }

    public Colaborador existeColaborador(Integer id) {

        for (Colaborador colab : colaboradores) {
            if (Objects.equals(colab.getIdUnico(), id)) {
                return colab;  // devuelve el colaborador
            }
        }

        return null;  // Devuelve null si no se encuentra un colaborador con el mismo tipo y número de documento
    }

    public void agregar(Colaborador colaborador) {
        colaboradores.add(colaborador);
    }

    public void darDeBaja(Colaborador colaborador) {
        colaboradores.remove(colaborador);
    }

    public TipoPersona devolverTipoPersona(String email) {
        for (Colaborador colab : colaboradores) {
            if (colab.getEmail().equals(email)) {
                return colab.getTipoPersona();
            }
        }
        return null;
    }

    public Roles devolverRol(String email) {
        //TODO
        return null;
    }

    public Colaborador crearColaboradorFisico(TipoDocumento tipoDocumento, String nroDocumento, String nombre, String apellido, String mail) {
        String identificadorUnico = tipoDocumento + nroDocumento;

        Boolean existeMail = existeMail(mail);

        if (!existeMail) { // NO EXISTE MAIL
            MedioDeContacto medioDeContacto = new MedioDeContacto(TipoMedioDeContacto.MAIL, mail);

            PersonaHumana persona = new PersonaHumana(tipoDocumento, nroDocumento, nombre, apellido, medioDeContacto);
            Colaborador colaborador = new Colaborador(persona);

            this.agregar(colaborador);

            String password = PasswordGenerator.generatePassword();
            // TODO ACA IRIA EL CODIGO PARA ALMACENAR EN LA BD LAS CREDENCIALES
            String mensajeMasCredenciales = "Bienvenido a la plataforma. Su usuario es: " + identificadorUnico + " y su contraseña es: " + password;

            Notificador.notificarXNuevoUsuario(mensajeMasCredenciales, colaborador);

            return colaborador;

        }
        return null; //TODO
    }

    private Boolean existeMail(String mail) {
        for (Colaborador colab : colaboradores) {
            if (colab.getEmail().equals(mail)) {
                return true;
            }
        }
        return false;
    }

}


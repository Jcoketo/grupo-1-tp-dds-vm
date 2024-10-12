package modelo.importador;

import modelo.colaboracion.DistribucionDeViandas;
import modelo.colaboracion.DonarDinero;
import modelo.colaboracion.DonarVianda;
import modelo.colaboracion.RegistroPersonasSituVulnerable;
import modelo.contraseña.PasswordGenerator;
import modelo.notificador.Notificador;
import modelo.personas.*;
import persistencia.RepositorioColaboradores;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AsignarColaborador {

    public static void agregarColaboracion(Colaborador colaborador, String fecha, String formaColaboracion, String cantidad) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Va ese formato para la fecha que levanta del CSV
        LocalDate fechaColaboracionAux = LocalDate.parse(fecha, formatter); // Levanta este formato dd/MM/yyyy y lo convierte a LocalDate.

        switch (formaColaboracion) {
            case "DINERO":
                DonarDinero donacionDinero = new DonarDinero(fechaColaboracionAux, Double.parseDouble(cantidad)); //cantidad es un Double
                colaborador.agregarColaboracion(donacionDinero);
                donacionDinero.incrementarPuntos(colaborador);
                break;
            case "DONACION_VIANDAS":
                DonarVianda donacionVianda = new DonarVianda(fechaColaboracionAux);
                colaborador.agregarColaboracion(donacionVianda);
                for(int i=0; i < Integer.parseInt(cantidad); i++) {
                    donacionVianda.incrementarPuntos(colaborador);
                }
                break;
            case "REDISTRIBUCION_VIANDAS":
                DistribucionDeViandas donacionDistribucion = new DistribucionDeViandas(fechaColaboracionAux, Integer.parseInt(cantidad));
                colaborador.agregarColaboracion(donacionDistribucion);
                donacionDistribucion.incrementarPuntos(colaborador);
                break;
            case "ENTREGA_TARJETAS":
                RegistroPersonasSituVulnerable registroPersonasSituVulnerable = new RegistroPersonasSituVulnerable(fechaColaboracionAux, Integer.parseInt(cantidad));
                colaborador.agregarColaboracion(registroPersonasSituVulnerable);
                registroPersonasSituVulnerable.incrementarPuntos(colaborador);
                break;
            default:
                break;
        }
    }

    public static Colaborador crearColaborador(TipoDocumento tipoDocumento, String nroDocumento, String nombre, String apellido, String mail) {
        String identificadorUnico = tipoDocumento + nroDocumento;

        RepositorioColaboradores repoColaboradores = RepositorioColaboradores.getInstancia();
        Colaborador colaborador = repoColaboradores.existeColaborador(1);
        //TODO VERIFICAR TODO IMPORTADOR - CAMBIE IDENTIFICADOR UNICO
        if (colaborador == null) { // NO EXISTE COLABORADOR
            MedioDeContacto medioDeContacto = new MedioDeContacto(TipoMedioDeContacto.MAIL, mail);

            PersonaHumana persona = new PersonaHumana(tipoDocumento, nroDocumento, nombre, apellido, medioDeContacto);

            Colaborador colaboradoraux = new Colaborador(persona);

            //colaborador = new PersonaHumana(tipoDocumento, nroDocumento, nombre, apellido, medioDeContacto);
            repoColaboradores.agregar(colaboradoraux);

            String password = PasswordGenerator.generatePassword();
            // TODO ACA IRIA EL CODIGO PARA ALMACENAR EN LA BD LAS CREDENCIALES
            String mensajeMasCredenciales = "Bienvenido a la plataforma. Su usuario es: " + identificadorUnico + " y su contraseña es: " + password;

            Notificador.notificarXNuevoUsuario(mensajeMasCredenciales, colaboradoraux);

            return colaboradoraux;

        }
        return colaborador;
    }
}

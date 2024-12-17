package modelo.authService;

import modelo.elementos.Heladera;
import modelo.excepciones.ExcepcionValidacion;
import modelo.notificador.Notificador;
import modelo.personas.Colaborador;
import modelo.personas.MedioDeContacto;
import modelo.personas.TipoMedioDeContacto;
import modelo.suscripcion.SuscripcionXCantidad;
import modelo.suscripcion.SuscripcionXFalla;
import modelo.suscripcion.TipoSuscripcion;
import persistencia.RepositorioColaboradores;
import persistencia.RepositorioHeladeras;

public class AuthServiceSuscripcion {

    private static RepositorioHeladeras repositorioHeladeras = RepositorioHeladeras.getInstancia();
    private static RepositorioColaboradores repositorioColaboradores = RepositorioColaboradores.getInstancia();


    public static void generarSuscripcion(Integer idHeladera, Integer idPersona, TipoSuscripcion tipoSuscripcion, Integer limite, TipoMedioDeContacto tipoMedioDeContacto) {

        Heladera heladera = repositorioHeladeras.buscarHeladera(idHeladera);
        Colaborador colaborador = repositorioColaboradores.buscarColaboradorXIdPersona(idPersona);

        if (!colaborador.getSuscripciones().stream().filter(suscripcion ->
                        suscripcion.getHeladera().getId() == idHeladera && suscripcion.getTipoSuscripcion() == tipoSuscripcion)
                .toList().isEmpty()) {
            throw new ExcepcionValidacion("Ya se encuentra suscripto a este tipo de alerta");
        }

        if (heladera == null || colaborador == null) {
            throw new ExcepcionValidacion("Hubo un problema en el servidor. Intente mas tarde");}

        if(tipoSuscripcion == TipoSuscripcion.POCO_ESPACIO && limite >= heladera.getViandasMaximas()) {
            throw new ExcepcionValidacion("La cantidad limite de viandas elegidas para notificar excede al valor posible (máximo de la heladera).");
        }

        if(limite < 0) {
            throw new ExcepcionValidacion("La cantidad limite de viandas debe ser mayor a 0.");
        }

        MedioDeContacto medioDeContacto = colaborador.getPersona().devolerMedioDeContacto(tipoMedioDeContacto);

        String mensaje = "";

        switch (tipoSuscripcion) {
            case QUEDAN_POCAS -> {
                SuscripcionXCantidad suscripcion = new SuscripcionXCantidad(heladera, colaborador, TipoSuscripcion.QUEDAN_POCAS, limite, medioDeContacto);
                heladera.agregarSuscriptor(suscripcion);
                colaborador.agregarSuscripcion(suscripcion);
                mensaje = "Te avisaremos cuando queden " + limite + " viandas en la heladera.";

            }
            case POCO_ESPACIO -> {
                SuscripcionXCantidad suscripcion = new SuscripcionXCantidad(heladera, colaborador, TipoSuscripcion.POCO_ESPACIO, limite, medioDeContacto);
                heladera.agregarSuscriptor(suscripcion);
                colaborador.agregarSuscripcion(suscripcion);
                mensaje = "Te avisaremos cuando quede poco espacio en la heladera.";
            }
            case DESPERFECTO -> {
                SuscripcionXFalla suscripcion = new SuscripcionXFalla(heladera, colaborador, TipoSuscripcion.DESPERFECTO, medioDeContacto);
                heladera.agregarSuscriptor(suscripcion);
                colaborador.agregarSuscripcion(suscripcion);
                mensaje = "Te avisaremos cuando haya un desperfecto en la heladera.";
            }
        }

        repositorioHeladeras.actualizarHeladera(heladera);


        String mensajeAux = "Te has suscripto a la heladera " + heladera.getNombre() + "." + mensaje + " \n\nRecuerda que puedes modificar tus suscripciones en cualquier momento.";

        Notificador.notificar(mensajeAux, "Suscripción realizada con éxito", medioDeContacto);
    }
}

/*
* public void realizarSuscripcionXFalla(Heladera heladera, TipoMedioDeContacto medio){
        SuscripcionXFalla suscripcion = new SuscripcionXFalla(heladera, this, TipoSuscripcion.DESPERFECTO, medio);
        heladera.agregarSuscriptor(suscripcion);
        this.suscripciones.add(suscripcion);
    }

    public void realizarSuscripcionXCantidad(Heladera heladera, Integer n, TipoSuscripcion tipo, TipoMedioDeContacto medio){
        SuscripcionXCantidad suscripcion = new SuscripcionXCantidad(heladera, this,tipo,n, medio);
        heladera.agregarSuscriptor(suscripcion);
        this.suscripciones.add(suscripcion);
    }
* */

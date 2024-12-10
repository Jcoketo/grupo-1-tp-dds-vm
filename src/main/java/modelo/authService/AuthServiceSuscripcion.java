package modelo.authService;

import modelo.elementos.Heladera;
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


    public static void generarSuscripcion(Integer idHeladera, Integer idPersona, TipoSuscripcion tipoSuscripcion, Integer limiteMinimo, Integer limiteMaximo, TipoMedioDeContacto tipoMedioDeContacto) {

        Heladera heladera = repositorioHeladeras.buscarHeladera(idHeladera);
        Colaborador colaborador = repositorioColaboradores.buscarColaboradorXIdPersona(idPersona);
        MedioDeContacto medioDeContacto = colaborador.getPersona().devolerMedioDeContacto(tipoMedioDeContacto);

        switch (tipoSuscripcion) {
            case QUEDAN_POCAS -> {
                SuscripcionXCantidad suscripcion = new SuscripcionXCantidad(heladera, colaborador, TipoSuscripcion.QUEDAN_POCAS, limiteMinimo, medioDeContacto);
                heladera.agregarSuscriptor(suscripcion);
                colaborador.agregarSuscripcion(suscripcion);
            }
            case POCO_ESPACIO -> {
                SuscripcionXCantidad suscripcion = new SuscripcionXCantidad(heladera, colaborador, TipoSuscripcion.POCO_ESPACIO, limiteMaximo, medioDeContacto);
                heladera.agregarSuscriptor(suscripcion);
                colaborador.agregarSuscripcion(suscripcion);
            }
            case DESPERFECTO -> {
                SuscripcionXFalla suscripcion = new SuscripcionXFalla(heladera, colaborador, TipoSuscripcion.DESPERFECTO, medioDeContacto);
                heladera.agregarSuscriptor(suscripcion);
                colaborador.agregarSuscripcion(suscripcion);
            }
        }

        repositorioHeladeras.actualizarHeladera(heladera);
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

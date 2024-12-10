package modelo.authService;

import modelo.elementos.Heladera;
import modelo.personas.Colaborador;
import modelo.suscripcion.TipoSuscripcion;
import persistencia.RepositorioColaboradores;
import persistencia.RepositorioHeladeras;

public class AuthServiceSuscripcion {

    private static RepositorioHeladeras repositorioHeladeras = RepositorioHeladeras.getInstancia();
    private static RepositorioColaboradores repositorioColaboradores = RepositorioColaboradores.getInstancia();


    public static void generarSuscripcion(Integer idHeladera, Integer idPersona, TipoSuscripcion tipoSuscripcion, Integer limiteMinimo, Integer limiteMaximo, String tipoMedioDeContacto) {

        Heladera heladera = repositorioHeladeras.buscarHeladera(idHeladera);
        Colaborador colaborador = repositorioColaboradores.buscarColaboradorXIdPersona(idPersona);

        switch (tipoSuscripcion) {
            case QUEDAN_POCAS -> {
                //realizarSuscripcionXCantidad(heladera, n, tipo, medio);
            }
            case POCO_ESPACIO -> {
                //realizarSuscripcionXCantidad(heladera, n, tipo, medio);
            }
            case DESPERFECTO -> {
                //realizarSuscripcionXFalla(heladera, medio);
            }
        }
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

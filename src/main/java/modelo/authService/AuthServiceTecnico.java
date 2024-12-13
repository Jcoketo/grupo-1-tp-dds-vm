package modelo.authService;

import modelo.consumosAPIs.servicioGeoLocalizacion.LatLong;
import modelo.consumosAPIs.servicioGeoLocalizacion.LocalizadorLatLong;
import modelo.elementos.*;
import modelo.excepciones.ExcepcionValidacion;
import modelo.personas.*;
import persistencia.*;

import java.time.LocalDate;
import java.util.List;

public class AuthServiceTecnico {
    private static RepositorioColaboradores repoColab = RepositorioColaboradores.getInstancia();
    private static RepositoriosTecnicos repoTecnicos = RepositoriosTecnicos.getInstancia();
    private static RepositorioHeladeras repoHeladeras = RepositorioHeladeras.getInstancia();
    private static RepositorioIncidentes repoIncidentes = RepositorioIncidentes.getInstancia();
    private static RepositorioUsuarios repoUsuarios = RepositorioUsuarios.getInstancia();

    public static void registrarTecnico(String nombre, String apellido, TipoDocumento tipoDoc, String numeroDoc, String cuil, String mail, String telefono, String direccion, String fechaNacimiento) {
        if (repoColab.existePersonaFisica(numeroDoc, tipoDoc) != null) {
            throw new ExcepcionValidacion("El técnico ya existe!");}

        if (repoUsuarios.existeMAIL(mail)) {
            throw new ExcepcionValidacion("El mail ya está en uso!");}


        Areas area = LocalizadorLatLong.obtenerArea(direccion);
        LatLong latLong = LocalizadorLatLong.obtenerLatitudYLongitud(direccion);
        PuntoEstrategico puntoEstrategico = new PuntoEstrategico(direccion, latLong.getLatitud(), latLong.getLongitud(), area);

        MedioDeContacto medioMAIL = new MedioDeContacto(TipoMedioDeContacto.MAIL, mail);
        PersonaHumana persona = new PersonaHumana(tipoDoc, numeroDoc, nombre, apellido, medioMAIL, direccion, fechaNacimiento);

        if(!telefono.equals("")) {
            MedioDeContacto medioTEL = new MedioDeContacto(TipoMedioDeContacto.TELEFONO, telefono);
            persona.agregarMediosDeContacto(medioTEL);
        }

        Tecnico tecnico = new Tecnico(persona, cuil, puntoEstrategico);

        repoTecnicos.registrarTecnico(tecnico);
    }

    public static void registrarVisita(Integer idTecnico, Integer idHeladera, Integer idIncidente, String descripcion,
                                       String URLfoto, Boolean problemaResuelto) {

        Heladera heladera = repoHeladeras.buscarHeladera(idHeladera);
        Tecnico tecnico = repoTecnicos.obtenerTecnico(idTecnico);

        Visita visita = new Visita(heladera, tecnico, descripcion, URLfoto, problemaResuelto);

        tecnico.registrarVisita(heladera, visita);

        Incidente incidente = repoIncidentes.devolverIncidente(idIncidente);

        if (problemaResuelto) {
            incidente.fueResuelto();
            heladera.marcarComoActiva();
        }

        repoTecnicos.registrarVisita(tecnico); // <--- persiste la visita tambien
        repoIncidentes.actualizarIncidente(incidente);
    }

}

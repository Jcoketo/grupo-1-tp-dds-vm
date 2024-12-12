package modelo.authService;

import modelo.elementos.*;
import modelo.excepciones.ExcepcionValidacion;
import modelo.personas.Tecnico;
import modelo.personas.TipoDocumento;
import modelo.personas.Visita;
import persistencia.RepositorioColaboradores;
import persistencia.RepositorioHeladeras;
import persistencia.RepositorioIncidentes;
import persistencia.RepositoriosTecnicos;

import java.util.List;

public class AuthServiceTecnico {
    private static RepositorioColaboradores repoColab = RepositorioColaboradores.getInstancia();
    private static RepositoriosTecnicos repoTecnicos = RepositoriosTecnicos.getInstancia();
    private static RepositorioHeladeras repoHeladeras = RepositorioHeladeras.getInstancia();
    private static RepositorioIncidentes repoIncidentes = RepositorioIncidentes.getInstancia();

    public static void registrarTecnico(String nombre, String apellido, TipoDocumento tipoDoc, String numeroDoc, String cuil, String mail, String telefono, Areas areaCobertura) {
        if(repoColab.existePersonaFisica(numeroDoc, tipoDoc) != null) {
            throw new ExcepcionValidacion("El técnico ya existe!");
        }

        repoTecnicos.registrarTecnico(nombre, apellido, tipoDoc, numeroDoc, cuil, mail, telefono, areaCobertura);
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

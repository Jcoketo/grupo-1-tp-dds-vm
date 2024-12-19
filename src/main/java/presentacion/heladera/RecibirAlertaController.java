package presentacion.heladera;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.elementos.Alerta;
import modelo.elementos.Heladera;
import modelo.elementos.TipoAlerta;
import modelo.notificador.Notificador;
import modelo.personas.MedioDeContacto;
import modelo.personas.Tecnico;
import modelo.personas.TipoMedioDeContacto;
import modelo.validador.Usuario;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioHeladeras;
import persistencia.RepositorioIncidentes;
import persistencia.RepositorioUsuarios;
import persistencia.RepositoriosTecnicos;

import java.util.List;

public class RecibirAlertaController implements Handler {

    private static RepositorioHeladeras repoHeladeras = RepositorioHeladeras.getInstancia();
    private static RepositorioIncidentes repoIncidentes = RepositorioIncidentes.getInstancia();
    private static RepositoriosTecnicos repoTecnicos = RepositoriosTecnicos.getInstancia();
    private static RepositorioUsuarios repoUsuarios = RepositorioUsuarios.getInstancia();

    @Override
    public void handle(@NotNull Context context) throws Exception {

        String idHeladera = context.queryParam("heladeraId");
        String tipoAlerta = context.queryParam("tipoAlerta");

        if ( idHeladera == null || idHeladera.isEmpty() || tipoAlerta == null || tipoAlerta.isEmpty() ) {
            context.redirect("/mapaHeladeras");
            return;
        }

        if ( !esNumerico(idHeladera) || !esNumerico(tipoAlerta) ) {
            context.redirect("/mapaHeladeras");
            return;
        }

        TipoAlerta tipo;

        switch (tipoAlerta){
            case "1" -> tipo = TipoAlerta.FRAUDE;
            case "2" -> tipo = TipoAlerta.FALLA_EN_CONEXION;
            case "3" -> tipo = TipoAlerta.FALLA_TEMPERATURA;
            default -> tipo = null;
        }

        if (tipo == null) {
            context.redirect("/mapaHeladeras");
            return;
        }

        Integer heladeraId = Integer.parseInt(idHeladera);

        Heladera heladera = repoHeladeras.buscarHeladera(heladeraId);

        if (heladera == null) {
            context.redirect("/mapaHeladeras");
            return;
        }

        Alerta alerta = new Alerta(tipo, heladera);
        repoIncidentes.agregarIncidente(alerta);
        heladera.marcarComoInactiva();
        repoHeladeras.actualizarHeladera(heladera);

        try {
            Tecnico tecnico = repoTecnicos.obtenerUnTecnicoXArea(heladera.getPuntoEstrategico().getAreas());
            tecnico.notificarAlerta(heladera, tipo);
        } catch (Exception e) {
            List<Usuario> usuarios = repoUsuarios.traerAdmins();
            MedioDeContacto medio = new MedioDeContacto();
            medio.setMedio(TipoMedioDeContacto.MAIL);
            usuarios.stream().forEach(usuario -> {
                medio.setContacto(usuario.getMail());
                Notificador.notificar(
                        "En el día de la fecha no hay técnicos disponibles en la zona para atender la alerta generada en " + heladera.getNombre() + " ubicada en " + heladera.getPuntoEstrategico().getDireccion() + ". \n Saludos.",
                        "Sin Técnico para arreglar heladeras",
                        medio
                );
            });
        }

        context.redirect("/mapaHeladeras");
    }

    public static boolean esNumerico(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}

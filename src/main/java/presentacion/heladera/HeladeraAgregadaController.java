package presentacion.heladera;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import modelo.authService.AuthServiceColaboracion;
import modelo.excepciones.ExcepcionValidacion;
import org.jetbrains.annotations.NotNull;
import persistencia.RepositorioHeladeras;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class HeladeraAgregadaController implements Handler {
    private RepositorioHeladeras repoHeladeras;

    public HeladeraAgregadaController(RepositorioHeladeras repoHeladeras) {
        super();
        this.repoHeladeras = repoHeladeras;
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        Map<String, Object> model = context.sessionAttribute("model");
        if (model == null) {
            model = new HashMap<>();
            context.sessionAttribute("model", model);
        }

        String nombre = context.formParam("nombre");
        String fInicio = context.formParam("fecha-inicio"); // falta parsear a LocalDate
        String capacidad = context.formParam("capacidad");
        String activa = context.formParam("activa");
        String direccion = context.formParam("direccion");
        String tMax = context.formParam("temperaturaMaxima");
        String tMin = context.formParam("temperaturaMinima");
        String usarMiDireccion = context.formParam("checkDireccion");

        /* Estos datos van a ser opcional si envia direccion o marca checkbox */
        String esXRecomendacion = context.formParam("esXRecomendacion");
        String direcRecomendada = context.formParam("direccionRecomendada");
        String latRecomendada = context.formParam("latitudRecomendada");
        String longRecomendada = context.formParam("longitudRecomendada");

        if (nombre.equals("") || activa == null) {
            model.put("errorHeladera", "Debe completar los campos obligatorios");
            context.redirect("/agregarHeladera");
            return;
        }

        if (capacidad.equals("") || !esNumerico(capacidad)) {
            model.put("errorHeladera", "La capacidad debe ser un número");
            context.redirect("/agregarHeladera");
            return;
        }

        Boolean CheckBoxDireccion = Boolean.FALSE;
        if (usarMiDireccion != null && usarMiDireccion.equals("on")) {
            CheckBoxDireccion = Boolean.TRUE;
        }
        Boolean esRecomendada = Boolean.FALSE;
        if (esXRecomendacion != null && esXRecomendacion.equals("on")) {
            esRecomendada = Boolean.TRUE;
        }

        if (!CheckBoxDireccion && (direcRecomendada == null || direcRecomendada.equals("")) && (direccion == null || direccion.equals(""))) {
            model.put("errorHeladera", "No puede ingresar una heladera sin una direccion");
            context.redirect("/agregarHeladera");
            return;
        }

        if ((CheckBoxDireccion && esRecomendada) || (CheckBoxDireccion && !direccion.equals("")) || (esRecomendada && !direccion.equals(""))) {
            model.put("errorHeladera", "No puede ingresar una heladera con dos direcciones");
            context.redirect("/agregarHeladera");
            return;
        }

        Boolean activo = Boolean.TRUE;
        if (activa.equals("1")) {
            activo = Boolean.TRUE;
        }

        if (activo && (tMax == null || tMax.equals("") || tMin == null || tMin.equals(""))) {
            model.put("errorHeladera", "Debe especificar las temperaturas máxima y mínima si la heladera está activa");
            context.redirect("/agregarHeladera");
            return;
        }

        if (activo && (fInicio == null || fInicio.equals(""))) {
            model.put("errorHeladera", "Debe especificar la fecha de inicio si la heladera está activa");
            context.redirect("/agregarHeladera");
            return;
        }

        LocalDate fechaInicio = LocalDate.parse(fInicio);
        if (activo && fechaInicio.isAfter(LocalDate.now())) {
            model.put("errorHeladera", "La fecha de inicio no puede ser posterior a la fecha actual");
            context.redirect("/agregarHeladera");
            return;
        }

        tMax = tMax.replace(",", ".");
        tMin = tMin.replace(",", ".");

        Float tempMax;
        Float tempMin;
        try {
            tempMax = Float.parseFloat(tMax);
            tempMin = Float.parseFloat(tMin);
        } catch (NumberFormatException e) {
            model.put("errorHeladera", "La temperatura máxima y mínima deben ser números");
            context.redirect("/agregarHeladera");
            return;
        }

        if (tempMax < tempMin) {
            model.put("errorHeladera", "La temperatura máxima debe ser mayor a la mínima!");
            context.redirect("/agregarHeladera");
            return;
        }

        try {
            Integer idPersona = context.sessionAttribute("idPersona");
            AuthServiceColaboracion.registrarHeladera(idPersona, nombre, capacidad, activo, direccion, fechaInicio, CheckBoxDireccion, tempMax, tempMin, esRecomendada, direcRecomendada, latRecomendada, longRecomendada);
        } catch (ExcepcionValidacion e) {
            model.put("errorHeladera", e.getMessage());
            context.redirect("/agregarHeladera");
            return;
        }

        context.redirect("/aceptarAgregarHeladera");
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
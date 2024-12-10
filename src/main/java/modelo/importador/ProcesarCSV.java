package modelo.importador;

import modelo.authService.AuthServiceColaborador;
import modelo.colaboracion.*;

import modelo.importador.validaciones.ValidarLongitudes;
import modelo.importador.validaciones.ValidarTipoDoc;
import modelo.importador.validaciones.VerificarTipoDonacion;
import modelo.personas.Colaborador;
import modelo.personas.TipoDocumento;
import persistencia.RepositorioColaboradores;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class ProcesarCSV {
    private static RepositorioColaboradores repoColab = RepositorioColaboradores.getInstancia();

    public static void ProcesarCSV(List<RegistroLeido> registrosLeidos) {

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH_mm");
        String formattedDateTime = now.format(formatter);

        EscribirLogError.crearArchivoLog(formattedDateTime + " Log errores CSV");

        for (RegistroLeido registro : registrosLeidos) {

            String tipoDoc = registro.getTipoDoc();
            String nroDocumento = registro.getNroDocumento();
            String nombre = registro.getNombre();
            String apellido = registro.getApellido();
            String mail = registro.getMail();
            String fecha = registro.getFechaDonacion(); // FORMATO YYYY/MM/DD
            String formaColaboracion = registro.getFormaColaboracion();
            String cantidad = registro.getCantidad();
            Integer record = registro.getRecord();

            if (tipoDoc.equals("") || nroDocumento.equals("") || nombre.equals("") || apellido.equals("") || mail.equals("") || fecha.equals("") || formaColaboracion.equals("") || cantidad.equals("")) {
                String mensajeError = "Error en Sintaxis.         Linea: " + record;
                EscribirLogError.escribirMensajeError(mensajeError, formattedDateTime);
                continue; // busca el siguiente registro
            }

            if (!esNumerico(nroDocumento) || !esNumerico(cantidad)) {
                String mensajeError = "Error en Sintaxis.         Linea: " + record;
                EscribirLogError.escribirMensajeError(mensajeError, formattedDateTime);
                continue; // busca el siguiente registro
            }

            boolean errorEnLongitud = ValidarLongitudes.validarLongitudes(tipoDoc, nroDocumento, nombre, apellido, mail, fecha, formaColaboracion, cantidad);
            if (errorEnLongitud) {
                String mensajeError = "Error en Sintaxis.         Linea: " + record;
                EscribirLogError.escribirMensajeError(mensajeError, formattedDateTime);
                continue; // busca el siguiente registro
            }

            boolean errorEnTipoDonacion = VerificarTipoDonacion.verificarTipoDonacion(formaColaboracion);
            if (errorEnTipoDonacion) {
                String mensajeError = "Tipo de Donacion Invalida. Linea: " + record;
                EscribirLogError.escribirMensajeError(mensajeError, formattedDateTime);
                continue; // busca el siguiente registro
            }

            if ( !fecha.equals("") ){
                if( !fecha.matches("\\d{4}/\\d{2}/\\d{2}") ){ // FORMATO YYYY/MM/DD
                    String mensajeError = "Fecha de Donacion Invalida. Linea: " + record;
                    EscribirLogError.escribirMensajeError(mensajeError, formattedDateTime);
                    continue; // busca el siguiente registro
                }
            }

            TipoDocumento tipoDocumento = ValidarTipoDoc.validarTipoDocumento(tipoDoc);
            if (tipoDocumento == null) {
                String mensajeError = "Tipo de Documento Invalido. Linea: " + record;
                EscribirLogError.escribirMensajeError(mensajeError, formattedDateTime);
                continue; // busca el siguiente registro
            }

            Colaborador colaborador = AuthServiceColaborador.procesarXCargaCSV(tipoDocumento, nroDocumento, nombre, apellido, mail, fecha, formaColaboracion, cantidad);

            LocalDate fechaDonacion = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyy/MM/dd"));

            switch (formaColaboracion) {
                case "DINERO":
                    DonarDinero donacionDinero = new DonarDinero(fechaDonacion, Double.parseDouble(cantidad));
                    donacionDinero.incrementarPuntos(colaborador);
                    colaborador.agregarColaboracion(donacionDinero);
                    repoColab.nuevaColaboracion(colaborador, donacionDinero);
                    break;
                case "DONACION_VIANDAS":
                    List<Colaboracion> donacionesDeVianda = new ArrayList<Colaboracion>();

                    for (int i = 0; i < Integer.parseInt(cantidad); i++) {
                        DonarVianda donacion = new DonarVianda(fechaDonacion);
                        donacionesDeVianda.add(donacion);
                        donacion.incrementarPuntos(colaborador);
                        colaborador.agregarColaboracion(donacion);
                    }
                    repoColab.persistirColaboraciones(donacionesDeVianda);
                    repoColab.agregar(colaborador);
                    break;
                case "REDISTRIBUCION_VIANDAS":
                    DistribucionDeViandas donacionDistribucion = new DistribucionDeViandas(fechaDonacion, Integer.parseInt(cantidad));
                    colaborador.agregarColaboracion(donacionDistribucion);
                    donacionDistribucion.incrementarPuntos(colaborador);
                    repoColab.nuevaColaboracion(colaborador, donacionDistribucion);
                    break;
                case "ENTREGA_TARJETAS":
                    RegistroPersonasSituVulnerable registroPersonasSituVulnerable = new RegistroPersonasSituVulnerable(fechaDonacion, Integer.parseInt(cantidad));
                    colaborador.agregarColaboracion(registroPersonasSituVulnerable);
                    registroPersonasSituVulnerable.incrementarPuntos(colaborador);
                    repoColab.nuevaColaboracion(colaborador, registroPersonasSituVulnerable);
                    break;
                default:
                    String mensajeError = "El campo 'formaColaboracion' no es valido. Linea: " + record;
                    EscribirLogError.escribirMensajeError(mensajeError, formattedDateTime);
                    continue; // busca el siguiente registro
            }



        }
    }

    public static boolean esNumerico(String str) {
        if (str == null) {
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

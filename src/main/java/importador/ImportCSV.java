package importador;

import colaboracion.DistribucionDeViandas;
import colaboracion.DonarDinero;
import colaboracion.DonarVianda;
import colaboracion.RegistroPersonasSituVulnerable;
import notificador.Notificador;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import personas.*;
import repositorios.RepositorioColaboradores;

import java.io.FileReader;
import java.io.Reader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class ImportCSV {
    public static void importCSV() throws IOException {
        try (Reader reader = new FileReader("src/main/resources/cargaMasivaNuevosUsuarios.csv")) {
            CSVParser csvParser = CSVFormat.DEFAULT
                    .withDelimiter(';') // el delimitador entre columna y columna es el ;
                    .parse(reader);
            for (CSVRecord record : csvParser) {
                String tipoDoc = record.get(0);
                String nroDocumento = record.get(1);
                String nombre = record.get(2);
                String apellido = record.get(3);
                String mail = record.get(4);
                String fecha = record.get(5);
                String formaColaboracion = record.get(6);
                String cantidad = record.get(7);

                /* **************************************************************************** */
                /*  I N I C I A   E L   P R O C E S O   DE   I M P O R T A R   U N   C S V      */
                /* **************************************************************************** */

                boolean errorEnLongitud = validarLongitudes(tipoDoc, nroDocumento, nombre, apellido, mail, fecha, formaColaboracion, cantidad);
                if (errorEnLongitud) {
                    String mensajeError = "Error en Sintaxis.         Linea: " + record.getRecordNumber();
                    escribirMensajeError(mensajeError);
                    continue; // busca el siguiente registro
                }

                boolean errorEnTipoDonacion = verificarTipoDonacion(formaColaboracion);
                if (errorEnTipoDonacion) {
                    String mensajeError = "Tipo de Donacion Invalida. Linea: " + record.getRecordNumber();
                    escribirMensajeError(mensajeError);
                    continue; // busca el siguiente registro
                }

                TipoDocumento tipoDocumento = validarTipoDocumento(tipoDoc);
                if (tipoDocumento == null) {
                    String mensajeError = "Tipo de Documento Invalido. Linea: " + record.getRecordNumber();
                    escribirMensajeError(mensajeError);
                    continue; // busca el siguiente registro
                }

                Colaborador colaborador = crearColaborador(tipoDocumento, nroDocumento, nombre, apellido, mail);

                agregarColaboracion(colaborador, fecha, formaColaboracion, cantidad);

                /* ********************************************************************** */
                /*  F I N   D E   P R O C E S O   DE   I M P O R T A R   U N   C S V      */
                /* ********************************************************************** */

            }
        }
    }

    private static boolean verificarTipoDonacion(String formaColaboracion) {
        return switch (formaColaboracion) {
            case "DINERO" -> false;
            case "DONACION_VIANDAS" -> false;
            case "REDISTRIBUCION_VIANDAS" -> false;
            case "ENTREGA_TARJETAS" -> false;
            default -> true;
        };
    }

    private static void agregarColaboracion(Colaborador colaborador, String fecha, String formaColaboracion, String cantidad) {
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

    private static Colaborador crearColaborador(TipoDocumento tipoDocumento, String nroDocumento, String nombre, String apellido, String mail) {
        String identificadorUnico = tipoDocumento + nroDocumento;

        RepositorioColaboradores repoColaboradores = RepositorioColaboradores.getInstancia();
        Colaborador colaborador = repoColaboradores.existeColaborador(identificadorUnico);

        if (colaborador == null) { // NO EXISTE COLABORADOR
            MedioDeContacto medioDeContacto = new MedioDeContacto(TipoMedioDeContacto.MAIL, mail);

            colaborador = new PersonaHumana(tipoDocumento, nroDocumento, nombre, apellido, medioDeContacto);
            repoColaboradores.agregar(colaborador);

            String password = PasswordGenerator.generatePassword();
            // TODO ACA IRIA EL CODIGO PARA ALMACENAR EN LA BD LAS CREDENCIALES
            String mensajeMasCredenciales = "Bienvenido a la plataforma. Su usuario es: " + identificadorUnico + " y su contraseña es: " + password;

            Notificador.notificarXNuevoUsuario(mensajeMasCredenciales, colaborador);

        }
        return colaborador;
    }

    private static Boolean validarLongitudes(String tipoDoc, String nroDocumento, String nombre, String apellido, String mail, String fecha, String formaColaboracion, String cantidad) {
        return tipoDoc.length() > 3 || nroDocumento.length() > 10 || nombre.length() > 50
                || apellido.length() > 50 || mail.length() > 50
                || fecha.length() > 10 || formaColaboracion.length() > 22
                || cantidad.length() > 7;
    }

    public static TipoDocumento validarTipoDocumento(String tipoDoc) {
        return switch (tipoDoc) {
            case "DNI" -> TipoDocumento.DNI;
            case "LC" -> TipoDocumento.LC;
            case "LE" -> TipoDocumento.LE;
            default -> null;
        };
    }

    public static void escribirMensajeError(String mensajeError) {
        String nombreArchivo = "src/main/resources/logErrores.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo, true))) {
            writer.write(mensajeError + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // METODO MAIN
    public static void main(String[] args) {
        try {
            importCSV();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }
}
package modelo.importador;

import modelo.importador.validaciones.ValidarLongitudes;
import modelo.importador.validaciones.ValidarTipoDoc;
import modelo.importador.validaciones.VerificarTipoDonacion;
import modelo.personas.Colaborador;
import modelo.personas.TipoDocumento;

import java.util.List;

import static modelo.importador.AsignarColaborador.agregarColaboracion;
import static modelo.importador.AsignarColaborador.crearColaborador;

public class ProcesarCSV {

    public static void ProcesarCSV(List<RegistroLeido> registrosLeidos) {
        for (RegistroLeido registro : registrosLeidos) {

            String tipoDoc = registro.getTipoDoc();
            String nroDocumento = registro.getNroDocumento();
            String nombre = registro.getNombre();
            String apellido = registro.getApellido();
            String mail = registro.getMail();
            String fecha = registro.getFecha();
            String formaColaboracion = registro.getFormaColaboracion();
            String cantidad = registro.getCantidad();
            Integer record = registro.getRecord();

            boolean errorEnLongitud = ValidarLongitudes.validarLongitudes(tipoDoc, nroDocumento, nombre, apellido, mail, fecha, formaColaboracion, cantidad);
            if (errorEnLongitud) {
                String mensajeError = "Error en Sintaxis.         Linea: " + record;
                EscribirLogError.escribirMensajeError(mensajeError);
                continue; // busca el siguiente registro
            }

            boolean errorEnTipoDonacion = VerificarTipoDonacion.verificarTipoDonacion(formaColaboracion);
            if (errorEnTipoDonacion) {
                String mensajeError = "Tipo de Donacion Invalida. Linea: " + record;
                EscribirLogError.escribirMensajeError(mensajeError);
                continue; // busca el siguiente registro
            }

            TipoDocumento tipoDocumento = ValidarTipoDoc.validarTipoDocumento(tipoDoc);
            if (tipoDocumento == null) {
                String mensajeError = "Tipo de Documento Invalido. Linea: " + record;
                EscribirLogError.escribirMensajeError(mensajeError);
                continue; // busca el siguiente registro
            }

            Colaborador colaborador = crearColaborador(tipoDocumento, nroDocumento, nombre, apellido, mail);
            agregarColaboracion(colaborador, fecha, formaColaboracion, cantidad);

        }
    }



}

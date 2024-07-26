package importador;

import personas.Colaborador;
import personas.TipoDocumento;
import java.util.List;

import static importador.AsignarColaborador.agregarColaboracion;
import static importador.AsignarColaborador.crearColaborador;
import static importador.EscribirLogError.escribirMensajeError;
import static importador.validaciones.ValidarLongitudes.validarLongitudes;
import static importador.validaciones.ValidarTipoDoc.validarTipoDocumento;
import static importador.validaciones.VerificarTipoDonacion.verificarTipoDonacion;

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

            boolean errorEnLongitud = validarLongitudes(tipoDoc, nroDocumento, nombre, apellido, mail, fecha, formaColaboracion, cantidad);
            if (errorEnLongitud) {
                String mensajeError = "Error en Sintaxis.         Linea: " + record;
                escribirMensajeError(mensajeError);
                continue; // busca el siguiente registro
            }

            boolean errorEnTipoDonacion = verificarTipoDonacion(formaColaboracion);
            if (errorEnTipoDonacion) {
                String mensajeError = "Tipo de Donacion Invalida. Linea: " + record;
                escribirMensajeError(mensajeError);
                continue; // busca el siguiente registro
            }

            TipoDocumento tipoDocumento = validarTipoDocumento(tipoDoc);
            if (tipoDocumento == null) {
                String mensajeError = "Tipo de Documento Invalido. Linea: " + record;
                escribirMensajeError(mensajeError);
                continue; // busca el siguiente registro
            }

            Colaborador colaborador = crearColaborador(tipoDocumento, nroDocumento, nombre, apellido, mail);
            agregarColaboracion(colaborador, fecha, formaColaboracion, cantidad);

        }
    }



}

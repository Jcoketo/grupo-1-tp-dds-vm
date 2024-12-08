package modelo.importador;

import modelo.importador.validaciones.ValidarLongitudes;
import modelo.importador.validaciones.ValidarTipoDoc;
import modelo.importador.validaciones.VerificarTipoDonacion;
import modelo.personas.Colaborador;
import modelo.personas.TipoDocumento;
import persistencia.RepositorioHeladeras;

import java.util.List;

import static modelo.importador.AsignarColaborador.agregarColaboracion;
import static modelo.importador.AsignarColaborador.crearColaborador;

public class ProcesarCSV {

    public static void ProcesarCSV(List<RegistroLeido> registrosLeidos) {

        RepositorioHeladeras repoHeladeras = RepositorioHeladeras.getInstancia();

        for (RegistroLeido registro : registrosLeidos) {

            String tipoDoc = registro.getTipoDoc();
            String nroDocumento = registro.getNroDocumento();
            String nombre = registro.getNombre();
            String apellido = registro.getApellido();
            String mail = registro.getMail();
            String fechaColab = registro.getFechaColab();         // FORMATO YYYY/MM/DD
            String formaColaboracion = registro.getFormaColaboracion();
            String monto = registro.getMonto();
            String idHeladeraDestino = registro.getIdHeladeraDestino();
            String tipoComida = registro.getTipoComida();
            String fechaCaducidad = registro.getFechaCaducidad(); // FORMATO YYYY/MM/DD
            String fechaDonacion = registro.getFechaDonacion();   // FORMATO YYYY/MM/DD
            String entregada = registro.getEntregada();
            String calorias = registro.getCalorias();
            String peso = registro.getPeso();
            String idHeladeraOrigen = registro.getIdHeladeraOrigen();
            List<Integer> idViandas = registro.getIdViandas();
            String motivoDistribucion = registro.getMotivoDistribucion();
            List<Integer> idTarjetas = registro.getIdTarjetas();
            String cantidadRepartida = registro.getCantidadRepartida();
            Integer record = registro.getRecord();

            if (!esNumerico(nroDocumento) || !esNumerico(monto) || !esNumerico(idHeladeraDestino)
                    || !esNumerico(calorias) || !esNumerico(peso) || !esNumerico(idHeladeraOrigen) || !esNumerico(cantidadRepartida)) {
                String mensajeError = "Error en Sintaxis.         Linea: " + record;
                EscribirLogError.escribirMensajeError(mensajeError);
                continue; // busca el siguiente registro
            }

            Boolean error = Boolean.FALSE;

            for (Integer idVianda : idViandas) {
                if (!esNumerico(idVianda.toString())) {
                    String mensajeError = "Error en Sintaxis.         Linea: " + record;
                    EscribirLogError.escribirMensajeError(mensajeError);
                    error = true;
                    break;
                }
            }
            if (error){
                continue;
            }

            for (Integer idTarjeta : idTarjetas) {
                if (!esNumerico(idTarjeta.toString())) {
                    String mensajeError = "Error en Sintaxis.         Linea: " + record;
                    EscribirLogError.escribirMensajeError(mensajeError);
                    error = true;
                    break;
                }
            }
            if (error){
                continue;
            }

            boolean errorEnLongitud = ValidarLongitudes.validarLongitudes(tipoDoc, nroDocumento, nombre, apellido, mail, fechaColab, formaColaboracion, fechaCaducidad,fechaDonacion,entregada, motivoDistribucion, tipoComida);
            if (errorEnLongitud) {
                String mensajeError = "Error en Sintaxis.         Linea: " + record;
                EscribirLogError.escribirMensajeError(mensajeError);
                continue; // busca el siguiente registro
            }

            if ( !fechaCaducidad.equals("") ){
                if( !fechaCaducidad.matches("\\d{4}-\\d{2}-\\d{2}") ){
                    String mensajeError = "Fecha de Caducidad Invalida. Linea: " + record;
                    EscribirLogError.escribirMensajeError(mensajeError);
                    continue; // busca el siguiente registro
                }
            }

            if ( !fechaCaducidad.equals("") ){
                if( !fechaCaducidad.matches("\\d{4}-\\d{2}-\\d{2}") ){
                    String mensajeError = "Fecha de Caducidad Invalida. Linea: " + record;
                    EscribirLogError.escribirMensajeError(mensajeError);
                    continue; // busca el siguiente registro
                }
            }

            if ( !fechaDonacion.equals("") ){
                if( !fechaDonacion.matches("\\d{4}-\\d{2}-\\d{2}") ){
                    String mensajeError = "Fecha de Donacion Invalida. Linea: " + record;
                    EscribirLogError.escribirMensajeError(mensajeError);
                    continue; // busca el siguiente registro
                }
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

            if ( !idHeladeraDestino.equals("") ){
                int idHeladera = Integer.parseInt(idHeladeraDestino);
                if( repoHeladeras.existeHeladera(idHeladera) ){
                    String mensajeError = "Heladera Destino no existe. Linea: " + record;
                    EscribirLogError.escribirMensajeError(mensajeError);
                    continue; // busca el siguiente registro
                }
            }
            if ( !idHeladeraOrigen.equals("") ){
                int idHeladera = Integer.parseInt(idHeladeraOrigen);
                if( repoHeladeras.existeHeladera(idHeladera) ){
                    String mensajeError = "Heladera Origen no existe. Linea: " + record;
                    EscribirLogError.escribirMensajeError(mensajeError);
                    continue; // busca el siguiente registro
                }
            }

            if ( !entregada.equals("") ) {
                if (!entregada.equals("1") && !entregada.equals("0")) {
                    String mensajeError = "El campo 'entregada' debe ser 1 / 0. Linea: " + record;
                    EscribirLogError.escribirMensajeError(mensajeError);
                    continue; // busca el siguiente registro
                }
            }

            Boolean errorDatosObligatorios = ValidarDatosObligatorios.validar(formaColaboracion, monto, idTarjetas, idViandas, record);

            switch (formaColaboracion) {
                case "DINERO":
                    if (monto.equals("")) {
                        String mensajeError = "El campo 'monto' no puede estar vacio. Linea: " + record;
                        EscribirLogError.escribirMensajeError(mensajeError);
                        continue; // busca el siguiente registro
                    }
                    break;
                case "DONACION_VIANDAS":
                    if (idTarjetas.isEmpty()) {
                        String mensajeError = "El campo 'idTarjetas' no puede estar vacio. Linea: " + record;
                        EscribirLogError.escribirMensajeError(mensajeError);
                        continue; // busca el siguiente registro
                    }
                    break;
                case "REDISTRIBUCION_VIANDAS":
                    if (idViandas.isEmpty()) {
                        String mensajeError = "El campo 'idViandas' no puede estar vacio. Linea: " + record;
                        EscribirLogError.escribirMensajeError(mensajeError);
                        continue; // busca el siguiente registro
                    }
                    break;

                    case "ENTREGA_TARJETAS":
                    if (idTarjetas.isEmpty()) {
                        String mensajeError = "El campo 'idTarjetas' no puede estar vacio. Linea: " + record;
                        EscribirLogError.escribirMensajeError(mensajeError);
                        continue; // busca el siguiente registro
                    }
                default:
                    String mensajeError = "El campo 'formaColaboracion' no es valido. Linea: " + record;
                    EscribirLogError.escribirMensajeError(mensajeError);
                    continue; // busca el siguiente registro
            }



            Colaborador colaborador = crearColaborador(tipoDocumento, nroDocumento, nombre, apellido, mail);
            agregarColaboracion(colaborador, fecha, formaColaboracion, cantidad);

        }
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

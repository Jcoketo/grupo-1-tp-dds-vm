package modelo.importador;

import modelo.importador.RegistroLeido;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CargarCSV {
    public static List<RegistroLeido> cargarCSV(String filename) throws Exception {
        List<RegistroLeido> registrosLeidos = new ArrayList<>();
        Reader reader = null;

        while (reader == null) {
            try {
                reader = new FileReader("src/main/resources/archivos/CSVs/" + filename);
            } catch (Exception e) {
                System.out.println("File not found, waiting for 5 seconds...");
                Thread.sleep(5000);
            }
        }

        try (CSVParser csvParser = CSVFormat.DEFAULT
                .withDelimiter(';') // el delimitador entre columna y columna es el ;
                .parse(reader)) {
            for (CSVRecord record : csvParser) {
                String tipoDoc = record.get(0);
                String nroDocumento = record.get(1);
                String nombre = record.get(2);
                String apellido = record.get(3);
                String mail = record.get(4);
                String fecha = record.get(5);
                String formaColaboracion = record.get(6);
                String cantidad = record.get(7);

                RegistroLeido registro = new RegistroLeido(tipoDoc, nroDocumento, nombre, apellido, mail, fecha, formaColaboracion, cantidad, record.getRecordNumber());
                registrosLeidos.add(registro);
            }
        }

        return registrosLeidos;
    }
}
// LA MISMA FUNCIONALIDAD PERO SI SE QUIERE MANDAR EL PATH COMO PARAMETRO
// QUE ES COMO DEBERIA SER

//public class CargarCSV {
//    static List<RegistroLeido> registrosLeidos = new ArrayList<>();
//
//    public static void cargarCSV(String path) throws IOException {
//        try (Reader reader = new FileReader(path)) {
//            CSVParser csvParser = CSVFormat.DEFAULT
//                    .withFirstRecordAsHeader() // Considera la primera fila como cabecera
//                    .withDelimiter(';') // El delimitador entre columna y columna es el ';'
//                    .parse(reader);
//            for (CSVRecord record : csvParser) {
//                String tipoDoc = record.get("TipoDoc");
//                String nroDocumento = record.get("NroDocumento");
//                String nombre = record.get("Nombre");
//                String apellido = record.get("Apellido");
//                String mail = record.get("Mail");
//                String fecha = record.get("Fecha");
//                String formaColaboracion = record.get("FormaColaboracion");
//                String cantidad = record.get("Cantidad");
//
//                RegistroLeido registro = new RegistroLeido(tipoDoc, nroDocumento, nombre, apellido, mail, fecha, formaColaboracion, cantidad, record.getRecordNumber());
//                registrosLeidos.add(registro);
//            }
//
//            RepositorioArchivos.CSVNOProcesados.add(registrosLeidos);
//        }
//    }
//}


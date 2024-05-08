package importador;

import colaboraciones.DonarDinero;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import personas.PersonaHumana;

import java.io.FileReader;
import java.io.Reader;
import java.io.IOException;
import java.lang.invoke.SwitchPoint;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.Boolean.TRUE;

public class ImportCSV {
    public static void importCSV() throws IOException {
        try (Reader reader = new FileReader("src/main/resources/formatodecsv.csv")) {
            CSVParser csvParser = CSVFormat.DEFAULT
                    .withDelimiter(';') // el delimitador entre columna y columna es el ;
                    .parse(reader);
            for (CSVRecord record : csvParser) {
                String tipoDoc = record.get(0);
                String documento = record.get(1);
                String nombre = record.get(2);
                String apellido = record.get(3);
                String mail = record.get(4);
                String fecha = record.get(5);
                String formaColaboracion = record.get(6);
                String cantidad = record.get(7);

                if( tipoDoc.length() > 3 || documento.length() > 10 || nombre.length() > 50
                                        || apellido.length() > 50 || mail.length() > 50
                                        || fecha.length() > 10 || formaColaboracion.length() > 22
                                        || cantidad.length() > 7 ){
                    System.out.println("Error de carga en archivo CSV en linea: " + record.getRecordNumber());
                    System.out.println("Uno o mas campos exceden la longitud permitida");
                    continue;
                }

                //TODO EXISTE USUARIO?
                if ( TRUE ){
                    PersonaHumana persona = buscarPersona(documento, tipoDoc);

                }
                else{
                    PersonaHumana persona = new PersonaHumana(nombre, apellido, mail, Integer.parseInt(documento), tipoDoc);
                    // si es nueva, tiene que entrar y verificar datos, entonces como hacemos para saber
                    // si un usuario esta completamente registrado o no
                    //TODO
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"); // Define el formato de la fecha
                LocalDateTime fechaColaboracionAux = LocalDateTime.parse(fecha, formatter); // Convierte el texto a LocalDateTime

                switch (formaColaboracion) {
                    case "DINERO":
                        DonarDinero donacion = new DonarDinero(persona.getTipo(), cantidad, fechaColaboracionAux);
                        break;
                    case "DONACION_VIANDAS":
                        break;
                    case "REDISTRIBUCION_VIANDAS":
                        break;
                    case "ENTREGA_TARJETAS":
                        break;
                    default:
                        System.out.println("Error de carga en archivo CSV en linea: " + record.getRecordNumber());
                        System.out.println("Forma de colaboracion no valida");
                        continue;
                }



                System.out.println("Record No - " + record.getRecordNumber());
                System.out.println("TipoDoc: " + tipoDoc);
                System.out.println("Documento: " + documento);
                System.out.println("Nombre: " + nombre);
                System.out.println("Apellido: " + apellido);
                System.out.println("mail: " + mail);
                System.out.println("fecha colaboracion: " + fecha);
                System.out.println("forma de colaboracion: " + formaColaboracion);
                System.out.println("cantidad: " + cantidad);
            }
        }
    }
}
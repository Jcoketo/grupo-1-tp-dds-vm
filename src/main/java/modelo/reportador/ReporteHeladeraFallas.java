package modelo.reportador;

import lombok.NoArgsConstructor;
import modelo.elementos.Heladera;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Entity
@DiscriminatorValue("HELADERA_FALLAS")
@NoArgsConstructor
public class ReporteHeladeraFallas extends Reporte {
    @Transient
    private Map<Heladera, Integer> datos;

    public ReporteHeladeraFallas(Map<Heladera, Integer> datos){
        this.datos = datos;
        try {
            this.path = saveToCSV("/app/static/archivos/reportes/");
        } catch (IOException e){
            this.path = "";
            e.printStackTrace();
        }
    }

    public String saveToCSV(String directory) throws IOException {
        Path dirPath = Paths.get(directory);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }

        path = dirPath.resolve("reporte_heladera_fallas_"+this.getFecha()+".csv").toString();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write("ID_HELADERA;NOMBRE_HELADERA;ESTADO;CANTIDAD_FALLAS\n");
            for (Map.Entry<Heladera, Integer> entry : datos.entrySet()) {
                Heladera heladera = entry.getKey();
                Integer contador = entry.getValue();
                writer.write(heladera.getId() + ";" + heladera.getNombre() + ";" + heladera.getActiva() + ";" + contador + "\n");
            }
        }
        return path;
    }

}

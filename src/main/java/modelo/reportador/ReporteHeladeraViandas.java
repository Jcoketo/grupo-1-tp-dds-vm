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
@DiscriminatorValue("HELADERA_VIANDAS")
@NoArgsConstructor
public class ReporteHeladeraViandas extends Reporte {
    @Transient
    private Map<Heladera, Integer> colocadas;
    @Transient
    private Map<Heladera, Integer> retiradas;

    public ReporteHeladeraViandas(Map<Heladera, Integer> colocadas, Map<Heladera, Integer> retiradas){
        this.colocadas = colocadas;
        this.retiradas = retiradas;
        try {
            this.path = saveToCSV("/static/archivos/reportes/");
        } catch (IOException e){
            this.path = "";
            e.printStackTrace();
        }
    }

    public String saveToCSV(String directory) throws IOException {
        String createdPath = directory + "reporte_heladera_viandas_"+this.getFecha()+".csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(createdPath))) {
            writer.write("ID_HELADERA;NOMBRE_HELADERA;CANTIDAD_COLOCADAS;CANTIDAD_RETIRADAS\n");
            for (Map.Entry<Heladera, Integer> entry : colocadas.entrySet()) {
                Heladera heladera = entry.getKey();
                Integer cantColocadas = entry.getValue();
                Integer cantRetiradas = this.retiradas.get(heladera);
                writer.write(heladera.getId() + ";" + heladera.getNombre() + ";" + cantColocadas + ";" + cantRetiradas + "\n");
            }
        }
        return createdPath;
    }

}


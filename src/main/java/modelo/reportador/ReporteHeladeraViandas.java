package modelo.reportador;

import modelo.elementos.Heladera;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class ReporteHeladeraViandas extends Reporte {
    private Map<Heladera, Integer> colocadas;
    private Map<Heladera, Integer> retiradas;

    public ReporteHeladeraViandas(Map<Heladera, Integer> colocadas, Map<Heladera, Integer> retiradas){
        this.colocadas = colocadas;
        this.retiradas = retiradas;
        try {
            this.path = saveToCSV("/archivos/reportes/");
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

        path = dirPath.resolve("reporte_heladera_viandas_"+this.getFecha()+".csv").toString();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write("ID_HELADERA;NOMBRE_HELADERA;CANTIDAD_COLOCADAS;CANTIDAD_RETIRADAS\n");
            for (Map.Entry<Heladera, Integer> entry : colocadas.entrySet()) {
                Heladera heladera = entry.getKey();
                Integer cantColocadas = entry.getValue();
                Integer cantRetiradas = this.retiradas.get(heladera);
                writer.write(heladera.getId() + ";" + heladera.getNombre() + ";" + cantColocadas + ";" + cantRetiradas + "\n");
            }
        }
        return path;
    }

}


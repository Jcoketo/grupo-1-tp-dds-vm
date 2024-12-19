package modelo.reportador;

import lombok.NoArgsConstructor;
import modelo.personas.Colaborador;
import modelo.personas.PersonaHumana;

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
@DiscriminatorValue("COLABORADOR")
@NoArgsConstructor
public class ReporteColaborador extends Reporte {
    @Transient
    private Map<Colaborador, Integer> datos;

    public ReporteColaborador(Map<Colaborador, Integer> datos){
        this.datos = datos;
        try {
            this.path = saveToCSV("/archivos/reportes/");
        } catch (IOException e){
            this.path = "";
            e.printStackTrace();
        }
    }

    public String saveToCSV(String directory) throws IOException {
        String createdPath = directory + "reporte_colaborador_"+this.getFecha()+".csv";
        String savePath = "/app/static" + createdPath;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(savePath))) {
            writer.write("ID_COLABORADOR;NOMBRE_COLABORADOR;VIANDAS_DONADAS\n");
            for (Map.Entry<Colaborador, Integer> entry : datos.entrySet()) {
                Colaborador colab = entry.getKey();
                Integer contador = entry.getValue();
                writer.write(colab.getId() + ";" + ((PersonaHumana)colab.getPersona()).getNombre() + ";" + contador + "\n");
            }
        }
        return createdPath;
    }


}

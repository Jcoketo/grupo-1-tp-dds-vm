package modelo.reportador;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Getter
public class GrupoReporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private LocalDate fechaCreacion;

    @Getter
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "grupo_reporte_id", referencedColumnName = "id")
    private List<Reporte> reportes;

    public GrupoReporte() {
        this.fechaCreacion = LocalDate.now();
        this.reportes = new ArrayList<Reporte>();
    }

    public String getPath(int id){
        Optional<Reporte> reporte = reportes.stream().filter(r -> r.getId() == id).findFirst();
        if(reporte.isPresent()) {
            return reporte.get().getPath();
        }
        return "";
    }

    public void agregarReporte(Reporte reporte){
        this.reportes.add(reporte);
    }

    public List<String> getPaths(){
        List<String> paths = new ArrayList<String>();
        for (Reporte reporte : reportes) {
            paths.add(reporte.getPath());
        }
        return paths;
    }
}

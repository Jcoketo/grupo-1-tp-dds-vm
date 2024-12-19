package modelo.reportador;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "reporte")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
public abstract class Reporte {
    @Id
    @GeneratedValue
    private int id;

    @Getter
    @Column
    protected String path;

    @Column
    private LocalDate fechaCreacion;

    public Reporte(){
        this.fechaCreacion = LocalDate.now();
    }

    public String getFecha(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return fechaCreacion.format(formatter);
    }
}
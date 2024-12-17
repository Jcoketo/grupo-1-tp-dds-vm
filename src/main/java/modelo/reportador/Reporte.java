package modelo.reportador;

import lombok.Getter;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "reporte")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
public abstract class Reporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    protected String path;
    private LocalDateTime fechaCreacion;

    public String getFecha(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return fechaCreacion.format(formatter);
    }
}
package modelo.elementos;

import java.time.LocalDateTime;

import javax.persistence.*;
import modelo.personas.Colaborador;


@Entity
@DiscriminatorValue("FALLA_TECNICA")
public class FallaTecnica extends Incidente{

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
    Colaborador colaborador;

    @Column
    String descripcion;

    @Column
    String URLfoto;

    public FallaTecnica(Heladera heladera, Colaborador colaborador, String descripcion, String URLfoto) {
        super(heladera);
        this.colaborador = colaborador;
        this.descripcion = descripcion;
        this.URLfoto = URLfoto;
        this.fechaHoraIncidente = LocalDateTime.now();
    }

    public FallaTecnica() {

    }
}

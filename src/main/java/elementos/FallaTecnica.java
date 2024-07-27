package elementos;

import personas.Colaborador;

import java.time.LocalDateTime;

public class FallaTecnica extends Incidente{
    Colaborador colaborador;
    String descripcion;
    String URLfoto;

    public FallaTecnica(Heladera heladera, Colaborador colaborador, String descripcion, String URLfoto) {
        super(heladera);
        this.colaborador = colaborador;
        this.descripcion = descripcion;
        this.URLfoto = URLfoto;
        this.fechaHoraIncidente = LocalDateTime.now();
    }
}

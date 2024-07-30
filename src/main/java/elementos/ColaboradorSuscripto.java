package elementos;

import lombok.Getter;
import personas.Colaborador;

public abstract class ColaboradorSuscripto {
    private Heladera heladera;
    private Colaborador colaborador;
    @Getter
    private TipoSuscripcion tipoSuscripcion;
    @Getter private Integer limiteViandasMinimas;
    @Getter private Integer limiteViandasMaximas;

    public ColaboradorSuscripto(Heladera heladera, Colaborador colaborador, TipoSuscripcion tipo) {
        this.heladera = heladera;
        this.colaborador = colaborador;
        this.tipoSuscripcion = tipo;
    }

    public void notificarmeAlerta() {
        //TODO
    }
}

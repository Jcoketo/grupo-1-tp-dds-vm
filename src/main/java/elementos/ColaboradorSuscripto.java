package elementos;

import personas.Colaborador;

public abstract class ColaboradorSuscripto {
    private Heladera heladera;
    private Colaborador colaborador;
    private TipoSuscripcion tipo;
    private Integer limiteViandasMinimas;
    private Integer limiteViandasMaximas;

    public ColaboradorSuscripto(Heladera heladera, Colaborador colaborador, TipoSuscripcion tipo) {
        this.heladera = heladera;
        this.colaborador = colaborador;
        this.tipo = tipo;
    }

    public void notificarmeAlerta()

}

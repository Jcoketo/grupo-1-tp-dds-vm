package elementos;

import personas.Colaborador;

public class ColaboradorSuscripto {
    private Heladera heladera;
    private Colaborador colaborador;
    private TipoSuscripcion tipo;

    public ColaboradorSuscripto(Heladera heladera, Colaborador colaborador, TipoSuscripcion tipo) {
        this.heladera = heladera;
        this.colaborador = colaborador;
        this.tipo = tipo;
    }
}

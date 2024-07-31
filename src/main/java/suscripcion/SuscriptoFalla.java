package suscripcion;

import elementos.Heladera;
import personas.Colaborador;
import personas.TipoMedioDeContacto;

public class SuscriptoFalla extends ColaboradorSuscripto {

    public SuscriptoFalla(Heladera heladera, Colaborador colaborador, TipoSuscripcion tipo, TipoMedioDeContacto medio) {
        super(heladera, colaborador, tipo, medio);
    }


}

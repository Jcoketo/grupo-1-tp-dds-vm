package modelo.suscripcion;

import modelo.elementos.Heladera;
import modelo.personas.Colaborador;
import modelo.personas.MedioDeContacto;
import modelo.personas.TipoMedioDeContacto;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("falla")
public class SuscripcionXFalla extends Suscripcion {

    public SuscripcionXFalla(Heladera heladera, Colaborador colaborador, TipoSuscripcion tipo, MedioDeContacto medio) {
        super(heladera, colaborador, tipo, medio);
    }


    public SuscripcionXFalla() {

    }
}

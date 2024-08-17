package personas;

import elementos.PuntoEstrategico;
import colaboracion.Colaboracion;
import elementos.Heladera;
import recomendadorDePuntos.RecomendadorDePuntos;

import java.util.List;

public class PersonaJuridica extends Persona{
    private String razonSocial;
    private TipoJuridica tipoJuridico;
    private Rubro rubro;
    private List<Heladera> heladeras;

    public PersonaJuridica(String razonSocial, TipoJuridica tipoJuridico, Rubro rubro, MedioDeContacto medioDeContacto){
        super(medioDeContacto);
        this.razonSocial = razonSocial;
        this.tipoJuridico = tipoJuridico;
        this.rubro = rubro;
        this.tipoPersona = TipoPersona.PJ;
    }

    public List<PuntoEstrategico> solicitarPuntosRecomendados(Double latitud, Double longitud, Double radio) {
        return RecomendadorDePuntos.getInstancia().obtenerPuntosRecomendados(latitud, longitud, radio);
    }

    public void incrementarPuntosXHeladera(){
        //TODO en el controller tiene que haber una crone mensual que le sume a los colaboradores juridicos
        // 5 puntos por cada heladera que tengan a cargo
        // [CANTIDAD_HELADERAS_ACTIVAS] * [∑ MESES_ACTIVAS] * 5
    }

}
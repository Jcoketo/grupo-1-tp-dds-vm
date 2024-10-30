package modelo.personas;

import modelo.elementos.PuntoEstrategico;
import modelo.elementos.Heladera;
import modelo.recomendadorDePuntos.RecomendadorDePuntos;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PersonaJuridica extends Persona{

    @Column
    private String razonSocial;

    @Enumerated(EnumType.STRING)
    private TipoJuridica tipoJuridico;

    @Enumerated(EnumType.STRING)
    private Rubro rubro;

    @Transient
    private List<Heladera> heladeras;

    public PersonaJuridica(String razonSocial, TipoJuridica tipoJuridico, Rubro rubro, MedioDeContacto medioDeContacto){
        this.mediosDeContacto = new ArrayList<MedioDeContacto>();
        this.mediosDeContacto.add(medioDeContacto);
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
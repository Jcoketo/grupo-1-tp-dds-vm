package modelo.personas;

import lombok.Getter;
import lombok.Setter;
import modelo.elementos.PuntoEstrategico;
import modelo.elementos.Heladera;
import modelo.recomendadorDePuntos.RecomendadorDePuntos;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PersonaJuridica extends Persona{

    @Column
    @Getter private String razonSocial;

    @Column
    private Integer CUIT;

    @Enumerated(EnumType.STRING)
    private TipoJuridico tipoJuridico;

    @Enumerated(EnumType.STRING)
    private Rubro rubro;

    @OneToMany
    @JoinColumn(name= "persona_juridica_id", referencedColumnName = "id")
    @Getter @Setter private List<Heladera> heladeras = new ArrayList<>();

    public PersonaJuridica(Integer CUIT, String razonSocial, TipoJuridico tipoJuridico, Rubro rubro, MedioDeContacto medioDeContacto){
        this.mediosDeContacto = new ArrayList<MedioDeContacto>();
        this.mediosDeContacto.add(medioDeContacto);
        this.razonSocial = razonSocial;
        this.tipoJuridico = tipoJuridico;
        this.rubro = rubro;
        this.tipoPersona = TipoPersona.PJ;
        this.CUIT = CUIT;
    }

    public PersonaJuridica() {

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
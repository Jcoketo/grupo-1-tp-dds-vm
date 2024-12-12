package modelo.personas;

import lombok.Getter;
import lombok.Setter;
import modelo.elementos.PuntoEstrategico;
import modelo.elementos.Heladera;
import modelo.consumosAPIs.recomendadorDePuntos.RecomendadorDePuntos;

import javax.persistence.*;
import javax.ws.rs.DefaultValue;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PersonaJuridica extends Persona{

    @Column
    @Getter private String razonSocial;

    @Column
    @DefaultValue("0")
    @Getter @Setter private String CUIT;

    @Enumerated(EnumType.STRING)
    @Getter @Setter private TipoJuridico tipoJuridico;

    @Enumerated(EnumType.STRING)
    @Getter @Setter private Rubro rubro;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name= "persona_juridica_id", referencedColumnName = "id")
    @Getter @Setter private List<Heladera> heladeras = new ArrayList<>();

    public PersonaJuridica(String CUIT, String razonSocial, TipoJuridico tipoJuridico, Rubro rubro, MedioDeContacto medioDeContacto, String direccion){
        this.mediosDeContacto = new ArrayList<MedioDeContacto>();
        this.mediosDeContacto.add(medioDeContacto);
        this.razonSocial = razonSocial;
        this.tipoJuridico = tipoJuridico;
        this.rubro = rubro;
        this.tipoPersona = TipoPersona.PJ;
        this.CUIT = CUIT;
        this.direccion = direccion;
    }

    public PersonaJuridica() {

    }

    public List<PuntoEstrategico> solicitarPuntosRecomendados(Double latitud, Double longitud, Double radio) {
        return RecomendadorDePuntos.getInstancia().obtenerPuntosRecomendados(latitud, longitud, radio);
    }

    public void agregarHeladera(Heladera heladera){
        this.heladeras.add(heladera);
    }

    public void incrementarPuntosXHeladera(){
        //TODO en el controller tiene que haber una crone mensual que le sume a los colaboradores juridicos
        // 5 puntos por cada heladera que tengan a cargo
        // [CANTIDAD_HELADERAS_ACTIVAS] * [∑ MESES_ACTIVAS] * 5
    }

}
package modelo.personas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "persona")
public abstract class Persona {

    @Id
    @GeneratedValue
    @Getter
    private int id;

    @Column
    @Getter protected String direccion;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "persona_id", referencedColumnName = "id")
    @Getter protected List<MedioDeContacto> mediosDeContacto;

    @Enumerated(EnumType.STRING)
    @Getter protected TipoPersona tipoPersona;

    /*public Persona(MedioDeContacto medioDeContacto) {
        this.mediosDeContacto = new ArrayList<MedioDeContacto>();
        this.mediosDeContacto.add(medioDeContacto);
    }*/

    public TipoPersona getTipoPersona() {
        return tipoPersona;
    }

    public void agregarMediosDeContacto(MedioDeContacto ... medioDeContactos) {
        Collections.addAll(this.mediosDeContacto, medioDeContactos);
    }

    public String getEmail(){
        for ( MedioDeContacto contactoAux : this.mediosDeContacto )
            if ( contactoAux.getMedio() == TipoMedioDeContacto.MAIL ){
                return contactoAux.getContacto();
            }
        return null;
    }
    public String getTelefono() {
        return this.mediosDeContacto.stream().filter(m -> m.getMedio().equals(TipoMedioDeContacto.TELEFONO)).findFirst().get().getContacto();
    }

    public String getUniqueIdentifier() {

        if(this.tipoPersona == TipoPersona.PH){
            return ((PersonaHumana)this).getDocumento().getUniqueIdentifier();
        }
        /*
        if(this.tipo == TipoPersona.PJ){
            return ((PersonaJuridica)this).cuit;
        }*/

        return null;
    }

    public String devolerMedioDeContacto(TipoMedioDeContacto tipo){
        for ( MedioDeContacto contactoAux : this.mediosDeContacto )
            if ( contactoAux.getMedio() == tipo ){
                return contactoAux.getContacto();
            }
        return null;
    }
}
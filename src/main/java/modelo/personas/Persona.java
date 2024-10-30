package modelo.personas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import persistencia.EntidadPersistente;

public class Persona extends EntidadPersistente {

    @Getter private String direccion;
    @Getter protected List<MedioDeContacto> mediosDeContacto;
    @Getter protected TipoPersona tipoPersona;


    public Persona(MedioDeContacto medioDeContacto) {
        this.mediosDeContacto = new ArrayList<MedioDeContacto>();
        this.mediosDeContacto.add(medioDeContacto);
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

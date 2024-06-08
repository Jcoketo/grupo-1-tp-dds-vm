package personas;

import lombok.Getter;

public class MedioDeContacto {
    @Getter private TipoMedioDeContacto medio;
    @Getter private String contacto;

    public MedioDeContacto(TipoMedioDeContacto medio, String contacto){
        this.medio = medio;
        this.contacto = contacto;
    }
}

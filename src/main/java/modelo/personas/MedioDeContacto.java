package modelo.personas;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "medio_de_contacto")
public class MedioDeContacto {
    @Id
    @GeneratedValue
    private int id;

    @Enumerated(EnumType.STRING)
    @Getter private TipoMedioDeContacto medio;

    @Column
    @Getter @Setter private String contacto;

    public MedioDeContacto(TipoMedioDeContacto medio, String contacto){
        this.medio = medio;
        this.contacto = contacto;
    }

    public TipoMedioDeContacto getTipo(){
        return this.medio;
    }

    public MedioDeContacto() {

    }
}

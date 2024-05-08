package personas;

import colaboraciones.Colaboracion;
import enums.MedioContacto;
import enums.TipoJuridica;
import enums.TipoPersona;

import java.util.List;

public class PersonaJuridica extends Colaborador{
    private String razonSocial;
    private TipoJuridica tipo;
    private String rubro;


    public PersonaJuridica(List<MedioContacto> contacto, String direccion, String razonSocial, TipoJuridica tipo, String rubro, String email) {
        super(contacto, direccion, email);
        this.razonSocial = razonSocial;
        this.tipo = tipo;
        this.rubro = rubro;
    }

    @Override
    public void colaborar(Colaboracion colaboracion) {
        colaboraciones.add(colaboracion);
        //TODO
    }

    public TipoPersona getTipo() {
        return TipoPersona.PJ;
    }
}

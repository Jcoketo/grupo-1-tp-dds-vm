package personas;

import colaboracion.Colaboracion;

import java.util.List;

public class PersonaJuridica extends Colaborador{
    private String razonSocial;
    private TipoJuridica tipo;
    private Rubro rubro;


    public PersonaJuridica(List<MedioContacto> contacto, String direccion, String razonSocial, TipoJuridica tipo, Rubro rubro, String email) {
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

package personas;

import colaboracion.Colaboracion;
import elementos.Heladera;

import java.util.List;

public class PersonaJuridica extends Colaborador{
    private String razonSocial;
    private TipoJuridica tipoJuridico;
    private Rubro rubro;
    private List<Colaboracion> colaboracionesPosibles;
    private List<Heladera> heladeras;

    public PersonaJuridica(List<MedioDeContacto> contacto, String direccion, String razonSocial, Rubro rubro, String email) {
        super(contacto, direccion);
        this.razonSocial = razonSocial;
        this.tipo = TipoPersona.PJ;
        this.rubro = rubro;

    }

    @Override
    public void colaborar(Colaboracion colaboracion) {
        this.colaboracionesRealizadas.add(colaboracion);
        //TODO
    }

    public void obtenerRecomendaciones() {
        //TODO
    }

    public void sumarPuntosHeladeras() {
        //TODO
    }
}

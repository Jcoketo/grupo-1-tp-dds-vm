package personas;

import recomendadorDePuntos.apiMock.dtos.PuntoEstrategico;
import colaboracion.Colaboracion;
import elementos.Heladera;
import recomendadorDePuntos.RecomendadorDePuntos;

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

    public List<PuntoEstrategico> obtenerRecomendaciones(Double latitud, Double longitud, Double radio) {
        return RecomendadorDePuntos.getInstancia().obtenerPuntosIdeales(latitud, longitud, radio);
    }

    public void sumarPuntosHeladeras() {
        //TODO
    }
}

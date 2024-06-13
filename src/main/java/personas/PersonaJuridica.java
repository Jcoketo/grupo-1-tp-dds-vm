package personas;

import elementos.PuntoEstrategico;
import recomendadorDePuntos.apiMock.dtos.PuntoDeColocacion;
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

    public PersonaJuridica(List<MedioDeContacto> contacto, TipoJuridica tipoJuridico, String direccion, String razonSocial, Rubro rubro, String email) {
        super(contacto);
        this.tipoJuridico = tipoJuridico;
        this.razonSocial = razonSocial;
        this.rubro = rubro;
        this.tipo = TipoPersona.PJ;
        this.direccion = direccion;
    }

    @Override
    public void colaborar(Colaboracion colaboracion) {
        this.colaboracionesRealizadas.add(colaboracion);
        //TODO
    }

    public List<PuntoEstrategico> obtenerPuntosRecomendados(Double latitud, Double longitud, Double radio) {
        return RecomendadorDePuntos.getInstancia().obtenerPuntosRecomendados(latitud, longitud, radio);
    }

    public void sumarPuntosHeladeras() {
        //TODO
    }
}

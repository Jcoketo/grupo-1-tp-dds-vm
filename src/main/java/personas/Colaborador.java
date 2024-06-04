package personas;

import colaboracion.Colaboracion;
import colaboracion.Oferta;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Colaborador {
    @Getter protected List<MedioDeContacto> mediosDeContacto;
    protected String direccion;
    protected List<Colaboracion> colaboracionesRealizadas;
    protected Float puntaje;
    protected List<Oferta> canjesRealizados;
    protected Boolean validada;
    @Getter @Setter protected TipoPersona tipo;

    public Colaborador(List<MedioDeContacto> mediosDeContacto, String direccion) {
        agregarMediosDeContacto(mediosDeContacto);
        this.direccion = direccion;
        this.colaboracionesRealizadas = new ArrayList<>();
    }

    public void agregarMediosDeContacto(List<MedioDeContacto> nuevosMedios) throws IllegalArgumentException {
        if (nuevosMedios.isEmpty()) {
            throw new IllegalArgumentException("La lista de contactos no puede estar vacía.");
        }
        this.mediosDeContacto.addAll(nuevosMedios);
    }

    public void colaborar(Colaboracion colaboracion) {
        colaboracionesRealizadas.add(colaboracion);
    }

    public void canjerPuntos(Oferta oferta){

    }

    public void validarDatos(){
        //TODO COMPLETAR DATOS CUANDO SE CREA SU USUARIO X CARGA MASIVA

        this.validada = Boolean.TRUE;
    }

    protected void iniciarSesion() {
        //TODO
    }

    /*
    public String getEmail(){
        for (MedioDeContacto contactoAux : this.mediosDeContacto)
            if ( contactoAux.getMedio() == TipoMedioDeContacto.MAIL ){
                return contactoAux.getContacto();
            }
    }
     */
}


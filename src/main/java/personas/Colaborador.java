package personas;

import colaboracion.Colaboracion;
import colaboracion.Oferta;
import elementos.Heladera;
import elementos.TarjetaPlastica;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Colaborador {
    @Getter protected List<MedioDeContacto> mediosDeContacto;
    @Getter protected String direccion;
    protected List<Colaboracion> colaboracionesRealizadas;
    protected Double puntaje;
    protected List<Oferta> canjesRealizados;
    protected Boolean validada;
    protected TarjetaPlastica tarjeta;

    @Getter protected TipoPersona tipo;

    public Colaborador(MedioDeContacto medioDeContacto) {
        this.mediosDeContacto = new ArrayList<MedioDeContacto>();
        this.mediosDeContacto.add(medioDeContacto);
        this.colaboracionesRealizadas = new ArrayList<>();
        this.puntaje = 0.0;
    }

    public void setTarjeta(TarjetaPlastica tarjeta) {
        this.tarjeta = tarjeta;
    }

    public void agregarMediosDeContacto(MedioDeContacto ... medioDeContactos) {
        Collections.addAll(this.mediosDeContacto, medioDeContactos);
    }

    public void agregarColaboracion(Colaboracion colaboracion){
        this.colaboracionesRealizadas.add(colaboracion);
    }

    public void incrementarPuntaje(Double puntaje){
        this.puntaje += puntaje;
    }

    public void canjearPuntos(Oferta oferta){
        Double puntosNecesarios = oferta.getPuntosNecesarios();
        if(puntaje > puntosNecesarios){
            puntaje -= puntosNecesarios;
            this.canjesRealizados.add(oferta);
        }
        else {
            System.out.println("Esta persona no posee los puntos necesarios para canjear esa oferta!");
        }
    }

    public void validarDatos(){
        // Este método valida que la cuenta ya tiene usuario y contraseña.
        // En caso afirmativo, cambia el valor del atributo en TRUE.
        this.validada = Boolean.TRUE;
    }

    protected void iniciarSesion() {
        //TODO
    }

    public String getEmail(){
        for ( MedioDeContacto contactoAux : this.mediosDeContacto )
            if ( contactoAux.getMedio() == TipoMedioDeContacto.MAIL ){
                return contactoAux.getContacto();
            }
        return null;
    }

    public String getUniqueIdentifier() {

        if(this.tipo == TipoPersona.PH){
            return ((PersonaHumana)this).getDocumento().getUniqueIdentifier();
        }
        /*
        if(this.tipo == TipoPersona.PJ){
            return ((PersonaJuridica)this).cuit;
        }*/

        return null;

    }


    public void realizarSuscripcion(Heladera heladera, Integer cantidadViandasLimite, CasoNotificacion casoNotificacion){
        //TODO
    }

    public void solicitarAperturaHeladera(Heladera heladera){
        //TODO
    }


}
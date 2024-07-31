package personas;

import colaboracion.Colaboracion;
import colaboracion.Oferta;
import elementos.*;
import lombok.Getter;
import repositorios.RepositorioSolicitudes;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Colaborador {
 
    @Getter protected String direccion;
    protected List<Colaboracion> colaboracionesRealizadas;
    protected Double puntaje;
    protected List<Oferta> canjesRealizados;
    protected Boolean validada;
    protected Persona persona;
    @Getter protected Tarjeta tarjeta;
    private List<ColaboradorSuscripto> suscripciones;


    // @Getter protected TipoPersona tipo;

    public TipoPersona getTipoPersona(){
        return persona.tipoPersona;
    }

    public Colaborador() {
        this.colaboracionesRealizadas = new ArrayList<>();
        this.puntaje = 0.0;
    }

    public Colaborador(Persona persona) {
        this.persona = persona;
        this.colaboracionesRealizadas = new ArrayList<>();
        this.puntaje = 0.0;
        this.validada = Boolean.FALSE;
        this.canjesRealizados = new ArrayList<>();
        //this.contadorViandas = 0;
    }

    public void recibiMiTarjeta(){
        this.tarjeta.recibida();
    }

    public void agregarMediosDeContacto(MedioDeContacto ... medioDeContactos) {
        Collections.addAll(persona.mediosDeContacto, medioDeContactos);
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
        for ( MedioDeContacto contactoAux : persona.mediosDeContacto )
            if ( contactoAux.getMedio() == TipoMedioDeContacto.MAIL ){
                return contactoAux.getContacto();
            }
        return null;
    }

    public String getUniqueIdentifier() {

        if(persona.tipoPersona == TipoPersona.PH){
            return ((PersonaHumana)persona).getDocumento().getUniqueIdentifier();
        }
        /*
        if(this.tipo == TipoPersona.PJ){
            return ((PersonaJuridica)this).cuit;
        }*/

        return null;

    }


    public void realizarSuscripcionXFalla(Heladera heladera){
        SuscriptoFalla suscripcion = new SuscriptoFalla(heladera, this, TipoSuscripcion.DESPERFECTO);
        heladera.agregarSuscriptor(suscripcion);
        this.suscripciones.add(suscripcion);
    }

    public void realizarSuscripcionXCantidad(Heladera heladera, Integer n, TipoSuscripcion tipo){

        SuscriptoCantidad suscripcion = new SuscriptoCantidad(heladera, this,tipo,n);
        heladera.agregarSuscriptor(suscripcion);
        this.suscripciones.add(suscripcion);
    }



    public void reportarFalla(Heladera heladera, String motivo, String foto){
        heladera.reportarFalla(this, motivo, foto);
    }

    public void solicitarTarjeta(){
        Tarjeta tarjeta = new Tarjeta();
        this.tarjeta = tarjeta;
    }


}

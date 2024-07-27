package personas;

import colaboracion.Colaboracion;
import colaboracion.Oferta;
import elementos.Heladera;
import elementos.SeguimientoApertura;
import elementos.TarjetaPlastica;
import elementos.TipoSolicitud;
import lombok.Getter;
import repositorios.RepositorioSolicitudes;

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
  
    protected TarjetaPlastica tarjeta;

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

    public void setTarjeta(TarjetaPlastica tarjeta) {
        this.tarjeta = tarjeta;
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


    public void realizarSuscripcion(Heladera heladera, Integer cantidadViandasLimite, CasoNotificacion casoNotificacion){
        //TODO
    }

    public void solicitarAperturaHeladera(Heladera heladera){
        heladera.permitirIngreso();
        SeguimientoApertura seguimientoApertura = new SeguimientoApertura(heladera,this, TipoSolicitud.SOLICITUD_APERTURA);
        RepositorioSolicitudes repositorioSolicitudes = RepositorioSolicitudes.getInstancia();
        repositorioSolicitudes.agregarSolicitud(seguimientoApertura);
    }

    public void reportarFalla(Heladera heladera, String motivo, String foto){
        heladera.reportarFalla(this, motivo, foto);
    }



}
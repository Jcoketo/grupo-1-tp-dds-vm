package modelo.personas;

import lombok.Generated;
import modelo.colaboracion.Colaboracion;
import modelo.colaboracion.Oferta;
//import elementos.*;
import lombok.Getter;
import modelo.elementos.Heladera;
import modelo.elementos.Tarjeta;
import modelo.suscripcion.ColaboradorSuscripto;
import modelo.suscripcion.SuscriptoCantidad;
import modelo.suscripcion.SuscriptoFalla;
import modelo.suscripcion.TipoSuscripcion;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name="colaborador")
public class Colaborador {
    @Id
    @GeneratedValue
    @Getter private int id;
 
    //@Getter protected String direccion;

    @Transient
    protected List<Colaboracion> colaboracionesRealizadas;

    @Column
    protected Double puntaje;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name= "colaborador_id",referencedColumnName = "id")
    protected List<Oferta> canjesRealizados;

    @Column
    protected Boolean validada;

    @OneToOne(cascade = CascadeType.ALL)
    @Getter protected Persona persona;

    @OneToOne(cascade = CascadeType.ALL)
    @Getter protected Tarjeta tarjeta;

    @OneToMany(mappedBy="colaborador", cascade ={CascadeType.ALL}, fetch = FetchType.EAGER )
    private List<ColaboradorSuscripto> suscripciones;

    @Transient
    @Getter protected Integer contadorViandasDonadasSemanal;

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

    public void resetearContadorViandasSemanales(){
        this.contadorViandasDonadasSemanal = 0;
    }

    public void aumentarCantidadDonacion(){
        this.contadorViandasDonadasSemanal += 1;
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

    public void realizarSuscripcionXFalla(Heladera heladera, TipoMedioDeContacto medio){
        SuscriptoFalla suscripcion = new SuscriptoFalla(heladera, this, TipoSuscripcion.DESPERFECTO, medio);
        heladera.agregarSuscriptor(suscripcion);
        this.suscripciones.add(suscripcion);
    }

    public void realizarSuscripcionXCantidad(Heladera heladera, Integer n, TipoSuscripcion tipo, TipoMedioDeContacto medio){
        SuscriptoCantidad suscripcion = new SuscriptoCantidad(heladera, this,tipo,n, medio);
        heladera.agregarSuscriptor(suscripcion);
        this.suscripciones.add(suscripcion);
    }

    /*public void reportarFalla(Heladera heladera, String motivo, String foto){
        heladera.reportarFalla(this, motivo, foto);
    }*/

    public void solicitarTarjeta(){
        Tarjeta tarjeta = new Tarjeta();
        this.tarjeta = tarjeta;
    }

    public void notificarme(String mensaje){
      //TODO
    }


}

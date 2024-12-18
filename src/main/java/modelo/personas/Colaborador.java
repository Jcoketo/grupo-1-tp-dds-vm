package modelo.personas;

import lombok.Setter;
import modelo.colaboracion.Colaboracion;
import modelo.colaboracion.Oferta;

import lombok.Getter;
import modelo.elementos.Heladera;
import modelo.elementos.Tarjeta;
import modelo.excepciones.ExcepcionCanjear;
import modelo.suscripcion.Suscripcion;
import modelo.suscripcion.SuscripcionXCantidad;
import modelo.suscripcion.SuscripcionXFalla;
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

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "colaborador_id", referencedColumnName = "id")
    @Getter List<Colaboracion> colaboracionesRealizadas;

    @Column
    @Getter protected Double puntaje;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name= "colaborador_id",referencedColumnName = "id")
    @Getter protected List<Oferta> canjesRealizados;

    @OneToOne(cascade = CascadeType.PERSIST)
    @Getter protected Persona persona;

    @OneToOne(cascade = CascadeType.ALL)
    @Getter protected Tarjeta tarjeta;

    @OneToMany(mappedBy="colaborador")
    @Getter private List<Suscripcion> suscripciones;

    @Column
    @Getter protected Integer contadorViandasDonadasSemanal;

    @Column
    @Getter @Setter
    private Boolean bajaLogica = Boolean.FALSE;

    public Colaborador() {
        this.colaboracionesRealizadas = new ArrayList<>();
        this.puntaje = 0.0;
        this.contadorViandasDonadasSemanal = 0;
    }

    public Colaborador(Persona persona, String xCSV) {
        this.persona = persona;
        this.colaboracionesRealizadas = new ArrayList<>();
        this.puntaje = 0.0;
        this.canjesRealizados = new ArrayList<>();
        this.contadorViandasDonadasSemanal = 0;
    }

    public Colaborador(Persona persona) {
        this.persona = persona;
        this.colaboracionesRealizadas = new ArrayList<>();
        this.puntaje = 0.0;
        this.canjesRealizados = new ArrayList<>();
        this.contadorViandasDonadasSemanal = 0;
    }

    public TipoPersona getTipoPersona(){ return this.persona.getTipoPersona(); }

    public void resetearContadorViandasSemanales(){
        this.contadorViandasDonadasSemanal = 0;
    }

    public void aumentarCantidadDonacion(){
        this.contadorViandasDonadasSemanal += 1;
    }

    public void recibiMiTarjeta(){
        this.tarjeta.fueRecibida();
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
            throw new ExcepcionCanjear("No tiene puntos suficientes");
        }
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

    public void solicitarTarjeta(){
        Tarjeta tarjeta = new Tarjeta();
        this.tarjeta = tarjeta;
    }

    public void agregarSuscripcion(Suscripcion suscripcion) {
        if ( this.getSuscripciones() == null ) {
            this.suscripciones = new ArrayList<>();
        }
        this.getSuscripciones().add(suscripcion);
    }
}
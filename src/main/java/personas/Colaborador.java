package personas;

import colaboracion.Colaboracion;
import colaboracion.Oferta;
import lombok.Getter;

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
  

    public Colaborador() {
        this.colaboracionesRealizadas = new ArrayList<>();
        this.puntaje = 0.0;
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

}
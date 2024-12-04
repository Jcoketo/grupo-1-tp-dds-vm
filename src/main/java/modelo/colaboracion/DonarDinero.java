package modelo.colaboracion;

import lombok.Getter;
import lombok.Setter;
import modelo.excepciones.ExcepcionValidacion;
import modelo.personas.Colaborador;
import modelo.personas.TipoPersona;
import javax.persistence.*;

import java.time.LocalDate;
import java.util.Arrays;

@Entity
@DiscriminatorValue("DONACION_DINERO")
public class DonarDinero extends Colaboracion{

    @Column
    @Getter private Double monto;

    @Enumerated(EnumType.STRING)
    private FrecuenciaDonacion frecuencia;

    @Transient
    @Setter private static Double coeficiente = 1.5;

    // CONSTRUCTOR PRINCIPAL
    public DonarDinero(LocalDate fechaDonacion, Double monto, FrecuenciaDonacion frecuenciaDonacion) {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PJ, TipoPersona.PH);
        this.fechaColaboracion = fechaDonacion;
        this.monto = monto;
        this.frecuencia = frecuenciaDonacion;
    }

    // CONSTRUCTOR PARA IMPORTADOR CSV
    public DonarDinero(LocalDate fechaDonacion, Double monto) {
        this.tiposPersonasHabilitadas = Arrays.asList(TipoPersona.PJ, TipoPersona.PH);
        this.fechaColaboracion = fechaDonacion;
        this.monto = monto;
        this.frecuencia = FrecuenciaDonacion.UNICA; // Es unica? TODO
    }

    public DonarDinero() {

    }

    @Override
    public void hacerColaboracion(Colaborador colaborador) {
        /*String text = validar(colaborador);
        if(text == null){
            incrementarPuntos(colaborador);
            colaborador.agregarColaboracion(this);
        }
        else {
            System.out.println("Error!!!");
            System.out.println(text);
        }*/ // CAMBIO COCO - MAS FACIL - NO HACE FALTA EL IF
        this.validar(colaborador);
        incrementarPuntos(colaborador);
        colaborador.agregarColaboracion(this);

    }
    // TODO YA NO HARIA FALTA
    // DEBERIA SER VOID
    @Override
    public String validar(Colaborador colaborador) {
        if(!this.tiposPersonasHabilitadas.contains(colaborador.getTipoPersona())){
            throw new ExcepcionValidacion("Ese Tipo de Persona no puede realizar este tipo de Colaboración!");
        }
        return "true";
    }

    @Override
    public void incrementarPuntos(Colaborador colaborador){
        colaborador.incrementarPuntaje(this.monto * coeficiente);
    }

}
package modelo.suscripcion;

import modelo.elementos.Heladera;
import lombok.Getter;
import modelo.notificador.Notificador;
import modelo.personas.Colaborador;
import modelo.personas.MedioDeContacto;
import modelo.personas.TipoMedioDeContacto;

import javax.persistence.*;

@Entity
@Table(name = "colaborador_suscripto")
public abstract class ColaboradorSuscripto {

    @Id
    @GeneratedValue
    @Getter private Long id;

    @ManyToOne
    private Heladera heladera;

    @Transient
    private Colaborador colaborador;

    @Enumerated(EnumType.STRING)
    @Getter private TipoSuscripcion tipoSuscripcion;

    @Column
    @Getter private Integer limiteViandasMinimas;

    @Column
    @Getter private Integer limiteViandasMaximas;

    @OneToOne
    @JoinColumn(name = "Colaborador_suscripto_id",referencedColumnName = "id")
    private MedioDeContacto medioDeContacto;

    public ColaboradorSuscripto(Heladera heladera, Colaborador colaborador, TipoSuscripcion tipo, TipoMedioDeContacto medio) {
        this.heladera = heladera;
        this.colaborador = colaborador;
        this.tipoSuscripcion = tipo;
        this.medioDeContacto = new MedioDeContacto(medio, colaborador.getPersona().devolerMedioDeContacto(medio));
        this.validarExistenciaMedioDeContacto();
    }

    // esta validacion debe hacerse en el controller
    public void validarExistenciaMedioDeContacto() {
        if (this.medioDeContacto.getContacto() == null) {
            throw new RuntimeException("No se puede notificar al colaborador, no tiene cargado el medio de contacto");
        }
    }

    public void notificarmeAlerta() {
        String texto;
        String asunto;
        switch (this.tipoSuscripcion) {
            case POCO_ESPACIO:
                texto = "La heladera en " + heladera.getPuntoEstrategico().getDireccion() + " tiene poco espacio!";
                asunto = "Poco espacio en heladera";
                Notificador.notificar(texto, asunto, colaborador, medioDeContacto.getMedio());
                break;
            case QUEDAN_POCAS:
                texto = "En la heladera ubicada en " + heladera.getPuntoEstrategico().getDireccion() + " quedan pocas viandas!";
                asunto = "Quedan pocas viandas!";
                Notificador.notificar(texto, asunto, colaborador, medioDeContacto.getMedio());
                break;
            case DESPERFECTO:
                Sugerencia sugerencia = new Sugerencia(heladera);
                texto = sugerencia.devolerMensajeSugerencia();
                asunto = "Hubo un Desperfecto!";
                Notificador.notificar(texto, asunto, colaborador, medioDeContacto.getMedio());
                break;
            default:
                break;

        }

    }
}

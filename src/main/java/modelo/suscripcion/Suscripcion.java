package modelo.suscripcion;

import lombok.Setter;
import modelo.elementos.Heladera;
import lombok.Getter;
import modelo.notificador.Notificador;
import modelo.personas.Colaborador;
import modelo.personas.MedioDeContacto;

import javax.persistence.*;

@Entity
@Table
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class
Suscripcion {

    @Id
    @GeneratedValue
    @Getter
    private int id;

    @ManyToOne
    @JoinColumn(name="heladera_id", referencedColumnName = "id")
    @Getter @Setter protected Heladera heladera;

    @ManyToOne
    @JoinColumn(name="colaborador_id",referencedColumnName = "id")
    @Getter @Setter protected Colaborador colaborador;

    @Enumerated(EnumType.STRING)
    @Getter protected TipoSuscripcion tipoSuscripcion;

    @Getter @Setter
    @OneToOne(cascade = CascadeType.PERSIST)
    protected MedioDeContacto medioDeContacto;

    @Getter @Setter
    @Column protected Boolean bajaLogica = Boolean.FALSE;

    public Suscripcion(Heladera heladera, Colaborador colaborador, TipoSuscripcion tipo, MedioDeContacto medio) {
        this.heladera = heladera;
        this.colaborador = colaborador;
        this.tipoSuscripcion = tipo;
        this.medioDeContacto = medio;
    }

    public Suscripcion() {

    }

    // esta validacion debe hacerse en el controller
    public void validarExistenciaMedioDeContacto() {
        if (this.medioDeContacto.getContacto() == null) {
            throw new RuntimeException("No se puede notificar al colaborador, no tiene cargado el medio de contacto");
        }
    }

    public void notificarmeSuscripcion() {
        String texto;
        String asunto;
        switch (this.tipoSuscripcion) {
            case POCO_ESPACIO:
                texto = "La heladera " + heladera.getNombre() + " ubicada en " + heladera.getPuntoEstrategico().getDireccion() + " tiene poco espacio!" +
                        "\n\nCorre a distribuir sus viandas y suma mas puntos!";
                asunto = "Queda poco espacio en " + heladera.getNombre() + "!";
                Notificador.notificar(texto, asunto, medioDeContacto);
                break;
            case QUEDAN_POCAS:
                texto = "La heladera " + heladera.getNombre()  + " ubicada en " + heladera.getPuntoEstrategico().getDireccion() + " tiene pocas viandas!" +
                        "\n\nVe a rellenarla con mas viandas y consigue mas puntos!";
                asunto = "Quedan pocas viandas en " + heladera.getNombre() + "!";
                Notificador.notificar(texto, asunto, medioDeContacto);
                break;
            case DESPERFECTO:
                Sugerencia sugerencia = new Sugerencia(heladera);
                texto = sugerencia.devolerMensajeSugerencia();
                asunto = "Hubo un Desperfecto en " + heladera.getNombre() +"!";
                Notificador.notificar(texto, asunto, medioDeContacto);
                break;
            default:
                break;

        }

    }

    public void darDeBaja() {
        this.bajaLogica = Boolean.TRUE;
        this.setHeladera(null);
    }

    public void notificarBaja() {
        String tipo;
        switch (tipoSuscripcion) {
            case POCO_ESPACIO -> tipo = "haya poco espacio";
            case QUEDAN_POCAS -> tipo = "quedan pocas viandas";
            case DESPERFECTO -> tipo = "haya un desperfecto";
            default -> tipo = "desconocido";
        }

        String texto = "Se ha dado de baja la suscripcion de notificarte cuando " + tipo + " en la heladera " + heladera.getNombre() + ", ya que la heladera fue dada de baja." +
                "\n\n Si cree que esto es un error, contactece con comunidadessolidarias@gmail.com";
        String asunto = "Baja de suscripcion en " + heladera.getNombre();
        Notificador.notificar(texto, asunto, medioDeContacto);
    }
}

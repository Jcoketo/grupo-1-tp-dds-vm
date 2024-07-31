package suscripcion;

import elementos.Heladera;
import lombok.Getter;
import notificador.Notificador;
import personas.Colaborador;
import personas.MedioDeContacto;
import personas.TipoMedioDeContacto;

public abstract class ColaboradorSuscripto {
    private Heladera heladera;
    private Colaborador colaborador;
    @Getter
    private TipoSuscripcion tipoSuscripcion;
    @Getter private Integer limiteViandasMinimas;
    @Getter private Integer limiteViandasMaximas;
    private MedioDeContacto medioDeContacto;

    public ColaboradorSuscripto(Heladera heladera, Colaborador colaborador, TipoSuscripcion tipo, TipoMedioDeContacto medio) {
        this.heladera = heladera;
        this.colaborador = colaborador;
        this.tipoSuscripcion = tipo;
        this.medioDeContacto = new MedioDeContacto(medio, colaborador.getPersona().devolerMedioDeContacto(medio));
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
                asunto = "Hubo un Despesfecto!";
                Notificador.notificar(texto, asunto, colaborador, medioDeContacto.getMedio());
                break;
            default:
                break;

        }

    }
}

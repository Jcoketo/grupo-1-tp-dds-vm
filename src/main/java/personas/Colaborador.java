package personas;

import colaboraciones.Colaboracion;
import enums.MedioContacto;
import enums.TipoPersona;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Colaborador {
    @Getter
    protected List<MedioContacto> contacto;
    @Getter
    protected String email;
    //TODO
    //hay que evaluar si tenemos un atributo email, o ponemos un diccionario de medio de contacto e info
    // porque como lo teniamos modelado, nunca guardamos el mail, el numero, etc
    protected String direccion;
    protected List<Colaboracion> colaboraciones;
    protected Integer puntaje;
    protected TipoPersona tipo;

    public Colaborador(String email){
        this.email = email;
        this.contacto = new ArrayList<>();
        this.colaboraciones = new ArrayList<>();
    }

    public Colaborador(List<MedioContacto> contacto, String direccion, String email ) {
        this.contacto = contacto;
        this.direccion = direccion;
        ArrayList<Colaboracion> colaboraciones = new ArrayList<>();
        this.email = email;
    }

    public void agregarContacto(MedioContacto contacto) {
        this.contacto.add(contacto);
    }

    public void colaborar(Colaboracion colaboracion) {
        colaboraciones.add(colaboracion);
    }

    public Integer calcularPuntaje() {
        this.puntaje = 0;
        Double pesosDonados = (double) 0;
        Integer viandasDonadas = 0;
        Integer viandasDistribuidas = 0;
        Integer tarjetasRepartidas = 0;
        Integer cantidadHeladeras = 0;

        for (Colaboracion colaboracion : colaboraciones) {
            switch (colaboracion.getTipo()) {
                case "DONAR_VIANDA":
                    viandasDonadas += 1;
                    break;
                case "DONAR_DINERO":
                    Double pesos = colaboracion.getMonto();
                    pesosDonados = pesosDonados + pesos;
                    break;
                case "DISTRIBUCION_VIANDA":
                    viandasDistribuidas += 1;
                    break;
                case "TARJETAS_REPARTIDAS":
                    tarjetasRepartidas += 1;
                    break;
                case "HACERSE_CARGO_HELADERA":
                    //TODO
                    Integer puntosHeladeras = 0;
                    break;
            }

        }

        puntaje = (int)(viandasDonadas * 1.5)
                    + viandasDistribuidas
                            + (tarjetasRepartidas * 2) ;
                            // + cantidadHeladeras;

        return puntaje;
    }

}

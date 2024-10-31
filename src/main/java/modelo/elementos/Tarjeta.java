package modelo.elementos;

import lombok.Getter;
import persistencia.RepositorioTarjetas;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tarjeta")
public class Tarjeta {

    @Id
    @GeneratedValue
    @Getter private long id;

    @Column
    protected String nro_tarjeta;

    @OneToMany
    @JoinColumn(name="uso_tarjeta_id",referencedColumnName = "id")
    protected List<UsoTarjetaPlastica> historialDeUsos;

    @Column
    protected Boolean recibida;

    public Tarjeta(){
        RepositorioTarjetas repositorioTarjetas = RepositorioTarjetas.getInstancia();
        this.nro_tarjeta = repositorioTarjetas.generarIdTarjeta();
        this.historialDeUsos = new ArrayList<UsoTarjetaPlastica>();
        this.recibida = false;
    }

    public void registrarUso(Heladera heladera){
        this.historialDeUsos.add(new UsoTarjetaPlastica(heladera));
    }

    public void recibida(){
        this.recibida = true;
    }
}

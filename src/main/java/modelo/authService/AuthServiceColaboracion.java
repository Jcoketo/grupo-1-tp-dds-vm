package modelo.authService;

import modelo.colaboracion.*;
import modelo.elementos.Heladera;
import modelo.elementos.PuntoEstrategico;
import modelo.elementos.TarjetaPlastica;
import modelo.excepciones.ExcepcionValidacion;
import modelo.personas.Colaborador;
import persistencia.RepositorioColaboradores;
import persistencia.RepositorioHeladeras;
import persistencia.RepositorioTarjetas;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AuthServiceColaboracion {

    private static RepositorioColaboradores repoColab = RepositorioColaboradores.getInstancia();
    private static RepositorioHeladeras repoHeladeras = RepositorioHeladeras.getInstancia();
    private static RepositorioTarjetas repoTarjetas = RepositorioTarjetas.getInstancia();

    public static void registrarColaboracionDinero(Integer idPersona, String monto){
        Colaborador colab = repoColab.buscarColaboradorXIdPersona(idPersona);
        DonarDinero donacion = new DonarDinero(LocalDate.now(), Double.parseDouble(monto));
        donacion.hacerColaboracion(colab);
        repoColab.nuevaColaboracion(colab, donacion);
    }

    public static void registrarColaboracionVianda(Integer idPersona, Integer heladeraId, String comida, String fechaCaducidad, Integer pesoVianda, Integer calorias, LocalDateTime fechaDonacion) {
        Colaborador colab = repoColab.buscarColaboradorXIdPersona(idPersona);
        Heladera heladera = repoHeladeras.buscarHeladera(heladeraId);
        Vianda vianda = new Vianda(comida, LocalDate.parse(fechaCaducidad), LocalDate.now(), colab, heladera, calorias, pesoVianda);
        DonarVianda donacion = new DonarVianda(vianda, heladera, fechaDonacion.toLocalDate());
        donacion.hacerColaboracion(colab);
        repoHeladeras.actualizarHeladera(heladera);
        repoColab.nuevaColaboracion(colab, donacion);

    }

    public static void registrarColaboracionDistribuirViandas(Integer idPersona, Integer idHeladeraOrigen, Integer idHeladeraDestino, MotivoDistribucion motivoDistribucion, Integer cantidadViandas) {
        Heladera origen = repoHeladeras.buscarHeladera(idHeladeraOrigen);
        Heladera destino = repoHeladeras.buscarHeladera(idHeladeraDestino);
        Colaborador colab = repoColab.buscarColaboradorXIdPersona(idPersona);

        if (!destino.entranXViandasMas(cantidadViandas)){
            throw new ExcepcionValidacion("No hay espacio suficiente en la heladera de destino para esa cantidad de viandas!");
        }
        if (!origen.tieneNViandasDisponibles(cantidadViandas)){
            throw new ExcepcionValidacion("No hay suficientes viandas en la heladera de origen!");
        }

        List<Vianda> viandas = new ArrayList<>();
        for (int i = 0; i < cantidadViandas; i++){
            System.out.println(i + ' ');
            Vianda vianda = origen.conocerVianda(i);
            viandas.add(vianda);
        }

        DistribucionDeViandas distribucion = new DistribucionDeViandas(origen, destino, motivoDistribucion, LocalDate.now());
        distribucion.setViandas(viandas);
        distribucion.hacerColaboracion(colab);
        repoColab.persistirViandas(viandas);
        repoHeladeras.actualizarHeladera(origen);
        repoHeladeras.actualizarHeladera(destino);
        repoColab.nuevaColaboracion(colab, distribucion);

    }

    public static void registrarPersonasVulnerables(Integer idPersona){
        Colaborador colab = repoColab.buscarColaboradorXIdPersona(idPersona);

        RegistroPersonasSituVulnerable colaboracion = repoColab.traerColaboradoresXColaboradorPersonaSitu(colab);

        if (colaboracion != null) {
            if ((colaboracion.getTarjetas().size() - colaboracion.getCantidadRepartida() > 0))
                throw new ExcepcionValidacion("Tienes tarjetas para repartir!");
        }

        List<TarjetaPlastica> tarjetas = repoTarjetas.crearNTarjetasPlasticas(2);

        RegistroPersonasSituVulnerable registroPersonasSituVulnerable = new RegistroPersonasSituVulnerable(2, tarjetas, LocalDate.now());

        colab.agregarColaboracion(registroPersonasSituVulnerable);

        repoColab.nuevaColaboracion(colab, registroPersonasSituVulnerable);
    }

    public static void registrarColaboracionHeladera(Integer idPersona, String nombre, LocalDate fechaInicio, Integer capacidad, Boolean activa, String direccion) {
        Colaborador colab = repoColab.buscarColaboradorXIdPersona(idPersona);
        PuntoEstrategico puntoColocacion = new PuntoEstrategico(direccion);

        Heladera heladeraNueva = new Heladera(nombre, capacidad, puntoColocacion, activa, fechaInicio);

        repoHeladeras.agregarHeladera(heladeraNueva);



    }
}
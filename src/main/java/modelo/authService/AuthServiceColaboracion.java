package modelo.authService;

import modelo.colaboracion.*;
import modelo.elementos.Heladera;
import modelo.excepciones.ExcepcionValidacion;
import modelo.personas.Colaborador;
import persistencia.RepositorioColaboradores;
import persistencia.RepositorioHeladeras;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AuthServiceColaboracion {

    private static RepositorioColaboradores repoColab = RepositorioColaboradores.getInstancia();
    private static RepositorioHeladeras repoHeladeras = RepositorioHeladeras.getInstancia();

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

        List<Vianda> viandas = new ArrayList<>();
        for (int i = 0; i < cantidadViandas; i++){
            Vianda vianda = origen.conocerVianda(i);
            viandas.add(vianda);
        }
        //List<Vianda> viandas = repoHeladeras.obtenerViandasDeHeladera(origen, cantidadViandas);
        DistribucionDeViandas distribucion = new DistribucionDeViandas(cantidadViandas, origen, destino, motivoDistribucion, LocalDate.now());
        distribucion.setViandas(viandas);
        distribucion.hacerColaboracion(colab);
        repoHeladeras.actualizarHeladera(origen);
        repoHeladeras.actualizarHeladera(destino);
        repoColab.persistirViandas(viandas);
        repoColab.nuevaColaboracion(colab, distribucion);

    }
}
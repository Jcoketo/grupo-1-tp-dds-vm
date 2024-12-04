package modelo.authService;

import modelo.colaboracion.DonarDinero;
import modelo.colaboracion.DonarVianda;
import modelo.colaboracion.Vianda;
import modelo.elementos.Heladera;
import modelo.personas.Colaborador;
import persistencia.RepositorioColaboradores;
import persistencia.RepositorioHeladeras;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
        // MERGEAR HELADERA
    }
}
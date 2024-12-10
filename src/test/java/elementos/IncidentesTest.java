package elementos;

import modelo.elementos.Alerta;
import modelo.elementos.Areas;
    import modelo.elementos.Heladera;
    import modelo.elementos.PuntoEstrategico;
import modelo.elementos.ReceptorMovimiento;
import modelo.elementos.TipoAlerta;
import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import modelo.personas.Colaborador;
    import modelo.personas.MedioDeContacto;
    import modelo.personas.PersonaHumana;
    import modelo.personas.Tecnico;
    import modelo.personas.TipoMedioDeContacto;
import persistencia.RepositorioIncidentes;
import persistencia.RepositoriosTecnicos;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class IncidentesTest {

 /* private Heladera heladera;
  private Colaborador colaborador;

  @BeforeEach
  public void setUp() {

    RepositorioIncidentes.getInstancia().incidentes.clear();

    // Crear entidades
    PersonaHumana personaHumana = new PersonaHumana("Juan", "Perez", new MedioDeContacto(TipoMedioDeContacto.MAIL, "juanperez@gmail.com"));
    colaborador = new Colaborador(personaHumana);

    PersonaHumana personaHumanaTecnico = new PersonaHumana("Luis", "Gonzalez", new MedioDeContacto(TipoMedioDeContacto.MAIL, "luisgonzalez@gmail.com"));
    Tecnico tecnico = new Tecnico("2744444430", Areas.PALERMO, personaHumanaTecnico);
    RepositoriosTecnicos.getInstancia().agregar(tecnico);

    PuntoEstrategico puntoEstrategico = new PuntoEstrategico(0.0, 0.0);
    puntoEstrategico.setAreas(Areas.PALERMO);

    heladera = new Heladera(10, puntoEstrategico);
    heladera.setActiva(true);

    // Reportar falla
    heladera.reportarFalla(colaborador, "Falla en el motor", "http://urlfoto.com");
  }

  @Test
  public void testHeladeraInactivaPorFalla() {
    // Verificar que la heladera se marcó como inactiva
    assertFalse(heladera.getActiva());
  }

  @Test
  public void testIncidenteRegistrado(){
    //verifica que el incidente se haya registrado en el repositorio
    assertEquals(1, RepositorioIncidentes.getInstancia().incidentes.size());
  }

  @Test
  public void testRecibirAlerta(){
    ReceptorMovimiento receptorMovimiento;

    receptorMovimiento = new ReceptorMovimiento(heladera);

    receptorMovimiento.recibirAlerta();

    // Verificar que la alerta se agregó al repositorio
    assertTrue(RepositorioIncidentes.getInstancia().incidentes.stream()
        .anyMatch(incidente -> incidente instanceof Alerta && ((Alerta) incidente).getTipoAlerta() == TipoAlerta.FRAUDE && incidente.getHeladera().equals(heladera)));
  }*/
}
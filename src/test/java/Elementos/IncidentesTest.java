package Elementos;

import elementos.Alerta;
import elementos.Areas;
    import elementos.Heladera;
    import elementos.PuntoEstrategico;
import elementos.ReceptorMovimiento;
import elementos.TipoAlerta;
import java.time.LocalDate;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import personas.Colaborador;
    import personas.MedioDeContacto;
    import personas.PersonaHumana;
    import personas.Tecnico;
    import personas.TipoMedioDeContacto;
import repositorios.RepositorioIncidentes;
import repositorios.RepositoriosTecnicos;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class IncidentesTest {

  private Heladera heladera;
  private Colaborador colaborador;

  @BeforeEach
  public void setUp() {
    // Crear entidades
    PersonaHumana personaHumana = new PersonaHumana("Juan", "Perez", new MedioDeContacto(TipoMedioDeContacto.MAIL, "juanperez@gmail.com"));
    colaborador = new Colaborador(personaHumana);

    PersonaHumana personaHumanaTecnico = new PersonaHumana("Luis", "Gonzalez", new MedioDeContacto(TipoMedioDeContacto.MAIL, "luisgonzalez@gmail.com"));
    Tecnico tecnico = new Tecnico("2744444430", Areas.PALERMO, personaHumanaTecnico);
    RepositoriosTecnicos.getInstancia().agregar(tecnico);

    PuntoEstrategico puntoEstrategico = new PuntoEstrategico(0.0, 0.0);
    puntoEstrategico.setAreas(Areas.PALERMO);

    heladera = new Heladera(10, LocalDate.now(), puntoEstrategico);
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
  }
}
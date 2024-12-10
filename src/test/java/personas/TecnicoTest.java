package personas;

import modelo.elementos.Areas;
import modelo.elementos.Heladera;
import modelo.elementos.PuntoEstrategico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import modelo.personas.MedioDeContacto;
import modelo.personas.PersonaHumana;
import modelo.personas.Tecnico;
import modelo.personas.TipoMedioDeContacto;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TecnicoTest {

  /*private Tecnico tecnico;
  private Heladera heladera;

  @BeforeEach
  public void setUp() {
    PersonaHumana personaHumanaTecnico = new PersonaHumana("Luis", "Gonzalez", new MedioDeContacto(TipoMedioDeContacto.MAIL, "luisgonzalez@gmail.com"));
    tecnico = new Tecnico("2744444430", Areas.PALERMO, personaHumanaTecnico);

    PuntoEstrategico puntoEstrategico = new PuntoEstrategico(0.0, 0.0);
    puntoEstrategico.setAreas(Areas.PALERMO);

    heladera = new Heladera(10, puntoEstrategico);
    heladera.setActiva(false);

    // Registrar visita y solucionar incidente
    tecnico.registrarVisita(heladera, "Reparación del motor", "http://urlfoto.com", true);
  }

  @Test
  public void testVisitaRegistradaEIncidenteSolucionado() {
    // Verificar que la visita se registró (por ende el incidente se solucionó)
    assertTrue(heladera.getActiva());
  }*/
}
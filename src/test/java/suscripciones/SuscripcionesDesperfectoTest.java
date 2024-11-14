package suscripciones;

import modelo.elementos.Heladera;
import modelo.elementos.PuntoEstrategico;
import modelo.elementos.ReceptorMovimiento;
import modelo.notificador.Notificador;
import modelo.notificador.StrategyNotificacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import modelo.suscripcion.Suscripcion;
import java.time.LocalDate;
import modelo.personas.Colaborador;
import modelo.personas.MedioDeContacto;
import modelo.personas.PersonaHumana;
import modelo.personas.TipoMedioDeContacto;
import modelo.suscripcion.SuscripcionXFalla;
import modelo.suscripcion.TipoSuscripcion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


public class SuscripcionesDesperfectoTest {

  private Heladera heladera;
  private ReceptorMovimiento receptorMovimiento;
  private Colaborador colaborador;
  private Suscripcion suscripcion;
  private StrategyNotificacion strategyNotificacionMock;
  private PuntoEstrategico puntoEstrategico;



  @BeforeEach
  public void setUp() {
    heladera = new Heladera(10, LocalDate.now(), null);
    puntoEstrategico = new PuntoEstrategico(0.0, 0.0);
    heladera.setPuntoEstrategico(puntoEstrategico);

    receptorMovimiento = new ReceptorMovimiento(heladera);

    PersonaHumana personaHumana2 = new PersonaHumana("Luis", "Gonzalez", new MedioDeContacto(TipoMedioDeContacto.MAIL, "luisgonzalez@gmail.com"));

    colaborador = new Colaborador(personaHumana2);

    suscripcion = new SuscripcionXFalla(heladera,colaborador, TipoSuscripcion.DESPERFECTO, TipoMedioDeContacto.MAIL);

    heladera.agregarSuscriptor(suscripcion);

    strategyNotificacionMock = mock(StrategyNotificacion.class);
    Notificador.agregarEstrategia(TipoMedioDeContacto.MAIL, strategyNotificacionMock);


  }
  @Test
  public void testSuscripcionExitosa() {
    assertTrue(heladera.getColaboradoresSucriptos().contains(suscripcion));
  }

  @Test
  public void testNotificarmeAlerta() {
    suscripcion.notificarmeAlerta();

    ArgumentCaptor<String> textoCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> asuntoCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<Colaborador> colaboradorCaptor = ArgumentCaptor.forClass(Colaborador.class);

    verify(strategyNotificacionMock).enviarNotificacion(textoCaptor.capture(), colaboradorCaptor.capture(), asuntoCaptor.capture());

    assertEquals("Hubo un Desperfecto!", asuntoCaptor.getValue());
    assertEquals(colaborador, colaboradorCaptor.getValue());
  }

}
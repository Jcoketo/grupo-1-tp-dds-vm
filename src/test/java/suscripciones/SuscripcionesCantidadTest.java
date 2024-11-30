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
import modelo.personas.Colaborador;
import modelo.personas.MedioDeContacto;
import modelo.personas.PersonaHumana;
import modelo.personas.TipoMedioDeContacto;
import modelo.suscripcion.SuscripcionXCantidad;
import modelo.suscripcion.TipoSuscripcion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SuscripcionesCantidadTest {

  private Heladera heladera;
  private ReceptorMovimiento receptorMovimiento;
  private Colaborador colaborador;
  private Suscripcion suscripcion;
  private StrategyNotificacion strategyNotificacionMock;
  private PuntoEstrategico puntoEstrategico;


  @BeforeEach
  public void setUp() {
    heladera = new Heladera(5, null);
    puntoEstrategico = new PuntoEstrategico(0.0, 0.0);
    puntoEstrategico.setDireccion("Av. Las Heras 2186");
    heladera.setPuntoEstrategico(puntoEstrategico);

    receptorMovimiento = new ReceptorMovimiento(heladera);

    PersonaHumana personaHumana2 = new PersonaHumana("Luis", "Gonzalez", new MedioDeContacto(TipoMedioDeContacto.MAIL, "luisgonzalez@gmail.com"));
    colaborador = new Colaborador(personaHumana2);

    strategyNotificacionMock = mock(StrategyNotificacion.class);
    Notificador.agregarEstrategia(TipoMedioDeContacto.MAIL, strategyNotificacionMock);

  }

  @Test
  public void testSuscripcionExitosa() {
    suscripcion = new SuscripcionXCantidad(heladera, colaborador, TipoSuscripcion.QUEDAN_POCAS, 5, TipoMedioDeContacto.MAIL);

    heladera.agregarSuscriptor(suscripcion);

    assertTrue(heladera.getColaboradoresSucriptos().contains(suscripcion));
  }

  @Test
  public void testNotificarmeAlertaQuedanPocas() {
    suscripcion = new SuscripcionXCantidad(heladera, colaborador, TipoSuscripcion.QUEDAN_POCAS, 5, TipoMedioDeContacto.MAIL);

    heladera.agregarSuscriptor(suscripcion);

    suscripcion.notificarmeAlerta();

    ArgumentCaptor<String> textoCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> asuntoCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<Colaborador> colaboradorCaptor = ArgumentCaptor.forClass(Colaborador.class);

    verify(strategyNotificacionMock).enviarNotificacion(textoCaptor.capture(), colaboradorCaptor.capture(), asuntoCaptor.capture());

    assertEquals("En la heladera ubicada en Av. Las Heras 2186 quedan pocas viandas!", textoCaptor.getValue());
    assertEquals("Quedan pocas viandas!", asuntoCaptor.getValue());
    assertEquals(colaborador, colaboradorCaptor.getValue());
  }

  @Test
  public void testNotificarmeAlertaPocoEspacio() {
    suscripcion = new SuscripcionXCantidad(heladera, colaborador, TipoSuscripcion.POCO_ESPACIO, 6, TipoMedioDeContacto.MAIL);

    heladera.agregarSuscriptor(suscripcion);

    suscripcion.notificarmeAlerta();

    ArgumentCaptor<String> textoCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> asuntoCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<Colaborador> colaboradorCaptor = ArgumentCaptor.forClass(Colaborador.class);

    verify(strategyNotificacionMock).enviarNotificacion(textoCaptor.capture(), colaboradorCaptor.capture(), asuntoCaptor.capture());

    assertEquals("La heladera en Av. Las Heras 2186 tiene poco espacio!", textoCaptor.getValue());
    assertEquals("Poco espacio en heladera", asuntoCaptor.getValue());
    assertEquals(colaborador, colaboradorCaptor.getValue());
  }
}
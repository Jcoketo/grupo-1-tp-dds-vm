package suscripciones;

import elementos.Heladera;
import elementos.PuntoEstrategico;
import elementos.ReceptorMovimiento;
import notificador.Notificador;
import notificador.StrategyNotificacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import suscripcion.ColaboradorSuscripto;
import java.time.LocalDate;
import personas.Colaborador;
import personas.MedioDeContacto;
import personas.PersonaHumana;
import personas.TipoMedioDeContacto;
import suscripcion.SuscriptoCantidad;
import suscripcion.TipoSuscripcion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SuscripcionesCantidadTest {

  private Heladera heladera;
  private ReceptorMovimiento receptorMovimiento;
  private Colaborador colaborador;
  private ColaboradorSuscripto colaboradorSuscripto;
  private StrategyNotificacion strategyNotificacionMock;
  private PuntoEstrategico puntoEstrategico;


  @BeforeEach
  public void setUp() {
    heladera = new Heladera(5, LocalDate.now(), null);
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
    colaboradorSuscripto = new SuscriptoCantidad(heladera, colaborador, TipoSuscripcion.QUEDAN_POCAS, 5, TipoMedioDeContacto.MAIL);

    heladera.agregarSuscriptor(colaboradorSuscripto);

    assertTrue(heladera.getColaboradoresSucriptos().contains(colaboradorSuscripto));
  }

  @Test
  public void testNotificarmeAlertaQuedanPocas() {
    colaboradorSuscripto = new SuscriptoCantidad(heladera, colaborador, TipoSuscripcion.QUEDAN_POCAS, 5, TipoMedioDeContacto.MAIL);

    heladera.agregarSuscriptor(colaboradorSuscripto);

    colaboradorSuscripto.notificarmeAlerta();

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
    colaboradorSuscripto = new SuscriptoCantidad(heladera, colaborador, TipoSuscripcion.POCO_ESPACIO, 6, TipoMedioDeContacto.MAIL);

    heladera.agregarSuscriptor(colaboradorSuscripto);

    colaboradorSuscripto.notificarmeAlerta();

    ArgumentCaptor<String> textoCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> asuntoCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<Colaborador> colaboradorCaptor = ArgumentCaptor.forClass(Colaborador.class);

    verify(strategyNotificacionMock).enviarNotificacion(textoCaptor.capture(), colaboradorCaptor.capture(), asuntoCaptor.capture());

    assertEquals("La heladera en Av. Las Heras 2186 tiene poco espacio!", textoCaptor.getValue());
    assertEquals("Poco espacio en heladera", asuntoCaptor.getValue());
    assertEquals(colaborador, colaboradorCaptor.getValue());
  }
}
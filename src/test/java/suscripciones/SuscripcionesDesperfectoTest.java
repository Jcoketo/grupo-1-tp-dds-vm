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
import suscripcion.SuscriptoFalla;
import suscripcion.TipoSuscripcion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


public class SuscripcionesDesperfectoTest {

  private Heladera heladera;
  private ReceptorMovimiento receptorMovimiento;
  private Colaborador colaborador;
  private ColaboradorSuscripto colaboradorSuscripto;
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

    colaboradorSuscripto = new SuscriptoFalla(heladera,colaborador, TipoSuscripcion.DESPERFECTO, TipoMedioDeContacto.MAIL);

    heladera.agregarSuscriptor(colaboradorSuscripto);

    strategyNotificacionMock = mock(StrategyNotificacion.class);
    Notificador.agregarEstrategia(TipoMedioDeContacto.MAIL, strategyNotificacionMock);


  }
  @Test
  public void testSuscripcionExitosa() {
    assertTrue(heladera.getColaboradoresSucriptos().contains(colaboradorSuscripto));
  }

  @Test
  public void testNotificarmeAlerta() {
    colaboradorSuscripto.notificarmeAlerta();

    ArgumentCaptor<String> textoCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> asuntoCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<Colaborador> colaboradorCaptor = ArgumentCaptor.forClass(Colaborador.class);

    verify(strategyNotificacionMock).enviarNotificacion(textoCaptor.capture(), colaboradorCaptor.capture(), asuntoCaptor.capture());

    assertEquals("Hubo un Desperfecto!", asuntoCaptor.getValue());
    assertEquals(colaborador, colaboradorCaptor.getValue());
  }

}
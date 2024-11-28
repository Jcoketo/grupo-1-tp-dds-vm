package elementos;

import modelo.colaboracion.DistribucionDeViandas;
import modelo.colaboracion.Vianda;
import modelo.elementos.Heladera;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import modelo.personas.Colaborador;
import modelo.personas.MedioDeContacto;
import modelo.personas.PersonaHumana;
import modelo.personas.TipoMedioDeContacto;
import modelo.personas.TipoPersona;
import modelo.colaboracion.MotivoDistribucion;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ColaboracionesTest {

  private DistribucionDeViandas distribucionDeViandasDesperfecto;
  private DistribucionDeViandas distribucionDeViandasFaltaViandas;
  private Colaborador colaborador;
  private Heladera heladeraOrigen;
  private Heladera heladeraDestino;
  private Heladera heladeraDestinoSinLugar;
  private PersonaHumana personaHumana;
  private PersonaHumana personaHumana2;

  @BeforeEach
  public void setUp() {
    heladeraOrigen = new Heladera(10, LocalDate.now(), null);
    heladeraDestino = new Heladera(10, LocalDate.now(), null);
    heladeraDestinoSinLugar = new Heladera(1, LocalDate.now(), null);

    personaHumana = new PersonaHumana("Juan", "Perez", new MedioDeContacto(TipoMedioDeContacto.MAIL, "juanperez@gmail.com"));
    colaborador = new Colaborador(personaHumana);

    distribucionDeViandasDesperfecto = new DistribucionDeViandas(TipoPersona.PH, 10, heladeraOrigen, heladeraDestino, MotivoDistribucion.DESPERFECTO, LocalDate.now());
    distribucionDeViandasFaltaViandas = new DistribucionDeViandas(TipoPersona.PH, 10, heladeraOrigen, heladeraDestino, MotivoDistribucion.FALTA_VIANDAS, LocalDate.now());
  }

  @Test
  public void testSolicitarAperturaHeladeraHabilitada() {
    heladeraOrigen.setHabilitado(true);

    distribucionDeViandasDesperfecto.solicitarAperturaHeladera(colaborador, heladeraDestino);

    //Verifica que la solicitud tenga una fecha de solicitud
    assertNotNull(distribucionDeViandasDesperfecto.getSolicitud().getFechaSolicitud());
  }

  @Test
  public void testPermitirIngresoHeladeraDestino(){
    heladeraOrigen.setHabilitado(true);
    heladeraDestino.setActiva(false);

    assertFalse(heladeraDestino.permitirIngreso());
}


  @Test
  public void testSolicitarAperturaHeladeraNoActiva() {
    heladeraDestino.setActiva(false);
    heladeraDestino.setHabilitado(false);

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    distribucionDeViandasDesperfecto.solicitarAperturaHeladera(colaborador, heladeraDestino);

    System.setOut(System.out);
    String expectedOutput = "La heladera no está activa\nLa heladera ya se encuentra inhabilitada\n";

    assertEquals(expectedOutput, outContent.toString());
  }


  @Test
  public void testHeladeraHabilitada(){
    heladeraOrigen.setHabilitado(true);
    distribucionDeViandasDesperfecto.solicitarAperturaHeladera(colaborador, heladeraDestino);

    assertEquals(true, heladeraOrigen.getHabilitado());
  }

  @Test
  public void testEfectuarAperturaConSolicitud() {
    heladeraOrigen.setHabilitado(true);

    distribucionDeViandasDesperfecto.solicitarAperturaHeladera(colaborador, heladeraDestino);

    distribucionDeViandasDesperfecto.efectuarApertura(colaborador);

    //Verifica que la solicitud tenga una fecha de apertura
    assertNotNull(distribucionDeViandasDesperfecto.getSolicitud().getAperturaFehaciente());
  }


  @Test
  public void testEfectuarAperturaSinSolicitud() {
    heladeraOrigen.setHabilitado(false);

    //Captura lo que devuelve el método
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    distribucionDeViandasDesperfecto.efectuarApertura(colaborador);

    assertTrue(outContent.toString().contains("No se ha solicitado la apertura de la heladera"));
  }

  @Test
  public void testTarjetaColaborador() {
    heladeraOrigen.setHabilitado(true);

    distribucionDeViandasDesperfecto.solicitarAperturaHeladera(colaborador, heladeraDestino);

    //Verifica que el colaborador tenga una tarjeta
    assertNotNull(colaborador.getTarjeta());
  }

  @Test
  public void testHeladeraDestinoSinLugar() {
    heladeraDestinoSinLugar.setHabilitado(true);
    heladeraDestinoSinLugar.setActiva(true);

    Colaborador colaborador2 = new Colaborador(personaHumana2);
    heladeraDestinoSinLugar.agregarVianda(new Vianda("Vianda1", LocalDate.now(), LocalDate.now().plusDays(5), colaborador2, heladeraDestinoSinLugar, true));

    IndexOutOfBoundsException thrown = assertThrows(
        IndexOutOfBoundsException.class,
        () -> heladeraDestinoSinLugar.agregarVianda(new Vianda("Vianda2", LocalDate.now(), LocalDate.now().plusDays(5), colaborador2, heladeraDestinoSinLugar, true)),
        "se espera que devuelva excepcion IndexOutOfBoundsException"
    );

    assertEquals("No se pueden agregar más viandas", thrown.getMessage());
  }

  @Test
  public void testHeladeraDestinoSinLugar2() {
    heladeraOrigen.setHabilitado(true);
    heladeraDestinoSinLugar.setHabilitado(true);
    heladeraDestinoSinLugar.setActiva(true);

    personaHumana2 = new PersonaHumana("Juan", "Perez", new MedioDeContacto(TipoMedioDeContacto.MAIL, "juanperez@gmail.com"));
    Colaborador colaborador2 = new Colaborador(personaHumana2);
    heladeraDestinoSinLugar.agregarVianda(new Vianda("Vianda1", LocalDate.now(), LocalDate.now().plusDays(5), colaborador2, heladeraDestinoSinLugar, true));

    distribucionDeViandasFaltaViandas.solicitarAperturaHeladera(colaborador2, heladeraDestinoSinLugar);

    distribucionDeViandasFaltaViandas.hacerColaboracion(colaborador2);
    //verifica que no se agrega la vianda a la heladera destino
    assertEquals(1, heladeraDestinoSinLugar.getViandas().size());
    //Verifica que se imprime la excepcion con el mensaje "No hay lugar en la heladera destino"
    assertThrows(IndexOutOfBoundsException.class, () -> heladeraDestinoSinLugar.agregarVianda(new Vianda("Vianda2", LocalDate.now(), LocalDate.now().plusDays(5), colaborador2, heladeraDestinoSinLugar, true)));
    }
}
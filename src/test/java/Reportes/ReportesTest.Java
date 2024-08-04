package reportador;

import elementos.Heladera;
import personas.Colaborador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import repositorios.RepositorioColaboradores;
import repositorios.RepositorioHeladeras;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GenerarReporteTest {

    private GenerarReporte generarReporte;

    @BeforeEach
    void setUp() {
        generarReporte = new GenerarReporte();
    }

    @Test
    void testObtenerFallasXHeladera() {
        Heladera heladera1 = mock(Heladera.class);
        Heladera heladera2 = mock(Heladera.class);
        
        when(RepositorioHeladeras.getHeladeras()).thenReturn(Arrays.asList(heladera1, heladera2));
        when(heladera1.getContadorFallasSemanal()).thenReturn(3);
        when(heladera2.getContadorFallasSemanal()).thenReturn(5);
        
        Map<Heladera, Integer> fallas = generarReporte.obtenerFallasXHeladera();

        assertEquals(3, fallas.get(heladera1));
        assertEquals(5, fallas.get(heladera2));
        verify(heladera1).resetearContador("fallas");
        verify(heladera2).resetearContador("fallas");
    }

    @Test
    void testObtenerViandasColocadasXHeladera() {
        Heladera heladera1 = mock(Heladera.class);
        Heladera heladera2 = mock(Heladera.class);

        when(RepositorioHeladeras.getHeladeras()).thenReturn(Arrays.asList(heladera1, heladera2));
        when(heladera1.getContadorViandasColocadas()).thenReturn(10);
        when(heladera2.getContadorViandasColocadas()).thenReturn(15);

        Map<Heladera, Integer> viandasColocadas = generarReporte.obtenerViandasColocadasXHeladera();

        assertEquals(10, viandasColocadas.get(heladera1));
        assertEquals(15, viandasColocadas.get(heladera2));
        verify(heladera1).resetearContador("colocadas");
        verify(heladera2).resetearContador("colocadas");
    }

    @Test
    void testObtenerViandasRetiradasXHeladera() {
        Heladera heladera1 = mock(Heladera.class);
        Heladera heladera2 = mock(Heladera.class);

        when(RepositorioHeladeras.getHeladeras()).thenReturn(Arrays.asList(heladera1, heladera2));
        when(heladera1.getContadorViandasRetiradas()).thenReturn(7);
        when(heladera2.getContadorViandasRetiradas()).thenReturn(12);

        Map<Heladera, Integer> viandasRetiradas = generarReporte.obtenerViandasRetiradasXHeladera();

        assertEquals(7, viandasRetiradas.get(heladera1));
        assertEquals(12, viandasRetiradas.get(heladera2));
        verify(heladera1).resetearContador("retiradas");
        verify(heladera2).resetearContador("retiradas");
    }

    @Test
    void testObtenerViandasXColaborador() {
        Colaborador colaborador1 = mock(Colaborador.class);
        Colaborador colaborador2 = mock(Colaborador.class);

        when(RepositorioColaboradores.getColaboradores()).thenReturn(Arrays.asList(colaborador1, colaborador2));
        when(colaborador1.getContadorViandasDonadasSemanal()).thenReturn(20);
        when(colaborador2.getContadorViandasDonadasSemanal()).thenReturn(30);

        Map<Colaborador, Integer> viandasXColaborador = generarReporte.obtenerViandasXColaborador();

        assertEquals(20, viandasXColaborador.get(colaborador1));
        assertEquals(30, viandasXColaborador.get(colaborador2));
        verify(colaborador1).resetearContadorViadasSemanales();
        verify(colaborador2).resetearContadorViadasSemanales();
    }
}
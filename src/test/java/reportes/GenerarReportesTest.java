package reportes;

import modelo.elementos.Heladera;
import modelo.personas.Colaborador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import modelo.reportador.GenerarReporte;
import modelo.reportador.ReporteHeladera;
import modelo.reportador.ReporteColaborador;
import persistencia.RepositorioColaboradores;
import persistencia.RepositorioHeladeras;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GenerarReportesTest {

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

        List<ReporteHeladera> fallas = generarReporte.obtenerFallasXHeladera();

        assertEquals(3, fallas.get(0).getCantidad());
        assertEquals(heladera1, fallas.get(0).getHeladera());

        assertEquals(5, fallas.get(1).getCantidad());
        assertEquals(heladera2, fallas.get(1).getHeladera());

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

        List<ReporteHeladera> viandasColocadas = generarReporte.obtenerViandasColocadasXHeladera();

        assertEquals(10, viandasColocadas.get(0).getCantidad());
        assertEquals(heladera1, viandasColocadas.get(0).getHeladera());

        assertEquals(15, viandasColocadas.get(1).getCantidad());
        assertEquals(heladera2, viandasColocadas.get(1).getHeladera());

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

        List<ReporteHeladera> viandasRetiradas = generarReporte.obtenerViandasRetiradasXHeladera();

        assertEquals(7, viandasRetiradas.get(0).getCantidad());
        assertEquals(heladera1, viandasRetiradas.get(0).getHeladera());

        assertEquals(12, viandasRetiradas.get(1).getCantidad());
        assertEquals(heladera2, viandasRetiradas.get(1).getHeladera());

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

        List<ReporteColaborador> viandasXColaborador = generarReporte.obtenerViandasXColaborador();

        assertEquals(20, viandasXColaborador.get(0).getCantidadViandas());
        assertEquals(colaborador1, viandasXColaborador.get(0).getColaborador());

        assertEquals(30, viandasXColaborador.get(1).getCantidadViandas());
        assertEquals(colaborador2, viandasXColaborador.get(1).getColaborador());

        verify(colaborador1).resetearContadorViandasSemanales();
        verify(colaborador2).resetearContadorViandasSemanales();
    }
}

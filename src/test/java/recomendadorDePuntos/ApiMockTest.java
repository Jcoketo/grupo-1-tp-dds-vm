package recomendadorDePuntos;


import modelo.elementos.PuntoEstrategico;
import modelo.consumosAPIs.recomendadorDePuntos.RecomendadorDePuntos;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import java.util.List;


public class ApiMockTest {

    @Test
    @DisplayName("Prueba Mockeo API.")
    public void solicitarPuntosEstatrategicosTest() {

        RecomendadorDePuntos recomendadorDePuntos = RecomendadorDePuntos.getInstancia();

        List<PuntoEstrategico> puntosEstrategicos = recomendadorDePuntos.obtenerPuntosRecomendados(-34.603722, -58.381592, 1000.0);

        Assertions.assertEquals(34.0522, puntosEstrategicos.get(0).getLatitud());
        Assertions.assertEquals(118.2437, puntosEstrategicos.get(0).getLongitud());
    }

}

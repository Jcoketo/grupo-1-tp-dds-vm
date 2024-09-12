package Elementos;

import elementos.Heladera;
import colaboracion.Vianda;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class HeladeraTest {

    @Test
    public void testRetirarVianda() {
        // Arrange
        Heladera heladera = new Heladera(10, LocalDate.now(), null);
        Heladera heladera2 = new Heladera(10, LocalDate.now(), null);
        Vianda vianda1 = new Vianda("Vianda1", LocalDate.now(), LocalDate.now(), null, heladera, false);
        Vianda vianda2 = new Vianda("Vianda2", LocalDate.now(), LocalDate.now(), null, heladera, false);

        heladera.agregarVianda(vianda1);
        heladera.agregarVianda(vianda2);

        Vianda retirada = heladera.retirarVianda(1); //elimina vianda2

        heladera2.agregarVianda(retirada);

        assertEquals(vianda2, retirada);
        assertEquals(1, heladera.getViandas().size());
        assertTrue(heladera.getViandas().contains(vianda1));

        assertEquals(1, heladera2.getViandas().size());

    }
}
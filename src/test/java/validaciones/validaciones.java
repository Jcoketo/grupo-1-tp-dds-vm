package validaciones;

import validador.ValidadorDeContrasenias;
import validador.SanitizadorDeContrasenias;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;

public class validaciones {
    private ValidadorDeContrasenias validador;

    /*
    @BeforeEach
    public void init() {
        this.validador = new Validador();
    }*/

    @Test
    @DisplayName("Prueba del Sanitizador.")
    public void borrarEspaciosTest() {
        String contrasenia = "hola    amigo";
        contrasenia = SanitizadorDeContrasenias.eliminarMultiplesEspacios(contrasenia);
        Assertions.assertEquals("hola amigo", contrasenia);
    }

    @Test
    @DisplayName("Prueba de la Condicion de Longitud.")
    public void contraseniaCortaTest() {
        this.validador = new ValidadorDeContrasenias();
        Assertions.assertEquals(
                false, validador.validarContrasenia("Juan", "aaaaaaa"));
    }

    @Test
    @DisplayName("Prueba de la Condicion de Credencial.")
    public void contraseniaSimilarAlNombreTest() {
        this.validador = new ValidadorDeContrasenias();
        Assertions.assertEquals(
                false, validador.validarContrasenia("Juan", "Juan1234"));
    }

    @Test
    @DisplayName("Prueba de la Condicion de Top10000.")
    public void contraseniaEnElTop10000Test() {
        this.validador = new ValidadorDeContrasenias();
        Assertions.assertEquals(
                false, validador.validarContrasenia("Juan", "qwerty"));
    }

    @Test
    @DisplayName("Prueba de una contrasenia valida.")
    public void contraseniaValidaTest() {
        this.validador = new ValidadorDeContrasenias();
        Assertions.assertEquals(
                true, validador.validarContrasenia("Juan", "parapente"));
    }

    @Test
    @DisplayName("Prueba de una contrasenia 2.")
    public void contraseniaValida() {
        this.validador = new ValidadorDeContrasenias();
        Assertions.assertEquals(
                false, validador.validarContrasenia("1", "1234"));
    }
}

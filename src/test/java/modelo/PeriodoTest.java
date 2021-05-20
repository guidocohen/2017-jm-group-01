package modelo;

import excepciones.PeriodoErroneoException;
import java.time.format.DateTimeParseException;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

public class PeriodoTest {

    private static Periodo periodoFebreroMarzo;
    private static Periodo periodoEneroJunio;
    private static Periodo periodoFebreroJulio;

    @BeforeClass
    public static void setUpClass() {
        periodoFebreroMarzo = new Periodo("01/02/2017", "31/03/2017");
        periodoEneroJunio = new Periodo("01/01/2017", "30/06/2017");
        periodoFebreroJulio = new Periodo("01/02/2017", "31/07/2017");
    }

    @Test(expected = DateTimeParseException.class) //TODO: Excepcion custom vale la pena?
    public void testPeriodoFechaCualquieraArrojaExcepcion() {
        new Periodo("trece de marzo de este año", "el dia que le sigue");
    }

    @Test(expected = PeriodoErroneoException.class)
    public void testPeriodoInvertidoArrojaExcepcion() {
        new Periodo("12/05/2017", "08/04/2017");
    }

    @Test
    public void testPeriodoIncluido() {
        assertTrue("Febrezo-Marzo deberia estar incluido en Enero-Junio",
                periodoFebreroMarzo.estaIncluidoEn(periodoEneroJunio));
    }

    @Test
    public void testPeriodoIncluidoConSolapamientoDeLimite() {
        assertTrue("Febrezo-Marzo deberia estar incluido en Febrero-Julio",
                periodoFebreroMarzo.estaIncluidoEn(periodoFebreroJulio));
    }

    @Test
    public void testPeriodoNoIncluido() {
        assertFalse("Enero-Junio no deberia estar incluido en Febrero-Julio",
                periodoEneroJunio.estaIncluidoEn(periodoFebreroJulio));
    }

}

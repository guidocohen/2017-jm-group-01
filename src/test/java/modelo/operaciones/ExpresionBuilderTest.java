package modelo.operaciones;

import excepciones.InterpretacionDeExpresionException;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

/**
 *
 * @author David
 */
public class ExpresionBuilderTest {

    private ExpresionBuilder sut;

    @Before
    public void setUp() {
        sut = new ExpresionBuilder();
    }

    @Test
    public void testExpresionSimple() {
        Calculable expresion = sut
                .numero(2)
                .suma()
                .numero(3)
                .suma()
                .numero(4)
                .suma()
                .numero(5)
                .build();
        assertThat(expresion.toString(), is("2.0+3.0+4.0+5.0"));
    }

    @Test
    public void testExpresionConVariables() {
        Calculable expresion = sut
                .identificador("INO_CONTINUA")
                .multiplicacion()
                .numero(7)
                .build();
        assertThat(expresion.toString(), is("INO_CONTINUA*7.0"));
    }

    @Test
    public void testExpresionConParentesis() {
        Calculable expresion = sut
                .identificador("ROE")
                .suma()
                .parentesisAbre()
                .numero(2)
                .suma()
                .identificador("EBITDA")
                .parentesisCierra()
                .multiplicacion()
                .numero(5)
                .build();
        assertThat(expresion.toString(), is("ROE+(2.0+EBITDA)*5.0"));
    }

    @Test
    public void testConstanteEntreParentesis() {
        Calculable expresion = sut
                .parentesisAbre()
                .numero(7)
                .parentesisCierra()
                .build();
        assertThat(expresion.toString(), is("(7.0)"));
    }

    @Test(expected = InterpretacionDeExpresionException.class)
    public void testParentesisMalCerrados() {
        Calculable expresion = sut
                .numero(5)
                .parentesisAbre()
                .numero(7)
                .build();
    }

    @Test(expected = InterpretacionDeExpresionException.class)
    public void testParentesisVaciosFalla() {
        Calculable expresion = sut
                .parentesisAbre()
                .parentesisCierra()
                .build();
    }

    @Test(expected = InterpretacionDeExpresionException.class)
    public void testOperadorSoloFalla() {
        sut.suma().build();
    }

    @Test(expected = InterpretacionDeExpresionException.class)
    public void testDosValoresSinOperadorFalla() {
        sut.identificador("ROE").numero(12).build();
    }

}

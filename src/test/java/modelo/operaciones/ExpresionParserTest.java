package modelo.operaciones;

import excepciones.InterpretacionDeExpresionException;
import fixture.TestFixture;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

/**
 *
 * @author David
 */
public class ExpresionParserTest {

    private ExpresionParser sut;

    @Before
    public void setUp() {
        sut = new ExpresionParser();
    }

    @Test
    public void testTripleEbitdaDeGoogle() {
        String expresion = "EBITDA * 3";
        Double esperado = 30418 * 3.0;

        Calculable calculo = sut.interpretar(expresion);
        Double resultado = calculo.getValor(TestFixture.google(), TestFixture.anio(2016));

        assertThat(resultado, is(esperado));
    }

    @Test
    public void testExpresionCuentasComplejaApple() {
        String expresion = "(EBITDA /INO_CONTINUAS + FREE_CASH_FLOW) * 0.1";
        Double esperado = (84505 / 21120 + 53394) * 0.1;

        Calculable calculo = sut.interpretar(expresion);
        Double resultado = calculo.getValor(TestFixture.apple(), TestFixture.anio(2015));

        assertThat(resultado, is(closeTo(esperado, 0.01)));
    }

    @Test
    public void testExpresionCuentasEIndicadoresFacebook() {
        String expresion = "(EBITDA /INO_CONTINUAS + FREE_CASH_FLOW) * 0.1";
        Double esperado = (84505 / 21120 + 53394) * 0.1;

        Calculable calculo = sut.interpretar(expresion);
        Double resultado = calculo.getValor(TestFixture.apple(), TestFixture.anio(2015));

        assertThat(resultado, is(closeTo(esperado, 0.01)));
    }

    @Test(expected = InterpretacionDeExpresionException.class)
    public void testParentesisMalPuestoFalla() {
        String expresion = "JUAN (";

        sut.interpretar(expresion);
    }

    @Test(expected = InterpretacionDeExpresionException.class)
    public void testSignoDeAdmiracionFalla() {
        String expresion = "!!true";

        sut.interpretar(expresion);
    }

    @Test(expected = InterpretacionDeExpresionException.class)
    public void testAndpersandFalla() {
        String expresion = "EBITDA && ROE";

        sut.interpretar(expresion);
    }

    @Test(expected = InterpretacionDeExpresionException.class)
    public void testSimboloNumeralFalla() {
        String expresion = "#INO_CONTINUAS + 7";

        sut.interpretar(expresion);
    }

}

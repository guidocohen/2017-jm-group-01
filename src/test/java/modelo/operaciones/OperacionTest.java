package modelo.operaciones;

import static org.junit.Assert.*;

import org.junit.Test;

public class OperacionTest {

	@Test
	public void seRealizaCorrectamenteLaOperacionSuma() {
		assertEquals(9, Operacion.SUMA.calcular(4, 5), 0);
	}

	@Test
	public void seRealizaCorrectamenteLaOperacionResta() {
		assertEquals(8, Operacion.RESTA.calcular(32, 24), 0);
	}

	@Test
	public void seRealizaCorrectamenteLaOperacionMultiplicacion() {
		assertEquals(54, Operacion.MULTIPLICACION.calcular(9, 6), 0);
	}

	@Test
	public void seRealizaCorrectamenteLaOperacionDivision() {
		assertEquals(7, Operacion.DIVISION.calcular(56, 8), 0);
	}

	@Test(expected = ArithmeticException.class)
	public void seLanzaUnaExcepcionAlIntentarDividirPorCero() {
		Operacion.DIVISION.calcular(12, 0);
	}

}

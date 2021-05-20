
import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import excepciones.CantDeAniosInvalidaException;
import excepciones.FiltrarPorPeriodoException;
import excepciones.PeriodoErroneoException;
import modelo.Periodo;

public class PeriodoTestConFixture extends Fixture {

	@Test
	public void testEstaDentroDelPeriodo() {
		assertTrue(anio2015.estaIncluidoEn(periodoValidoLargo));
	}

	@Test
	public void testNoEstaDentroDelPeriodo() {
		assertFalse(anio2015.estaIncluidoEn(periodoValidoCorto));
	}

	@Test(expected = PeriodoErroneoException.class)
	public void testPeriodoInvalidoYSeProduceLaExcepcion() throws PeriodoErroneoException {
		periodoInvalido1 = new Periodo("26/11/2017", "13/12/2016");
	}

	@Test
	public void testCuentaEsDeUnPeriodo() {
		assertTrue(cuentaValida2.esDe(periodoValidoLargo));
	}

	@Test
	public void testCuentaNoEsDeUnPeriodo() {
		assertFalse(cuentaValida2.esDe(periodoValidoCorto));
	}

	@Test(expected = CantDeAniosInvalidaException.class)
	public void testCrearUnPeriodoDeUltimos0aniosYSeProduceLaExcepcion() {
		anio2014.ultimosNAnios(0);
	}

	@Test
	public void testCrearPeriodoDeLosUltimosNAnios() {
		Periodo periodo = new Periodo(2);

		assertEquals(periodo.getDesdeFecha(), LocalDate.of(2015, 01, 01));
		assertEquals(periodo.getHastaFecha(), LocalDate.of(2016, 12, 31));
	}

	@Test
	public void testDesplazar1AnioMasReciente() {
		Periodo anio2015Mas1 = anio2015.desplazar1AnioMasReciente();
		assertEquals(anio2015Mas1.toString(), anio2016.toString());
	}


	@Test(expected = FiltrarPorPeriodoException.class)
	public void testFiltrarPorPeriodoException1() {
		cuentaValida1.setTipo("EBITDA");
		cuentaValida2.setPeriodo(anio2015);
		cuentaValida2.setTipo("EBITDA");
		cuentaValida2.setPeriodo(anio2015);
		cuentasVacia.add(cuentaValida1);
		cuentasVacia.add(cuentaValida2);
		facebook.setCuentas(cuentasVacia);
		facebook.obtenerResultadoDeCuenta("EBITDA", anio2015);
	}

	@Test
	public void testComprendeTodoElPeriodo() {
		assertTrue(anio2015.comprendeTodoElPeriodo());
	}

	@Test
	public void testCantidadDeAnios() {
		assertEquals(4, periodoValidoLargo.cantDeAnios());
	}
	
	@Test
	public void testEsPosteriorA() {
		assertTrue(anio2015.esPosteriorA(anio2014));
	}

}

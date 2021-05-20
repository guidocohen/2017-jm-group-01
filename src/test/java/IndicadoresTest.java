import static org.junit.Assert.*;

import org.junit.Test;

import excepciones.FiltrarPorNombreException;
import modelo.indicadores.Indicador;
import modelo.indicadores.IndicadorUsuario;
import modelo.repositorios.RepoDeIndicadores;
import modelo.repositorios.archivos.RepoDeIndicadoresArchivo;

public class IndicadoresTest extends Fixture {
	private Double resultado;

	@Test
	public void testElRepoContieneLosIndicadoresPorDefecto() {
		RepoDeIndicadores repo = RepoDeIndicadoresArchivo.getInstancia();
		repo.setIndicadoresPredefinidos();
		equals(repoDeIndicadores.getIndicadores().equals(repo));
	}

	// El primer parametro del assertEquals tiene que ser el Valor Esperado
	@Test
	public void testSePuedenAgregarIndicadoresGeneradosPorElUsuario() {
		assertEquals("EBITDA+3.0", repoDeIndicadores.obtenerIndicador("Rie").getFormula());
	}

	//Una vez que exista un metodo que resuelva el resultado de la formula se puede hacer este test para ver si el
	//valor de la cuenta da igual a la aplicacion de la misma en la formula
	
	@Test
	public void testCalculoValorTotalDeUnaFormulaPersonalizada() {
		String formula = repoDeIndicadores.obtenerIndicador("RIE").getFormula();
		Indicador indicador = new IndicadorUsuario("Indicador RIE",formula);
		resultado = indicador.calcularPara(apple, anio2015);

		assertEquals(resultado, apple.obtenerResultadoDeCuenta("EBITDA", anio2015) + 3, 0);
	}

	@Test
	public void testIndicadorCapitalTotal() {
		resultado = indicadorCapitalTotal.calcularPara(facebook, anio2015);
		assertEquals(5.0, resultado, 0);
	}

	@Test
	public void testIndicadorDividendos() {
		resultado = indicadorDividendos.calcularPara(facebook, anio2016);
		assertEquals(10.0, resultado, 0);
	}

	@Test
	public void testIndicadorIngresoNeto() {
		assertEquals(3672.0, indicadorIngresoNeto.calcularPara(intel, anio2016), 0);
	}

	@Test
	public void testIndicadorRoe() {
		resultado = indicadorRoe.calcularPara(google, anio2016);
		assertEquals(714.0, resultado, 0);
	}


	@Test(expected=FiltrarPorNombreException.class)
	public void testIndicadorIngresoNetoDevuelveExcepcion() {
		indicadorIngresoNeto.calcularPara(intel, anio2015);
	}

}
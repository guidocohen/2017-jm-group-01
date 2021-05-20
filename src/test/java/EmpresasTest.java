import static org.junit.Assert.*;

import org.junit.Test;

import excepciones.FiltrarPorNombreException;


public class EmpresasTest extends Fixture {

	@Test
	public void testSeRealizaCorrectamenteElFiltradoDeEmpresasPorNombre() throws FiltrarPorNombreException {
		assertEquals(facebook.getNombre(), repoDeEmpresas.obtenerEmpresa("Facebook").getNombre());
	}

	@Test(expected = FiltrarPorNombreException.class)
	public void testNoExistenEmpresasConUnNombreDadoYSeProduceLaExcepcion() throws FiltrarPorNombreException {
		repoDeEmpresas.obtenerEmpresa("Amazon");
	}

	@Test
	public void testSeRealizaCorrectamenteElFiltradoDeCuentasPorPeriodo() {
		assertEquals(facebook.getCuentas(), facebook.filtrarCuentasPorPeriodo(periodoValidoLargo));
	}

	@Test
	public void testFiltradoDeCuentasPorPeriodoDevuelveListaVacia() {
		assertEquals(cuentasVacia, facebook.filtrarCuentasPorPeriodo(periodoValidoCorto));
	}

}

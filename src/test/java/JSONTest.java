
import carga.CargadorEmpresasBuilder;
import carga.CargadorIndicadores;
import carga.CargadorIndicadoresBuilder;

import java.io.IOException;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import excepciones.ArchivoNoEncontradoException;
import excepciones.FiltrarPorNombreException;
import excepciones.FormatoNoSoportadoException;
import excepciones.JsonException;
import java.io.File;

import modelo.indicadores.Indicador;
import carga.CargadorEmpresas;
import modelo.repositorios.archivos.RepoDeIndicadoresArchivo;

public class JSONTest extends Fixture {
	private CargadorIndicadores cargadorDeIndicadores;
	private CargadorEmpresas cargadorDeEmpresas;
	
	@Before
	public void setUp() throws IOException, ArchivoNoEncontradoException, JsonException {
		cargadorDeIndicadores = new CargadorIndicadoresBuilder()
				.conArchivo(new File(directorioArchivos + "indicadoresTest" + ".json"))
				.paraFormatoAdecuado();
		cargadorDeEmpresas = new CargadorEmpresasBuilder()
				.conArchivo(new File(directorioArchivos + "empresas.json"))
				.paraFormatoAdecuado();
	}

	@Test
	public void testObtenerFacebookJSON() throws IOException {
		repoDeEmpresas.setEmpresas(cargadorDeEmpresas.cargar());
		facebook = repoDeEmpresas.getEmpresas().get(0);
		assertEquals("Facebook", facebook.getNombre());
	}
	
	@Test
	public void testObtenerGoogleJSON() throws IOException {
		repoDeEmpresas.setEmpresas(cargadorDeEmpresas.cargar());
		google = repoDeEmpresas.getEmpresas().get(1);
		assertEquals("Google", google.getNombre());
	}

	@Test
	public void testEscribirEmpresasJSON() throws IOException {
		try{
			CargadorEmpresas cargador = new CargadorEmpresasBuilder()
					.conArchivo(new File(directorioArchivos+"writeTest.json"))
					.paraFormatoAdecuado();
			cargador.guardar(empresas);
		} catch(Exception e){
			fail("No se espera que la operacion correcta arroje excepcion");
		}
	}

	@Test
	public void testEscribirEmpresasXML() {
		try{
			CargadorEmpresas cargador = new CargadorEmpresasBuilder()
					.conArchivo( new File(directorioArchivos+"writeTest.xml"))
					.paraFormatoAdecuado();
			cargador.guardar(empresas);
		} catch(Exception e){
			fail("No se espera que la operacion correcta arroje excepcion");
		}
	}

	@Test
	public void testEscribirIndicadoresJSON() throws IOException {
		try {
			cargadorDeIndicadores.guardar(RepoDeIndicadoresArchivo.getInstancia().getIndicadores());
		} catch (Exception e) {
			fail("No se espera que la operacion correcta arroje excepcion");
		}
	}
	
	@Test
	public void testObtenerIndicadoresJSON_Y_Validar() throws IOException, FiltrarPorNombreException {
		repoDeIndicadores.cargarIndicadoresDefinidos(cargadorDeIndicadores);
		Indicador indicador = repoDeIndicadores.getIndicadoresDefinidos().get("RIE");
		assertEquals(indicador.getNombre(), "RIE"); 
		assertEquals(indicador.getFormula(), "EBITDA+3.0");
	}
	
	@Test (expected=JsonException.class)
	public void testObtenerEmpresasMalFormadasYDevolverExcepcion() throws FiltrarPorNombreException, IOException{
		CargadorEmpresas cargador = new CargadorEmpresasBuilder()
				.conArchivo(new File(directorioArchivos+"empresasMalFormadas.json"))
				.paraFormatoAdecuado();
		cargador.cargar();
	}
	
	@Test (expected=JsonException.class)
	public void testObtenerIndicadoresMalFormadosYDevolverExcepcion() throws FiltrarPorNombreException, IOException{
		cargadorDeIndicadores = new CargadorIndicadoresBuilder()
				.conArchivo(new File(directorioArchivos + "indicadoresMalFormados" + ".json"))
				.paraFormatoAdecuado();
		repoDeIndicadores.cargarIndicadoresDefinidos(cargadorDeIndicadores);
	}

	@Test
	public void testObtenerIndicadoresJSON_Y_Calcular() throws IOException {
		repoDeIndicadores.cargarIndicadoresDefinidos(cargadorDeIndicadores);
		Indicador indicador = repoDeIndicadores.getIndicadoresDefinidos().get("RIE");
		Double resultado = indicador.calcularPara(google, anio2015);
		Assert.assertEquals(24821.0,resultado,0);
	}
	

	@Test(expected = FormatoNoSoportadoException.class)
	public void testFormatoNoSoportadoException() throws IOException {
		cargadorDeIndicadores = new CargadorIndicadoresBuilder()
				.conArchivo(new File("./src/test/resources/indicadoresTest.patito"))
				.paraFormatoAdecuado();
	}
	
	@After
	public void clean() {
		repoDeEmpresas = null;
		repoDeIndicadores = null;
	}

}

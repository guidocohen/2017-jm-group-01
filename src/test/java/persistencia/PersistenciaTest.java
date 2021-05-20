package persistencia;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import carga.CargadorEmpresas;
import carga.CargadorEmpresasBuilder;
import excepciones.FiltrarPorNombreException;
import modelo.Empresa;
import modelo.repositorios.persistencia.RepoDeEmpresasPersistencia;

public class PersistenciaTest {
	protected List <Empresa> empresas;
	protected RepoDeEmpresasPersistencia repoDeEmpresas;
	protected String pathEmpresas;
	protected CargadorEmpresas cargador;
	protected Empresa Google;
	
	
	@Before
	public void initialize() throws FiltrarPorNombreException, IOException{
		empresas = new ArrayList<Empresa>();
		pathEmpresas = "./src/test/resources/empresas.json";
		cargador = new CargadorEmpresasBuilder()
					.conArchivo(new File(pathEmpresas))
					.paraFormatoAdecuado();
		repoDeEmpresas = RepoDeEmpresasPersistencia.getInstancia();
                repoDeEmpresas.iniciar();
		repoDeEmpresas.cargarEmpresas(cargador);
                repoDeEmpresas.confirmar();
		Google = new Empresa("Google");
	}
	
	@After
	public void after() {
                repoDeEmpresas.iniciar();
		repoDeEmpresas.eliminarTodasLasEmpresas();
                repoDeEmpresas.confirmar();
	}
	
	@Test
	public void testSePersistenCorrectamenteLasEmpresas() {
		empresas = repoDeEmpresas.getEmpresas();
		assertEquals(4, empresas.size());
	}
	
	
	@Test
	public void testSeBuscaUnaEmpresaYSeLaEncuetraCorrectamente() {
		Empresa empresaEncontrada = repoDeEmpresas.obtenerEmpresa("Google");
		assertTrue(Google.getNombre().equals(empresaEncontrada.getNombre()));
	}

	@Test
	public void testActualizarEmpresa() {
		repoDeEmpresas.actualizarEmpresa(new Empresa("Facebook"), 1);
		assertEquals(4, repoDeEmpresas.getEmpresas().size());
	}
	
	@Test
	public void testEliminarEmpresaEncontrada() {
		Empresa empresaEncontrada = repoDeEmpresas.obtenerEmpresa("Google");
                repoDeEmpresas.iniciar();
		repoDeEmpresas.eliminarEmpresa(empresaEncontrada);
                repoDeEmpresas.confirmar();
		assertEquals(3, repoDeEmpresas.getEmpresas().size());
	}

	
	@Test
	public void seEliminanTodasLasEmpresasPersistidas() {
                repoDeEmpresas.iniciar();
		repoDeEmpresas.eliminarTodasLasEmpresas();
                repoDeEmpresas.confirmar();
		assertEquals(0, repoDeEmpresas.getEmpresas().size());
	}

}

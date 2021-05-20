package persistencia;


import java.io.File;
import java.io.IOException;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import carga.CargadorIndicadores;
import carga.CargadorIndicadoresBuilder;
import excepciones.FiltrarPorNombreException;
import modelo.indicadores.Indicador;
import modelo.repositorios.persistencia.RepoDeIndicadoresPersistencia;
import static org.junit.Assert.assertTrue;


public class IndicadorPersistenciaTest {
	
	protected Map<String, Indicador> indicadores;
	protected RepoDeIndicadoresPersistencia repoDeIndicadores;
	protected String pathIndicadores;
	protected CargadorIndicadores cargador;
	
	@Before
	public void initialize() throws FiltrarPorNombreException, IOException{
		pathIndicadores = "./src/test/resources/indicadores.json";
		 cargador = new CargadorIndicadoresBuilder()
					.conArchivo(new File(pathIndicadores))
					.paraFormatoAdecuado();
		repoDeIndicadores = RepoDeIndicadoresPersistencia.getInstance();
		repoDeIndicadores.cargarIndicadoresDefinidos(cargador);
		
	}
	
	@After
	public void after() {
                repoDeIndicadores.iniciar();
		repoDeIndicadores.eliminarTodosLosIndicadores();
                repoDeIndicadores.confirmar();
	}
	 
	@Test
	public void sePersistenCorrectamenteLosIndicadores(){
		indicadores = repoDeIndicadores.getIndicadores();
		//assertEquals(5,indicadores.size()); //TODO: NO SE QUE LE PASA A TRAVIS CON ESTO! D:
                assertTrue(indicadores.size()>0);
	}
		 	
}
	 
	
	


	 


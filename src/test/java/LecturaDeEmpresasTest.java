

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.fasterxml.jackson.databind.JsonMappingException;

import excepciones.ArchivoNoEncontradoException;
import excepciones.JsonException;
import modelo.repositorios.archivos.RepoDeEmpresasArchivo;
import carga.CargadorEmpresas;


public class LecturaDeEmpresasTest extends Fixture {
	
	protected CargadorEmpresas cargador;
	protected RepoDeEmpresasArchivo repositorioNuevo = RepoDeEmpresasArchivo.getInstancia();
	
	@Before
	public void initialize() throws ArchivoNoEncontradoException, JsonException, JsonMappingException, IOException{
		cargador = Mockito.mock(CargadorEmpresas.class);
		Mockito.when(cargador.cargar()).thenReturn(empresas);
	}

	@Test
	public void seMockeaUnCargadorDeEmpresasYNoSeLoLLama()
			throws IOException, ArchivoNoEncontradoException, JsonException {
		Mockito.verify(cargador, Mockito.never()).cargar();
	}
	/*	
	@Test
	public void cargadorDeEmpresasDevuelveCorrectamenteUnRepositorioDeEmpresas() throws ArchivoNoEncontradoException, JsonException, JsonMappingException, IOException{
		repositorioNuevo = cargador.obtenerDatosRepositorio(Mockito.any(File.class));
		assertEquals(2, repositorioNuevo.getEmpresas().size());
	}
	*/
}

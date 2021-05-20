package persistencia;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import excepciones.FiltrarPorNombreException;

import modelo.metodologias.Metodologia;
import modelo.repositorios.persistencia.RepoDeMetodologiasPersistencia;

public class MetodologiaPersistenciaTest implements WithGlobalEntityManager {
	protected RepoDeMetodologiasPersistencia repoDeMetodologias;
	protected ArrayList<Metodologia> metodologias;
	protected Metodologia miMetodologia;

	@Before
	public void initialize() throws FiltrarPorNombreException, IOException{
		miMetodologia= new Metodologia("MethodX");
		repoDeMetodologias=new RepoDeMetodologiasPersistencia();
		}
	
	@After
	public void after(){
                repoDeMetodologias.iniciar();
		repoDeMetodologias.eliminarMetodologias();
                repoDeMetodologias.confirmar();
	}

	@Test
	public void sePersistenCorrectamenteLasEmpresas() {
                repoDeMetodologias.iniciar();
		repoDeMetodologias.agregar(miMetodologia);
                repoDeMetodologias.confirmar();
		assertEquals(1, repoDeMetodologias.getMetodologias().size());
	}
	
	@Test
	public void testSeEliminanLasMetodologiasPersistidas() {
                repoDeMetodologias.iniciar();
		repoDeMetodologias.eliminarMetodologias();
                repoDeMetodologias.confirmar();
		assertEquals(0, repoDeMetodologias.getMetodologias().size());
	}
	
	@Test
	public void testSeBuscaUnaMetodologiaPorNombreYSeLaEncuentra() {
		entityManager().getTransaction().begin();
		repoDeMetodologias.agregar(miMetodologia);
		entityManager().getTransaction().commit();
		Metodologia metodologia = repoDeMetodologias.getMetodologiaPorNombre("MethodX");
		assertTrue(metodologia.getNombre().equals(miMetodologia.getNombre())); 
	}

}
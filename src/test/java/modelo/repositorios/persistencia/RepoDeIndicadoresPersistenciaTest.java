package modelo.repositorios.persistencia;

import fixture.TestFixture;
import modelo.Empresa;
import modelo.Periodo;
import modelo.indicadores.Indicador;
import modelo.indicadores.IndicadorUsuario;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;

/**
 *
 * @author David
 */
public class RepoDeIndicadoresPersistenciaTest {
    
    private RepoDeIndicadoresPersistencia sut = RepoDeIndicadoresPersistencia.getInstance();
    
    @Before
    public void setUp(){
        sut.iniciar();
        sut.eliminarTodosLosIndicadores();
        sut.confirmar();
    }
    
   
/*    @Test
    public void testObtenerIndicadoresPredefinidos(){
		entityManager().getTransaction().begin();
    	sut.setIndicadoresPredefinidos();
		entityManager().getTransaction().commit();
    }
*/
    
    @Test
    public void testPersistirIndicador() {
        Indicador indicador = new IndicadorUsuario("Ejemplo", "2+3");
        assertThat(sut.getAll(),hasSize(7));
        
        sut.iniciar();
        sut.agregar(indicador);
        sut.confirmar();

        assertThat(sut.getAll(),hasSize(8));
    }
    
    @Test
    public void testPersistirIndicadorRecuperaConFormulaCalculable() {
        Indicador indicador = new IndicadorUsuario("DobleInoContinuas", "INO_CONTINUAS*2");
        Empresa intel = TestFixture.intel();
        Periodo anio2016 = TestFixture.anio(2016);
        Double esperado = 3562.0 * 2;
        
	sut.iniciar();
        sut.agregar(indicador);
	sut.confirmar();
        
        System.out.println(sut.getAll());
        
        Indicador indicadorPersistido=sut.obtenerIndicador(indicador.getNombre());
        
        assertThat(indicadorPersistido.calcularPara(intel, anio2016),is(esperado));
        
    }
    
    @Test
    public void testPersistirIndicadoresCiclicosArrojaExcepcion(){
        sut.iniciar();
        sut.agregar(new IndicadorUsuario("DobleRoe", "Roe * 2"));
        sut.agregar(new IndicadorUsuario("Roe", "INO_CONTINUAS + MagicPercent/100"));
        sut.confirmar();
        try{
            sut.iniciar();
            sut.agregar(new IndicadorUsuario("MagicPercent", "DobleRoe * 1000 + EBITDA"));
            fail("Se esperaba una excepcion por crear un bucle");
        }catch(RuntimeException e){
            assertThat(e.getCause(), is(instanceOf(DirectedAcyclicGraph.CycleFoundException.class)));
        } finally{
            sut.revertir();
        }
    }

}

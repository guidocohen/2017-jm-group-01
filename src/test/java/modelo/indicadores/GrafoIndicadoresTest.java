package modelo.indicadores;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph.CycleFoundException;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author David
 */
public class GrafoIndicadoresTest {

    private GrafoIndicadores grafo;

    private IndicadorUsuario a = new IndicadorUsuario("A", "B + C");
    private IndicadorUsuario b = new IndicadorUsuario("B", "X + 0.7");
    private IndicadorUsuario c = new IndicadorUsuario("C", "X + 0.7");
    private IndicadorUsuario x = new IndicadorUsuario("X", "A*3 + J");

    @Before
    public void setUp() {
        grafo = new GrafoIndicadores();
        grafo.add(a);
        grafo.add(b);
        grafo.add(c);
        /* El Grafo queda
            A -> B
            |    |
            v    v
            C -> X
         */

    }

    @Test
    public void indicadoresConCiclos() {
        try {
            //X depende de A, que depende de B/C quienes dependen de X.
            grafo.add(x);
            fail("Se esperaba una excepcion por cliclo");
        } catch (IllegalArgumentException e) {
            assertThat(e.getCause(), is(instanceOf(CycleFoundException.class)));
        }
    }

    @Test
    public void borrarIndicadorIntermedioNoSiempreSalvaBucle() {
        grafo.remove(b);
        try {
            //Borrar B no elimina a X como nodo (ya que C depende de X)
            grafo.add(x);
            fail("Se esperaba una excepcion por cliclo");
        } catch (IllegalArgumentException e) {
            assertThat(e.getCause(), is(instanceOf(CycleFoundException.class)));
        }
    }

    @Test
    public void puedoBorrarIndicadoresYZafarElCiclo() {
        grafo.remove(b);
        grafo.remove(c);
        grafo.add(x);
    }

}

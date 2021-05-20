package modelo.indicadores;

import java.util.ArrayList;
import java.util.List;
import org.jgrapht.DirectedGraph;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 *
 * @author David
 */
public class GrafoIndicadores {

    private final DirectedGraph<String, DefaultEdge> grafo = new DirectedAcyclicGraph(DefaultEdge.class);

    public void add(Indicador indicador) {
        String parent = indicador.getNombre();
        grafo.addVertex(parent);
        for (String child : indicador.getDependencias()) {
            grafo.addVertex(child);
            grafo.addEdge(parent, child);
        }
    }

    public void remove(Indicador indicador) {
        List<DefaultEdge> edges = new ArrayList(grafo.outgoingEdgesOf(indicador.getNombre()));
        
        for (DefaultEdge edge : edges) {
            String target = grafo.getEdgeTarget(edge);
            if (grafo.inDegreeOf(target) == 1) {
                grafo.removeVertex(target);
            } else {
                grafo.removeEdge(edge);
            }
        }
    }

    @Override
    public String toString() {
        return grafo.toString();
    }
}

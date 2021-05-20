package modelo.operacionesMetodologia;

import java.util.List;
import java.util.function.Predicate;
import modelo.Periodo;
import modelo.indicadores.Indicador;
import modelo.metodologias.RankingEmpresa;

/**
 * Operacion Taxativa
 * Definen si vale la pena invertir (con 1 que se falle ya no vale).
 * @author David
 */
public abstract class OperacionTaxativa extends OperacionMetodologia {

    public OperacionTaxativa(String nombre, Indicador indicador) {
        super(nombre, indicador);
    }
    
    protected abstract Boolean testDeAceptacion(RankingEmpresa e, Periodo periodo);
    
    @Override
    public List<RankingEmpresa> operar(List<RankingEmpresa> rankings, Periodo periodo) {
        rankings.stream()
                .filter(ranking -> testDeAceptacion(ranking, periodo))
                .forEach(RankingEmpresa::sumarCantCondicionesOk);
        rankings.stream()
                .filter(ranking -> !testDeAceptacion(ranking, periodo))
                .forEach(RankingEmpresa::setNoValeLaPenaInvertir);
        return rankings;
    }

}

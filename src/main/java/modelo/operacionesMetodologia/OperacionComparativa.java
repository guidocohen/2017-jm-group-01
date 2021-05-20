package modelo.operacionesMetodologia;

import java.util.List;
import static java.util.stream.Collectors.toList;
import java.util.stream.IntStream;
import modelo.Periodo;
import modelo.indicadores.Indicador;
import modelo.metodologias.RankingEmpresa;

/**
 * Operacion No Taxativa
 * @author David
 */
public abstract class OperacionComparativa extends OperacionMetodologia {

    public OperacionComparativa(String nombre, Indicador indicador) {
        super(nombre, indicador);
    }
    
    protected abstract int comparar(RankingEmpresa rank1, RankingEmpresa rank2, Periodo periodo);

    @Override
    public List<RankingEmpresa> operar(List<RankingEmpresa> rankings, Periodo periodo) {
        List<RankingEmpresa> rankOrdenado = rankings.stream().sorted((r1,r2)-> comparar(r1,r2,periodo)).collect(toList());
        rankOrdenado.forEach(RankingEmpresa::sumarCantCondicionesOk);
        IntStream.range(0, rankOrdenado.size())
                .forEach(index -> rankOrdenado.get(index).sumarParaOrdenar(index));
        return rankings;
    }

}

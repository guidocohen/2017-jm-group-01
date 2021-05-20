package modelo.operacionesMetodologia;

import modelo.Periodo;
import modelo.indicadores.Indicador;
import modelo.metodologias.RankingEmpresa;

public class MenorAValor extends OperacionTaxativa {

    private final Double valor;

    public MenorAValor(Indicador indicador, Double valor) {
        super(TipoOperacionMetodologia.MENOR_A_VALOR.name(), indicador);
        this.valor = valor;
    }

    @Override
    protected Boolean testDeAceptacion(RankingEmpresa ranking, Periodo periodo) {
        Double resultado = indicador.calcularPara(ranking.getEmpresa(), periodo);
        return resultado < valor;
    }
}

package modelo.operacionesMetodologia;

import modelo.Periodo;
import modelo.indicadores.Indicador;
import modelo.metodologias.RankingEmpresa;

public class MayorAValor extends OperacionTaxativa {

    private final Double valor;

    public MayorAValor( Indicador indicador, Double valor) {
        super(TipoOperacionMetodologia.MAYOR_A_VALOR.name(), indicador);
        this.valor = valor;
    }

    @Override
    protected Boolean testDeAceptacion(RankingEmpresa ranking, Periodo periodo) {
        Double resultado = indicador.calcularPara(ranking.getEmpresa(), periodo);
        return resultado > valor;
    }
}

package modelo.operacionesMetodologia;

import modelo.Periodo;
import modelo.indicadores.Indicador;
import modelo.metodologias.RankingEmpresa;

public class MenorAEmpresa extends OperacionComparativa {

    public MenorAEmpresa(Indicador indicador) {
        super(TipoOperacionMetodologia.MENOR_A_EMPRESA.name(), indicador);
    }

    @Override
    public int comparar(RankingEmpresa ranking1, RankingEmpresa ranking2, Periodo periodo) {
        Double valor1 = indicador.calcularPara(ranking1.getEmpresa(), periodo);
        Double valor2 = indicador.calcularPara(ranking2.getEmpresa(), periodo);
        return valor1.compareTo(valor2);
    }
}

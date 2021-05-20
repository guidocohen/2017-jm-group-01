package modelo.operacionesMetodologia;

import modelo.metodologias.RankingEmpresa;
import modelo.Periodo;
import modelo.indicadores.Indicador;

public class MayorAEmpresa extends OperacionComparativa {

    public MayorAEmpresa(Indicador indicador) {
        super(TipoOperacionMetodologia.MAYOR_A_EMPRESA.name(), indicador);
    }

    @Override
    public int comparar(RankingEmpresa ranking1, RankingEmpresa ranking2, Periodo periodo) {
        Double valor1 = indicador.calcularPara(ranking1.getEmpresa(), periodo);
        Double valor2 = indicador.calcularPara(ranking2.getEmpresa(), periodo);
        return valor2.compareTo(valor1);
    }

}

package modelo.operacionesMetodologia;

import modelo.Periodo;
import modelo.indicadores.Indicador;
import modelo.metodologias.RankingEmpresa;

public class Decreciente extends OperacionTaxativa {

    public Decreciente(Indicador indicador) {
        super(TipoOperacionMetodologia.DECRECIENTE.name(), indicador);
    }

    @Override
    protected Boolean testDeAceptacion(RankingEmpresa ranking, Periodo periodo) {
        int cantDeAnios = periodo.cantDeAnios();
        Periodo anioDesde = new Periodo(periodo.anioDesdeFecha());
        int contadorDeDecrecimiento = 0;
	for (int i = 0; i < cantDeAnios; i++) {
            Periodo proxAnio = anioDesde.desplazar1AnioMasReciente();
            // siempre true para los indicadores que tienen una constante como formula
            // TODO cambiar <= por < cuando esten implementadas las formulas
            Double valorDesde = indicador.calcularPara(ranking.getEmpresa(), anioDesde);
            Double valorProx = indicador.calcularPara(ranking.getEmpresa(), proxAnio);
            if (valorDesde >= valorProx) {
                contadorDeDecrecimiento++;
            }
            anioDesde = anioDesde.desplazar1AnioMasReciente();
        }
        // Decrece al menos el 80% de las veces en todo el periodo
        int porcentajeDeDecrecimiento = contadorDeDecrecimiento *100 / cantDeAnios;
        return porcentajeDeDecrecimiento >= 80;
    }
}

package modelo.operacionesMetodologia;

import modelo.metodologias.RankingEmpresa;
import modelo.Periodo;
import modelo.indicadores.Indicador;

public class Creciente extends OperacionTaxativa {

    public Creciente(Indicador indicador) {
        super(TipoOperacionMetodologia.CRECIENTE.name(), indicador);
    }

    @Override
    protected Boolean testDeAceptacion(RankingEmpresa ranking, Periodo periodo) {
        int cantDeAnios = periodo.cantDeAnios();
        Periodo anioDesde = new Periodo(periodo.anioDesdeFecha());
        int contadorDeCrecimiento = 0;
        for (int i = 0; i < cantDeAnios; i++) {
            Periodo proxAnio = anioDesde.desplazar1AnioMasReciente();
            // siempre true para los indicadores que tienen una constante como formula
            // TODO cambiar <= por < cuando esten implementadas las formulas
            Double valorDesde = indicador.calcularPara(ranking.getEmpresa(), anioDesde);
            Double valorProx = indicador.calcularPara(ranking.getEmpresa(), proxAnio);
            if (valorDesde <= valorProx ) {
                contadorDeCrecimiento++;
            }
            anioDesde = anioDesde.desplazar1AnioMasReciente();
        }
        // Crece al menos el 80% de las veces en todo el periodo
        double porcentajeDeCrecimiento = contadorDeCrecimiento*100 / cantDeAnios;
        return porcentajeDeCrecimiento >= 80;
    }

}

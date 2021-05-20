package modelo.operacionesMetodologia;

import java.util.List;
import modelo.metodologias.RankingEmpresa;
import modelo.Periodo;
import modelo.indicadores.Indicador;

public abstract class OperacionMetodologia {
        protected String nombre;
        protected Indicador indicador;

        public OperacionMetodologia(String nombre, Indicador indicador) {
            this.nombre = nombre;
            this.indicador = indicador;
        }

	public abstract List<RankingEmpresa> operar(List<RankingEmpresa> ranking, Periodo periodo);

        public String getNombre() {
            return nombre;
        }

        public Indicador getIndicador() {
            return indicador;
        }

	public boolean esDeTipoMayorMenorAValor() {
		return getNombre() == TipoOperacionMetodologia.MAYOR_A_VALOR.name()
				|| getNombre() == TipoOperacionMetodologia.MENOR_A_VALOR.name();
	}

	public boolean esDeTipoCrecienteDecreciente() {
		return getNombre() == TipoOperacionMetodologia.CRECIENTE.name()
				|| getNombre() == TipoOperacionMetodologia.DECRECIENTE.name();
	}

	public boolean esDeTipoMayorMenorAEmpresa() {
		return getNombre() == TipoOperacionMetodologia.MAYOR_A_EMPRESA.name()
				|| getNombre() == TipoOperacionMetodologia.MENOR_A_EMPRESA.name();
	}

}

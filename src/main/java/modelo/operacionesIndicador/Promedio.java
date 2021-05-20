package modelo.operacionesIndicador;

import modelo.Empresa;
import modelo.Periodo;
import modelo.indicadores.Indicador;

public class Promedio extends OperacionIndicador {

	public Promedio() { }

	@Override
	public Double calcular(Periodo periodo, Empresa empresa, Indicador indicador) {
		Sumatoria sumatoria = new Sumatoria();
		return sumatoria.calcular(periodo, empresa, indicador) / periodo.cantDeAnios();
	}

	@Override
	public String getNombre() {
		return TipoOperacionIndicador.PROMEDIO.name();
	}
	
	@Override
	public String toString() {
		return getNombre();
	}

}

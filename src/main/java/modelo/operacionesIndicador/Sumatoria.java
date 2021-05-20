package modelo.operacionesIndicador;

import modelo.Empresa;
import modelo.Periodo;
import modelo.indicadores.Indicador;

public class Sumatoria extends OperacionIndicador {

	public Sumatoria() { }

	@Override
	public Double calcular(Periodo periodo, Empresa empresa, Indicador indicador) {
		int cantDeAnios = periodo.cantDeAnios();
		Double sumatoria = 0.0;
		Periodo anioProgresivo = new Periodo(periodo.anioDesdeFecha());
		for (int i = 0; i < cantDeAnios; i++) {
			sumatoria += indicador.calcularPara(empresa, anioProgresivo);
			anioProgresivo = anioProgresivo.desplazar1AnioMasReciente();
		}
		return new Double(sumatoria);
	}

	@Override
	public String getNombre() {
		return TipoOperacionIndicador.SUMATORIA.name();
	}

	@Override
	public String toString() {
		return getNombre();
	}

}

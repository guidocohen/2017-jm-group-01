package modelo.operacionesIndicador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import modelo.Empresa;
import modelo.Periodo;
import modelo.indicadores.Indicador;

public class Mediana extends OperacionIndicador {

	public Mediana() { }

	@Override
	public Double calcular(Periodo periodo, Empresa empresa, Indicador indicador) {
		int cantDeAnios = periodo.cantDeAnios();
		List<Double> resultados = new ArrayList<>();
		Periodo anioProgresivo = new Periodo(periodo.anioDesdeFecha());
		for (int i = 0; i < cantDeAnios; i++) {
			resultados.add(indicador.calcularPara(empresa, anioProgresivo));
			anioProgresivo = anioProgresivo.desplazar1AnioMasReciente();
		}
		Collections.sort(resultados);
		int size = resultados.size();
		if (size % 2 == 0)
			return new Double((resultados.get((size / 2) -1) + resultados.get(size / 2)) / 2);
		else
			return new Double(resultados.get((size - 1) / 2));
	}

	@Override
	public String getNombre() {
		return TipoOperacionIndicador.MEDIANA.name();
	}

	@Override
	public String toString() {
		return getNombre();
	}

}

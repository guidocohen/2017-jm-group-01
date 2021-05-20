package modelo.operacionesIndicador;

import modelo.Empresa;
import modelo.Periodo;
import modelo.indicadores.Indicador;

public class OperacionIndicador {
	public String getNombre(){
		return "-";
	}

	public Double calcular(Periodo periodo, Empresa empresa, Indicador indicador) {
		if (periodo.cantDeAnios() > 1) periodo = new Periodo(periodo.anioHastaFecha());
		return indicador.calcularPara(empresa, periodo);
	}

}

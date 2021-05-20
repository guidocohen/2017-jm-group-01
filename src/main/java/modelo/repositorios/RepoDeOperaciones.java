package modelo.repositorios;

import java.util.HashMap;
import java.util.Map;
import modelo.Periodo;
import modelo.indicadores.Indicador;
import modelo.indicadores.predef.IndicadorIngresoNeto;
import modelo.operacionesIndicador.Mediana;
import modelo.operacionesIndicador.OperacionIndicador;
import modelo.operacionesIndicador.Promedio;
import modelo.operacionesIndicador.Sumatoria;
import modelo.operacionesIndicador.TipoOperacionIndicador;
import modelo.operacionesMetodologia.*;

public class RepoDeOperaciones {
	private static RepoDeOperaciones instancia;
	private Map<String, OperacionMetodologia> operaciones = new HashMap<>();
	private Map<String, OperacionIndicador> operacionesIndicador = new HashMap<>();

	private RepoDeOperaciones() {
		setOperacionesPredefinidas();
		setOperacionesIndicadorPredefinidas();
	}

	public static RepoDeOperaciones getInstancia() {
		if (instancia == null)
			instancia = new RepoDeOperaciones();
		return instancia;
	}

	public Map<String, OperacionMetodologia> getOperaciones() {
		return this.operaciones;
	}

	public void setOperaciones(Map<String, OperacionMetodologia> operaciones) {
		this.operaciones = operaciones;
	}

	public void setOperacionesPredefinidas() {
                Indicador indicador = new IndicadorIngresoNeto();
		operaciones.put(TipoOperacionMetodologia.CRECIENTE.name(), new Creciente(indicador));
		operaciones.put(TipoOperacionMetodologia.DECRECIENTE.name(), new Decreciente(indicador));
		operaciones.put(TipoOperacionMetodologia.MAYOR_A_VALOR.name(), new MayorAValor(indicador,5000.0));
		operaciones.put(TipoOperacionMetodologia.MAYOR_A_EMPRESA.name(), new MayorAEmpresa(indicador));
		operaciones.put(TipoOperacionMetodologia.MENOR_A_VALOR.name(), new MenorAValor(indicador,5000.0));
		operaciones.put(TipoOperacionMetodologia.MENOR_A_EMPRESA.name(), new MenorAEmpresa(indicador));
	}
	
	public void setOperacionesIndicadorPredefinidas() {
		operacionesIndicador.put(TipoOperacionIndicador.MEDIANA.name(), new Mediana());
		operacionesIndicador.put(TipoOperacionIndicador.PROMEDIO.name(), new Promedio());
		operacionesIndicador.put(TipoOperacionIndicador.SUMATORIA.name(), new Sumatoria());
	}

	public Map<String, OperacionIndicador> getOperacionesIndicador() {
		return operacionesIndicador;
	}

	public void setOperacionesIndicador(Map<String, OperacionIndicador> operacionesIndicador) {
		this.operacionesIndicador = operacionesIndicador;
	}

}

package modelo.repositorios;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import modelo.Periodo;

import modelo.condiciones.Condicion;
import modelo.condiciones.TipoCondicion;
import modelo.condiciones.derivadas.Longevidad;
import modelo.condiciones.primitivas.LongevidadPrioritaria;
import modelo.condiciones.primitivas.LongevidadTaxativa;
import modelo.condiciones.primitivas.MargenCC;
import modelo.condiciones.primitivas.MaximizarROE;
import modelo.condiciones.primitivas.MinimizarDeuda;

public class RepoDeCondiciones {
	private static RepoDeCondiciones instancia;
	private Map<String, Condicion> condiciones = new HashMap<>();
	private Map<String, Condicion> condicionesDefinidas = new HashMap<>();

	private RepoDeCondiciones() {
		setCondicionesPredefinidas();
	}

	// private RepoDeCondiciones() { }

	public static RepoDeCondiciones getInstancia() {
		if (instancia == null)
			instancia = new RepoDeCondiciones();
		return instancia;
	}

	public Map<String, Condicion> getCondiciones() {
		return this.condiciones;
	}

	public void setCondiciones(Map<String, Condicion> condiciones) {
		this.condiciones = condiciones;
	}

	public void setCondicionesPredefinidas() {
		condiciones.put(TipoCondicion.LONGEVIDAD.name(), new Longevidad());
		condiciones.put(TipoCondicion.LONGEVIDAD_PRIORITARIA.name(), new LongevidadPrioritaria());
		condiciones.put(TipoCondicion.LONGEVIDAD_TAXATIVA.name(), new LongevidadTaxativa());
		condiciones.put(TipoCondicion.MARGEN_CC.name(), new MargenCC());
		condiciones.put(TipoCondicion.MAXIMIZAR_ROE.name(), new MaximizarROE());
		condiciones.put(TipoCondicion.MINIMIZAR_DEUDA.name(), new MinimizarDeuda());
	}

	public void agregar(Condicion condicion) {
		condiciones.put(condicion.getNombre(), condicion);
		condicionesDefinidas.put(condicion.getNombre(), condicion);
	}
	
	public Collection<Condicion> getDefinidas() {
		return condicionesDefinidas.values();
	}
	
	public Map<String, Condicion> getCondicionesDefinidas() {
		return condicionesDefinidas;
	}

	public void setCondicionesDefinidas(Map<String, Condicion> condicionesDefinidas) {
		this.condicionesDefinidas = condicionesDefinidas;
	}

}

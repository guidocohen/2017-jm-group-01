package views.vm;

import modelo.indicadores.Indicador;
import modelo.repositorios.RepoDeIndicadores;

import java.util.Collection;
import java.util.stream.Collectors;
import modelo.indicadores.IndicadorUsuario;

import org.uqbar.commons.model.ObservableUtils;
import org.uqbar.commons.utils.Observable;

import excepciones.IndicadorRecursivoException;
import modelo.repositorios.archivos.RepoDeIndicadoresArchivo;

@Observable
public class IndicadoresViewModel {
	private String formula;
	private String nombre;

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void addIndicador() {
		if(formula.toLowerCase().contains(nombre.toLowerCase())) throw new IndicadorRecursivoException();
		Indicador indicadorNuevo = new IndicadorUsuario(nombre, formula, null);
		RepoDeIndicadoresArchivo.getInstancia().agregar(indicadorNuevo);
		adaptarIndicadores(RepoDeIndicadoresArchivo.getInstancia().getDefinidos());
		ObservableUtils.firePropertyChanged(this, "indicadores");
		setNombre("");
		setFormula("");
	}

	public Collection<IndicadorAMostrar> getIndicadores() {
		return adaptarIndicadores(RepoDeIndicadoresArchivo.getInstancia().getDefinidos());
	}

	public Collection<IndicadorAMostrar> adaptarIndicadores(Collection<Indicador> indicadoresAux){
		return indicadoresAux.stream()
				.map(indicador -> new IndicadorAMostrar(indicador))
				.collect(Collectors.toList());
	}
	
}

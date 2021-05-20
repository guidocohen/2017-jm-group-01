package views.vm;

import java.util.Collection;
import java.util.List;
import modelo.Periodo;

import org.uqbar.commons.model.ObservableUtils;
import org.uqbar.commons.utils.Observable;

import modelo.metodologias.RankingEmpresa;
import modelo.metodologias.Metodologia;
import modelo.repositorios.archivos.RepoDeEmpresasArchivo;
import modelo.repositorios.archivos.RepoDeMetodologiasArchivo;

@Observable
public class MetodologiasViewModel {
	private Metodologia metodologiaSeleccionada;
	private List<RankingEmpresa> empresas;
	
	public MetodologiasViewModel() {
		getMetodologias();
		ObservableUtils.firePropertyChanged(this,"metodologias");
	}

	public Collection<Metodologia> getMetodologias() {
		return RepoDeMetodologiasArchivo.getInstancia().getAll();
	}
	
	public Metodologia getMetodologiaSeleccionada() {
		ObservableUtils.firePropertyChanged(this,"metodologias");
		return metodologiaSeleccionada;
	}

	public void setMetodologiaSeleccionada(Metodologia metodologiaSeleccionada) {
		this.metodologiaSeleccionada = metodologiaSeleccionada;
		ObservableUtils.firePropertyChanged(this,"metodologias");
	}
	
	public List<RankingEmpresa> getEmpresas() {
		return empresas;
	}

	public void evaluarMetodologia() {
		empresas = getMetodologiaSeleccionada().evaluar(RepoDeEmpresasArchivo.getInstancia().getEmpresas(),new Periodo(1));
		ObservableUtils.firePropertyChanged(this,"empresas");
	}
}

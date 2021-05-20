package ui.viewModel;

import java.io.IOException;

import org.uqbar.commons.model.UserException;

import carga.CargaBatch;
import modelo.repositorios.persistencia.RepoDeEmpresasPersistencia;
import views.vm.MainViewModel;

public class EmpresaVM extends MainViewModel {
	private String excepcion ="";
	protected CargaBatch cargaBatch = new CargaBatch();
	
	public EmpresaVM() {
		try {
			empresas = cargarEmpresas();
		} catch (UserException | IOException e) {
			setExcepcion(e.getMessage());
		}
	}

	@Override
	public void setPathEmpresas(String pathEmpresas) {
		this.pathEmpresas = pathEmpresas;
		try {
			empresas = cargarEmpresas();
		} catch (UserException | IOException e) {
			setExcepcion(e.getMessage());
		}
	}

	public String getExcepcion() {
		return excepcion;
	}

	public void setExcepcion(String excepcion) {
		this.excepcion = excepcion;
	}
	
	public void cargarEmpresasBatch() throws IOException {
		if(RepoDeEmpresasPersistencia.getInstancia().getEmpresas().isEmpty())
			empresas = cargarEmpresas();
		cargaBatch.cargarEmpresasBatch();
	}
}

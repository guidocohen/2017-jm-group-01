package modelo.repositorios;

import java.io.IOException;
import java.util.List;

import carga.CargadorEmpresas;
import excepciones.FiltrarPorNombreException;
import modelo.Empresa;


public interface RepoDeEmpresas {
	
	public List<Empresa> getEmpresas();
	
        public Empresa obtenerPorId(Integer id);
	
	public void cargarEmpresas(CargadorEmpresas cargador) throws FiltrarPorNombreException, IOException;

	public Empresa obtenerEmpresa(String nombreEmpresa);
	

}

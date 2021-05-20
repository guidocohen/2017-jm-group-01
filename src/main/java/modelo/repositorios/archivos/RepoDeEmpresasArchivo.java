package modelo.repositorios.archivos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;





import excepciones.FiltrarPorNombreException;

import modelo.Empresa;
import modelo.repositorios.RepoDeEmpresas;
import carga.CargadorEmpresas;


public class RepoDeEmpresasArchivo implements RepoDeEmpresas {
	
	
	private static RepoDeEmpresasArchivo instancia;
	
	private List<Empresa> empresas = new ArrayList<>();
    
	protected RepoDeEmpresasArchivo() {	}
	
	

	public List<Empresa> getEmpresas() {
		return this.empresas;
	}

	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}

	public void cargarEmpresas(CargadorEmpresas cargador) throws FiltrarPorNombreException, IOException {
		empresas.clear();
		empresas.addAll(cargador.cargar());
	}

	public Empresa obtenerEmpresa(String nombreEmpresa) {
		Empresa nuevaEmpresa = this.getEmpresas().stream()
			.filter(empresa -> empresa.getNombre().equalsIgnoreCase(nombreEmpresa)).findAny().orElse(null);
		if (nuevaEmpresa == null)
			throw new FiltrarPorNombreException(nombreEmpresa);
		return nuevaEmpresa;
	}

	public static RepoDeEmpresasArchivo getInstancia() {
		if (instancia == null)
			instancia= new RepoDeEmpresasArchivo();
		return instancia;
	}

        @Override
        public Empresa obtenerPorId(Integer id) {
            throw new UnsupportedOperationException("En los archivos no se cuenta con ids");
        }
	
}
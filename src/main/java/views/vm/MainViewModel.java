package views.vm;

import carga.CargaBatch;
import carga.CargadorEmpresas;
import carga.CargadorEmpresasBuilder;
import carga.CargadorIndicadores;
import carga.CargadorIndicadoresBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import org.uqbar.commons.model.ObservableUtils;
import org.uqbar.commons.model.UserException;
import org.uqbar.commons.utils.Observable;

import excepciones.*;
import modelo.Cuenta;
import modelo.Empresa;
import modelo.Periodo;

import modelo.indicadores.Indicador;
import modelo.indicadores.IndicadorUsuario;
import modelo.repositorios.archivos.RepoDeEmpresasArchivo;
import modelo.repositorios.archivos.RepoDeIndicadoresArchivo;
import modelo.repositorios.persistencia.RepoDeEmpresasPersistencia;

@Observable
public class MainViewModel {
	protected Periodo periodo;
	protected Collection<IndicadorAMostrar> indicadores;
	protected List<Cuenta> cuentas;
	protected List<Empresa> empresas;
	protected Empresa empresaSeleccionada;
	protected String pathEmpresas;
	protected String pathIndicadores = "./src/test/resources/indicadores.json";
	protected Integer anio;
	protected CargaBatch cargaBatch = new CargaBatch();
	
	public MainViewModel() {
		try {
			empresas = cargarEmpresas();
		} catch (UserException | IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public String getPathIndicadores() {
		return pathIndicadores;
	}

	public void setPathIndicadores(String pathIndicadores) {
		this.pathIndicadores = pathIndicadores;
	}

	public List<Cuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}

	public String getPathEmpresas() {
		return pathEmpresas;
	}

	public void setPathEmpresas(String pathEmpresas) {
		this.pathEmpresas = pathEmpresas;
		try {
			empresas = cargarEmpresas();
		} catch (UserException | IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
	}

	public Empresa getEmpresaSeleccionada() {
		return empresaSeleccionada;
	}

	public void setEmpresaSeleccionada(Empresa empresaSeleccionada) {
		this.empresaSeleccionada = empresaSeleccionada;
	}

	public Collection<IndicadorAMostrar> getIndicadores() {
		return indicadores;
	}

	public void setIndicadores(List<IndicadorAMostrar> indicadores) {
		this.indicadores = indicadores;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public List<Empresa> getEmpresas() {
		return empresas;
	}
	
	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}

	public List<Empresa> cargarEmpresas() throws IOException {
		pathEmpresas = cargaBatch.mostrarArchivosDeUnDirectorio();
		if (pathEmpresas == null) return new ArrayList<>();
		try {
			CargadorEmpresas cargador = new CargadorEmpresasBuilder()
					.conArchivo(new File(pathEmpresas))
					.paraFormatoAdecuado();
			//RepoDeEmpresasPersistencia.getInstancia().iniciar();
			RepoDeEmpresasPersistencia.getInstancia().cargaInicial(cargador);
			//RepoDeEmpresasPersistencia.getInstancia().confirmar();
			return RepoDeEmpresasPersistencia.getInstancia().getEmpresas();
		} catch (FileNotFoundException e) {
			throw new ArchivoNoEncontradoException();
		}
	}

	public void buscarCuentasEmpresa() {
		periodo = new Periodo(anio.toString());
		setCuentas(empresaSeleccionada.filtrarCuentasPorPeriodo(periodo));
		if (cuentas.isEmpty()) {
			throw new FiltrarPorPeriodoException();
		}
	}

	public void buscarIndicadores() {
		RepoDeIndicadoresArchivo.getInstancia().setIndicadoresPredefinidos();
		mostrarIndicadores();
	}

	public void cargarIndicadoresDefinidos()
			throws FiltrarPorNombreException, IOException {
		try {
			CargadorIndicadores cargador = new CargadorIndicadoresBuilder()
					.conArchivo(new File(pathIndicadores))
					.paraFormatoAdecuado();
			RepoDeIndicadoresArchivo.getInstancia().cargarIndicadoresDefinidos(cargador);
		} catch (FileNotFoundException e) {
			throw new ArchivoNoEncontradoException();
		}
	}
	
	public void guardarIndicadoresDefinidos() throws IOException {
		try {
			CargadorIndicadores cargador = new CargadorIndicadoresBuilder()
					.conArchivo(new File(pathIndicadores))
					.paraFormatoAdecuado();
			cargador.guardar(RepoDeIndicadoresArchivo.getInstancia().getIndicadoresDefinidos());
		} catch (FileNotFoundException e) {
			throw new ArchivoNoEncontradoException();
		}
	}

	public void clean() {
		setEmpresaSeleccionada(null);
		setCuentas(new ArrayList<>());
		setIndicadores(new ArrayList<>());
		RepoDeIndicadoresArchivo.getInstancia().limpiarIndicadores();
		RepoDeEmpresasArchivo.getInstancia().setEmpresas(new ArrayList<>());
		setPeriodo(new Periodo());
		setAnio(null);
	}

	public Collection<IndicadorAMostrar> adaptarIndicadores(Collection<Indicador> indicadoresAux) {
		return indicadoresAux.stream().map(indicador -> new IndicadorAMostrar((IndicadorUsuario) indicador, empresaSeleccionada, periodo))
				.collect(Collectors.toList());
	}

	private void mostrarIndicadores() {
		indicadores = adaptarIndicadores(RepoDeIndicadoresArchivo.getInstancia().getAll());
		ObservableUtils.firePropertyChanged(this, "indicadores");
	}
	
	public Empresa buscarEmpresa(String nombreEmpresa) {
		return getEmpresas().stream()
				.filter(empresa -> empresa.getNombre().equalsIgnoreCase(nombreEmpresa))
				.collect(Collectors.toList()).get(0);
	}
}

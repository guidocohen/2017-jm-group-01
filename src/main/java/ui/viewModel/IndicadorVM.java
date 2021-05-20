package ui.viewModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.uqbar.commons.model.UserException;
import carga.CargadorEmpresasBuilder;
import carga.CargadorIndicadores;
import carga.CargadorIndicadoresBuilder;
import excepciones.ArchivoNoEncontradoException;
import excepciones.FiltrarPorNombreException;
import excepciones.IndicadorRecursivoException;
import modelo.*;
import modelo.indicadores.*;
import modelo.repositorios.*;
import modelo.repositorios.archivos.*;
import views.vm.IndicadorAMostrar;

public class IndicadorVM {
	private static RepoDeIndicadores repoIndicadores;
	private static RepoDeEmpresas repoEmpresas;
	private Collection<IndicadorAMostrar> indicadoresDefinidos;
	private Collection<IndicadorAMostrar> indicadoresPredefinidos;
	private IndicadorAMostrar indicadorAMostrar;
	private Empresa empresaSeleccionada;
	private List<Empresa> empresas;
	private String anio;
	private CargadorIndicadores cargador = new CargadorIndicadoresBuilder()
											.conArchivo(new File("./src/test/resources/indicadores.json"))
											.paraFormatoAdecuado();
	
	public IndicadorVM() {
		repoIndicadores = RepoDeIndicadoresArchivo.getInstancia();
		repoEmpresas = RepoDeEmpresasArchivo.getInstancia();
		try {
			repoIndicadores.cargarIndicadoresDefinidos(cargador);
			repoEmpresas.cargarEmpresas(new CargadorEmpresasBuilder().conArchivo(new File("src/test/resources/empresas.json")).paraFormatoAdecuado());
			empresas = repoEmpresas.getEmpresas();
		} catch (IOException e) {
			new UserException("error al cargar recursos");
		}
	}

	//public void buscarIndicadores(long id) {
	public void buscarIndicadores() {
		indicadoresPredefinidos = adaptarIndicadores(repoIndicadores.getIndicadoresPredefinidos());
		indicadoresDefinidos = adaptarIndicadores(repoIndicadores.getDefinidos());

		//indicadoresDefinidos.removeIf(i-> ! i.delUsuario(id));
	}
	
	public void evaluar(String nombre, String empresa, String anio){
		Indicador indicador = repoIndicadores.obtenerIndicador(nombre);
		empresaSeleccionada = buscarEmpresa(empresa);
		this.anio = anio;
		Periodo periodo = new Periodo(Integer.valueOf(anio).toString());
		adaptar(indicador);
		indicadorAMostrar.setResultado(indicador.calcularPara(empresaSeleccionada, periodo));
	}

	public void altaIndicador(Indicador indicador) {
		if (indicador.getFormula().toLowerCase().contains(indicador.getNombre().toLowerCase()))
			throw new IndicadorRecursivoException();
		repoIndicadores.agregar(indicador);
		indicadoresDefinidos = adaptarIndicadores(repoIndicadores.getDefinidos());
		try {
			guardarIndicadoresDefinidos();
		} catch (IOException e) {
			throw new UserException("Error al guardar indicador");
		}
	}
	
	public void bajaIndicador(Indicador indicador){
		repoIndicadores.eliminar(indicador);
		indicadoresDefinidos = adaptarIndicadores(repoIndicadores.getDefinidos());
		try {
			guardarIndicadoresDefinidos();
		} catch (IOException e) {
			throw new UserException("Error al guardar indicador");
		}
	}
	
	public void guardarIndicadoresDefinidos() throws IOException {
		try {
			cargador.guardar(repoIndicadores.getIndicadoresDefinidos());
		} catch (FileNotFoundException e) {
			throw new ArchivoNoEncontradoException();
		}
	}
	
	public void cargarIndicadoresDefinidos() throws FiltrarPorNombreException, IOException {
		try {
			repoIndicadores.cargarIndicadoresDefinidos(cargador);
		} catch (FileNotFoundException e) {
			throw new ArchivoNoEncontradoException();
		}
	}
	
	public Collection<IndicadorAMostrar> adaptarIndicadores(Collection<Indicador> indicadoresAux) {
		return indicadoresAux.stream().map(indicador -> new IndicadorAMostrar(indicador)).collect(Collectors.toList());
	}

	public void adaptar(Indicador indicador) {
		indicadorAMostrar = new IndicadorAMostrar(indicador);
	}

	public Empresa buscarEmpresa(String nombreEmpresa) {
		return repoEmpresas.getEmpresas().stream()
				.filter(empresa -> empresa.getNombre().equalsIgnoreCase(nombreEmpresa))
				.collect(Collectors.toList()).get(0);
	}

	public IndicadorAMostrar getIndicadorAMostrar() {
		return indicadorAMostrar;
	}

	public Collection<IndicadorAMostrar> getIndicadoresDefinidos() {
		return indicadoresDefinidos;
	}
	
	public Empresa getEmpresaSeleccionada(){
		return empresaSeleccionada;
	}

	public List<Empresa> getEmpresas() {
		return empresas;
	}

	public Collection<IndicadorAMostrar> getIndicadoresPredefinidos() {
		return indicadoresPredefinidos;
	}

	public String getAnio() {
		return anio;
	}

}

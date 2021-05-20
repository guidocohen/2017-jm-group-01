package modelo.repositorios.archivos;

import java.util.Collection;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import carga.CargadorIndicadores;
import excepciones.FiltrarPorNombreException;
import java.util.Arrays;
import modelo.Empresa;
import modelo.Periodo;
import modelo.indicadores.Indicador;
import modelo.indicadores.TipoIndicador;
import modelo.repositorios.RepoDeIndicadores;

public class RepoDeIndicadoresArchivo implements RepoDeIndicadores {
	private static RepoDeIndicadoresArchivo instancia;
	private Map<String, Indicador> indicadoresPredefinidos = new HashMap<>();
	private Map<String, Indicador> indicadoresDefinidos = new HashMap<>();

	private RepoDeIndicadoresArchivo() {
		setIndicadoresPredefinidos();
	}

	@Override
	public void setIndicadoresPredefinidos() {
		Arrays.asList(TipoIndicador.values())
				.forEach(tipoIndicador -> indicadoresPredefinidos.put(tipoIndicador.name(), tipoIndicador.getIndicador()));
	}

	public Collection<Indicador> getIndicadoresPredefinidos() {
 		return indicadoresPredefinidos.values();
	}    
	
	public static RepoDeIndicadores getInstancia() {
		if (instancia == null) {
			instancia = new RepoDeIndicadoresArchivo();
		}
		return instancia;
	}

	public Collection<Indicador> getPredefinidos() {
		return indicadoresPredefinidos.values();
	}
	
        @Override
	public Collection<Indicador> getDefinidos() {
		return indicadoresDefinidos.values();
	}

        @Override
	public Indicador getIndicadorPorNombre(String nombreIndicador) {
		setIndicadoresPredefinidos();
		return getIndicadores().get(nombreIndicador);
	}

        @Override
	public void agregar(Indicador indicador) {
		indicadoresDefinidos.put(indicador.getNombre(), indicador);
	}

        @Override
	public Map<String, Indicador> getIndicadoresDefinidos() {
		return indicadoresDefinidos;
	}

	public void setIndicadoresDefinidos(Map<String, Indicador> indicadoresDefinidos) {
		this.indicadoresDefinidos = indicadoresDefinidos;
	}
	
        @Override
	public void cargarIndicadoresDefinidos(CargadorIndicadores cargador)
			throws IOException, FiltrarPorNombreException {

		indicadoresDefinidos.putAll(cargador.cargarIndicadores());
	}

	public void guardarIndicadoresDefinidos(CargadorIndicadores cargador)
			throws IOException {
		cargador.guardar(indicadoresDefinidos);
	}

        @Override
	public Indicador obtenerIndicador(String nombre) {
		Indicador indicadorBuscado = getIndicadores().values().stream()
				.filter(indicador -> indicador.getNombre().equalsIgnoreCase(nombre)).findAny().orElse(null);
		if (indicadorBuscado == null)
			throw new FiltrarPorNombreException(nombre);
		return indicadorBuscado;
	}
	
    
	public void calcularIndicadoresPara(Empresa empresa, Periodo periodo){
		indicadoresPredefinidos.values().stream()
			.forEach(indicador -> indicador.calcularPara(empresa, periodo));
		indicadoresDefinidos.values().stream()
			.forEach(indicador -> indicador.calcularPara(empresa, periodo));
	}
	
        @Override
	public void limpiarIndicadores(){
		indicadoresDefinidos = new HashMap<String, Indicador>();
		setIndicadoresPredefinidos();
	}

	@Override
	public void eliminar(Indicador indicador) {
		if (indicadoresDefinidos.containsKey(indicador.getNombre()))
			indicadoresDefinidos.remove(indicador.getNombre());
	}

	@Override
	public Collection<Indicador> getAll() {
		return getIndicadores().values();
	}
	
	public Map<String,Indicador> getIndicadores(){
        Map<String, Indicador> indicadores = new HashMap<>();
        indicadores.putAll(indicadoresDefinidos);
        indicadores.putAll(indicadoresPredefinidos);
		return indicadores;
	}
}

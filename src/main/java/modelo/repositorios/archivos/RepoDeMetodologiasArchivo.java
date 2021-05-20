package modelo.repositorios.archivos;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import modelo.Periodo;

import modelo.metodologias.Metodologia;
import modelo.metodologias.MetodologiaWarrenBuffet;
import modelo.repositorios.RepoDeMetodologias;

public class RepoDeMetodologiasArchivo implements RepoDeMetodologias {
	private static RepoDeMetodologiasArchivo instancia;
	private Map<String, Metodologia> metodologias = new HashMap<>();
	private Map<String, Metodologia> metodologiasDefinidas = new HashMap<>();

	private RepoDeMetodologiasArchivo() {
		setMetodologiasPredefinidas();
	}

	public void setMetodologiasPredefinidas() {
		metodologias.put("WARREN_BUFFET", new MetodologiaWarrenBuffet());
	}

	public static RepoDeMetodologiasArchivo getInstancia() {
		if (instancia == null) {
			instancia = new RepoDeMetodologiasArchivo();
		}
		return instancia;
	}

	public Collection<Metodologia> getAll() {
		return metodologias.values();
	}
	
	public Collection<Metodologia> getDefinidas() {
		return metodologiasDefinidas.values();
	}

	public Metodologia getMetodologiaPorNombre(String nombreMetodologia) {
		setMetodologiasPredefinidas();
		return metodologias.get(nombreMetodologia);
	}

	public void agregar(Metodologia metodologia) {
		metodologiasDefinidas.put(metodologia.getNombre(), metodologia);
		metodologias.put(metodologia.getNombre(), metodologia);
	}

	public Map<String, Metodologia> getMetodologias() {
		return metodologias;
	}
	
	public void setMetodologias(Map<String, Metodologia> metodologias) {
		this.metodologias = metodologias;
	}

	public Map<String, Metodologia> getMetodologiasDefinidas() {
		return metodologiasDefinidas;
	}

	public void setMetodologiasDefinidas(Map<String, Metodologia> metodologiasDefinidas) {
		this.metodologiasDefinidas = metodologiasDefinidas;
	}
	
/*	public RepoDeMetodologias cargarMetodologiasDefinidas(CargadorMetodologias cargador)
			throws IOException, FiltrarPorNombreException, ParseException {

		metodologiasDefinidas.putAll(cargador.cargarMetodologias());
		metodologias.putAll(cargador.cargarMetodologias());
		return instancia;
	}

	public void guardarMetodologiasDefinidas(CargadorMetodologias cargador)
			throws IOException {
		cargador.guardar(metodologiasDefinidas);
	}

	public Metodologia obtenerMetodologia(String nombre) {
		Metodologia metodologiaBuscada = getMetodologias().values().stream()
				.filter(metodologia -> metodologia.getNombre().equalsIgnoreCase(nombre)).findAny().orElse(null);
		if (metodologiaBuscada == null)
			throw new FiltrarPorNombreException(nombre);
		return metodologiaBuscada;
	}
*/	
	public void limpiarMetodologias(){
		metodologiasDefinidas = new HashMap<String, Metodologia>();
		metodologias = new HashMap<String, Metodologia>();
	}

        @Override
        public Metodologia getById(long id) {
                throw new UnsupportedOperationException("En los archivos no se cuenta con ids");
        }

}

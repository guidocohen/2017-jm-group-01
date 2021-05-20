package excepciones;

import org.uqbar.commons.model.UserException;

@SuppressWarnings("serial")
public class FiltrarPorNombreException extends UserException {

	public FiltrarPorNombreException(String nombre) {
		super("No se encontraron resultados para " + nombre);
	}

}

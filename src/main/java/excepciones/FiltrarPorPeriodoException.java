package excepciones;

import org.uqbar.commons.model.UserException;

@SuppressWarnings("serial")
public class FiltrarPorPeriodoException extends UserException {

	public FiltrarPorPeriodoException() {
		super("No se encontraron resultados para ese anio");

	}

	public FiltrarPorPeriodoException(String message) {
		super(message);
	}

}

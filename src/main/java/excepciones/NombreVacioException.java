package excepciones;

import org.uqbar.commons.model.UserException;

public class NombreVacioException extends UserException {

	public NombreVacioException(String criterio) {
		super("Ingrese un nombre para la " + criterio);
	}

}

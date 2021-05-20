package excepciones;

import org.uqbar.commons.model.UserException;

public class FormatoNoSoportadoException extends UserException {
	public FormatoNoSoportadoException() {
		super("Formato de archivo no soportado");
	}
}

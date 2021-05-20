package excepciones;

import org.uqbar.commons.model.UserException;

public class CantDeAniosInvalidaException extends UserException {
	public CantDeAniosInvalidaException() {
		super("Cantidad Invalida de Anios");
	}
}

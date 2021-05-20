package excepciones;

import org.uqbar.commons.model.UserException;

@SuppressWarnings("serial")
public class ArchivoNoEncontradoException extends UserException {

	public ArchivoNoEncontradoException() {
		super("Archivo inexistente");

	}

}

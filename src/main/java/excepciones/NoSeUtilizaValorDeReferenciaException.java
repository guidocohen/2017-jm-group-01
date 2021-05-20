package excepciones;

import org.uqbar.commons.model.UserException;

public class NoSeUtilizaValorDeReferenciaException extends UserException {

	public NoSeUtilizaValorDeReferenciaException() {
		super("Una operacion prioritaria no utiliza un valor de referencia");
	}

}

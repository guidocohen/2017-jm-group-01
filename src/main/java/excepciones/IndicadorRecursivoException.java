package excepciones;

import org.uqbar.commons.model.UserException;

public class IndicadorRecursivoException extends UserException {

	public IndicadorRecursivoException() {
		super("Indicador Recursivo - Intente nuevamente con otro");
	}

}

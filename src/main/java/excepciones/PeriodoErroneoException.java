package excepciones;

import org.uqbar.commons.model.UserException;

@SuppressWarnings("serial")
public class PeriodoErroneoException extends UserException {

	public PeriodoErroneoException() {
		super("Periodo Erroneo");

	}

}

package excepciones;

import org.uqbar.commons.model.UserException;

public class NoComparaConEmpresa2Exception extends UserException {

	public NoComparaConEmpresa2Exception() {
		super("Una condicion/operacion taxativa no utiliza otra empresa para comparar");
	}

}

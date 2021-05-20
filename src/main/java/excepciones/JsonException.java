package excepciones;

import org.uqbar.commons.model.UserException;

@SuppressWarnings("serial")
public class JsonException extends UserException {

    public JsonException() {
        super("Error: JSON mal formado");
    }

    public JsonException(Exception cause) {
        super("Error: JSON mal formado", cause);
    }

}

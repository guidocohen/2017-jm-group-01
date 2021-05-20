package excepciones;

import java.io.IOException;
import org.uqbar.commons.model.UserException;

/**
 *
 * @author David
 */
public class InterpretacionDeExpresionException extends UserException{
    
    public InterpretacionDeExpresionException(String message) {
        super(message);
    }

    public InterpretacionDeExpresionException(String string, IOException ex) {
        super(string, ex);
    }
    
}

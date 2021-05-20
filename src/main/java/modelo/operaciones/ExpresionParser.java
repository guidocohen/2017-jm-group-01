package modelo.operaciones;

import excepciones.InterpretacionDeExpresionException;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;

/**
 *
 * @author David
 */
public class ExpresionParser {

    public Calculable interpretar(String expresion, ExpresionBuilder builder) throws InterpretacionDeExpresionException {
        try {
            StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(expresion));
            tokenizer.ordinaryChar('/');
            tokenizer.ordinaryChar('-');
            tokenizer.wordChars('_', '_');

            tokenizer.nextToken();

            while (tokenizer.ttype != StreamTokenizer.TT_EOF) {
                switch (tokenizer.ttype) {
                    case StreamTokenizer.TT_WORD:
                        builder = builder.identificador(tokenizer.sval);
                        break;
                    case StreamTokenizer.TT_NUMBER:
                        builder = builder.numero(tokenizer.nval);
                        break;
                    case 40:
                        builder = builder.parentesisAbre();
                        break;
                    case 41:
                        builder = builder.parentesisCierra();
                        break;
                    case 42:
                        builder = builder.multiplicacion();
                        break;
                    case 43:
                        builder = builder.suma();
                        break;
                    case 45:
                        builder = builder.resta();
                        break;
                    case 47:
                        builder = builder.division();
                        break;
                    default:
                        throw new InterpretacionDeExpresionException("Caracter no esperado: " + (char) tokenizer.ttype);
                }
                tokenizer.nextToken();
            }
            return builder.build();
        } catch (IOException ex) {
            throw new InterpretacionDeExpresionException("Se produjo un error de IO al interpretar \"" + expresion + "\"", ex);
        }
    }

    public Calculable interpretar(String expresion) throws InterpretacionDeExpresionException{
        return interpretar(expresion, new ExpresionBuilder());
    }

}

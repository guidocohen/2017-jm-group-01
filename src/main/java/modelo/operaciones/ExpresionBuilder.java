package modelo.operaciones;

import excepciones.FiltrarPorNombreException;
import excepciones.InterpretacionDeExpresionException;
import java.util.Stack;
import modelo.indicadores.Indicador;
import modelo.repositorios.archivos.RepoDeIndicadoresArchivo;

/**
 * Constructor de expresiones basado en el algoritmo
 * <a href="https://en.wikipedia.org/wiki/Shunting-yard_algorithm">Shutting-yard</a>
 *
 * @author David
 */
public class ExpresionBuilder {

    private final Stack<Calculable> calculables;
    private final Stack<Operacion> operaciones;
    private ExpresionBuilder recursivo;

    public ExpresionBuilder() {
        calculables = new Stack<>();
        operaciones = new Stack<>();
    }

    private ExpresionBuilder(ExpresionBuilder recursivo) {
        this();
        this.recursivo = recursivo;
    }

    public ExpresionBuilder identificador(String sval) {
        calculables.push(new ValorString(sval));
        return this;
    }

    public ExpresionBuilder numero(double nval) {
        calculables.push(new ValorConstante(nval));
        return this;
    }

    public ExpresionBuilder calculable(Calculable valor) {
        calculables.push(new ValorParentesis(valor));
        return this;
    }

    public ExpresionBuilder suma() {
        return validarPrecedencia(Operacion.SUMA);
    }

    public ExpresionBuilder resta() {
        return validarPrecedencia(Operacion.RESTA);
    }

    public ExpresionBuilder multiplicacion() {
        return validarPrecedencia(Operacion.MULTIPLICACION);
    }

    public ExpresionBuilder division() {
        return validarPrecedencia(Operacion.DIVISION);
    }

    public ExpresionBuilder parentesisAbre() {
        return new ExpresionBuilder(this);
    }

    public ExpresionBuilder parentesisCierra() {
        if (recursivo == null) {
            throw new InterpretacionDeExpresionException("No se esperaba un parentesis que cierra en este momento.");
        }
        return recursivo.calculable(finalValue());
    }

    private ExpresionBuilder validarPrecedencia(Operacion operacion) {
        if (!operaciones.isEmpty() && operacion.menorPrecendenciaQue(operaciones.peek())) {
            reducir();
        }
        operaciones.push(operacion);
        return this;
    }

    private void reducir() {
        Expresion expresion = new Expresion();
        expresion.setOperando2(calculables.pop());
        expresion.setOperacion(operaciones.pop());
        expresion.setOperando1(calculables.pop());
        calculables.push(expresion);
    }

    private Calculable finalValue() {
        while (operaciones.size() > 0 && calculables.size() > 1) {
            reducir();
        }
        if (operaciones.empty() && calculables.size() == 1) {
            return calculables.pop();
        }
        throw new InterpretacionDeExpresionException("Expresion mal formada. Operadores: " + operaciones.size() + " Operandos:" + calculables.size() + ".");
    }

    public Calculable build() {
        if (recursivo != null) {
            throw new InterpretacionDeExpresionException("Hay parentesis sin cerrar.");
        }
        return finalValue();
    }

}

package modelo.operaciones;

import java.util.Set;
import modelo.Empresa;
import modelo.Periodo;

/**
 *
 * @author David
 */
public class ValorParentesis implements Calculable {

    private final Calculable valor;

    public ValorParentesis(Calculable valor) {
        this.valor = valor;
    }

    @Override
    public double getValor(Empresa empresa, Periodo periodo) {
        return valor.getValor(empresa, periodo);
    }

    @Override
    public String toString() {
        return "(" + valor.toString() + ')';
    }

    @Override
    public Set<String> getDependencias() {
        return valor.getDependencias();
    }

}

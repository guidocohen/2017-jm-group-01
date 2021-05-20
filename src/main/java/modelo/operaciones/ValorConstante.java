package modelo.operaciones;

import java.util.Collections;
import java.util.Set;
import modelo.Empresa;
import modelo.Periodo;

public class ValorConstante implements Calculable {

    private double valorConstante;

    public ValorConstante(double valor) {
        this.valorConstante = valor;
    }

    @Override
    public double getValor(Empresa empresa, Periodo periodo) {
        return this.valorConstante;
    }

    @Override
    public String toString() {
        return String.valueOf(valorConstante);
    }

    @Override
    public Set<String> getDependencias() {
        return Collections.EMPTY_SET;
    }

}

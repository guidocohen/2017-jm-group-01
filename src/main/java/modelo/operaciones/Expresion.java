package modelo.operaciones;

import java.util.HashSet;
import java.util.Set;
import modelo.Empresa;
import modelo.Periodo;

public class Expresion implements Calculable {

    private Calculable operando1;
    private Calculable operando2;
    private Operacion operacion;

    @Override
    public double getValor(Empresa empresa, Periodo periodo) {
        return operacion.calcular(operando1.getValor(empresa, periodo), operando2.getValor(empresa, periodo));
    }

    public Calculable getOperando1() {
        return operando1;
    }

    public void setOperando1(Calculable operando1) {
        this.operando1 = operando1;
    }

    public Calculable getOperando2() {
        return operando2;
    }

    public void setOperando2(Calculable operando2) {
        this.operando2 = operando2;
    }

    public Operacion getOperacion() {
        return operacion;
    }

    public void setOperacion(Operacion operacion) {
        this.operacion = operacion;
    }

    @Override
    public Set<String> getDependencias() {
        Set<String> dependencias = new HashSet<>();
        dependencias.addAll(operando1.getDependencias());
        dependencias.addAll(operando2.getDependencias());
        return dependencias;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(operando1)
                .append(operacion)
                .append(operando2)
                .toString();
    }

}

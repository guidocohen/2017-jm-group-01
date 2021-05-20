package modelo.indicadores.predef;

import java.util.Collections;
import java.util.Set;
import modelo.Empresa;
import modelo.Periodo;
import modelo.indicadores.Indicador;

/**
 *
 * @author nefre
 */
public class IndicadorLongevidad implements Indicador {

    @Override
    public String getFormula() {
        return "EDAD";
    }

    @Override
    public String getNombre() {
        return "Longevidad";
    }

    @Override
    public Set<String> getDependencias() {
        return Collections.EMPTY_SET;
    }

    @Override
    public Double calcularPara(Empresa empresa, Periodo periodo) {
        return Double.valueOf(empresa.getEdad());
    }

    @Override
    public String toString() {
        return getNombre();
    }
}

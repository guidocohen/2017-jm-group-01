package modelo.indicadores.predef;

import java.util.Collections;
import java.util.Set;
import modelo.Empresa;
import modelo.Periodo;
import modelo.indicadores.Indicador;
import modelo.indicadores.TipoIndicador;

public class IndicadorDividendos implements Indicador {

	public Double calcularPara(Empresa empresa, Periodo periodo) {
		return 10.0; // TODO implementar
	}

	public String getFormula() {
		return "10";
	}

	public String getNombre() {
		return TipoIndicador.DIVIDENDOS.name();
	}
	
	@Override 
	public String toString(){
		return getNombre();
	}
        
        @Override
        public Set<String> getDependencias() {
            return Collections.EMPTY_SET;
        }
}

package modelo.indicadores.predef;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import modelo.Empresa;
import modelo.Periodo;
import modelo.indicadores.Indicador;
import modelo.indicadores.TipoIndicador;

public class IndicadorROE implements Indicador {

	public Double calcularPara(Empresa empresa, Periodo periodo) {

        Double ingresoNeto = new IndicadorIngresoNeto()
                .calcularPara(empresa, periodo);

        Double dividendos = new IndicadorDividendos()
                .calcularPara(empresa, periodo);

        Double capitalTotal = new IndicadorCapitalTotal()
                .calcularPara(empresa, periodo);
        
        return (ingresoNeto - dividendos) / capitalTotal;
	}

	public String getFormula() {
		return "(" + TipoIndicador.INGRESO_NETO.name() + " - " + TipoIndicador.DIVIDENDOS.name() + ") / "
				+ TipoIndicador.CAPITALTOTAL.name();
	}

	public String getNombre() {
		return TipoIndicador.ROE.name();
	}
	
	@Override 
	public String toString(){
		return getNombre();
	}
        
        @Override
        public Set<String> getDependencias() {
            return Stream.of(TipoIndicador.INGRESO_NETO.name(),
                    TipoIndicador.DIVIDENDOS.name(),
                    TipoIndicador.CAPITALTOTAL.name())
                    .collect(Collectors.toSet());
        }

}

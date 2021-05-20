package modelo.indicadores.predef;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import modelo.Empresa;
import modelo.Periodo;
import modelo.TipoCuenta;
import modelo.indicadores.Indicador;
import modelo.indicadores.TipoIndicador;

public class IndicadorProporcionDeDeuda implements Indicador {
	public Double calcularPara(Empresa empresa, Periodo periodo) {
		Double resultadoFDS = empresa.obtenerResultadoDeCuenta(TipoCuenta.FDS.name(), periodo);

		return resultadoFDS - 20.0; // TODO implementar (este es un ejemplo)
	}

	public String getFormula() {
		return TipoCuenta.FDS.name() + " - " + "20.0";
	}

	public String getNombre() {
		return TipoIndicador.PROPORCION_DE_DEUDA.name();
	}
	
	@Override 
	public String toString(){
		return getNombre();
	}
        
        @Override
        public Set<String> getDependencias() {
            return Stream.of(TipoCuenta.FDS.name()).collect(Collectors.toSet());
        }
}

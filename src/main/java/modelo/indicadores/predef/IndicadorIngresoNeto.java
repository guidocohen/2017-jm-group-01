package modelo.indicadores.predef;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import modelo.Empresa;
import modelo.Periodo;
import modelo.TipoCuenta;
import modelo.indicadores.Indicador;
import modelo.indicadores.TipoIndicador;

public class IndicadorIngresoNeto implements Indicador {

    public Double calcularPara(Empresa empresa, Periodo periodo) {
		Double resultadoINO_Continua = empresa.obtenerResultadoDeCuenta(TipoCuenta.INO_CONTINUAS.name(), periodo);
		Double resultadoINO_Discontinua = empresa.obtenerResultadoDeCuenta(TipoCuenta.INO_DISCONTINUAS.name(), periodo);
        //TODO validar las cuentas, si no da tirar la excepcion "NoSePuedeCalcularIndicadorException"
       
        return resultadoINO_Continua + resultadoINO_Discontinua;
    }

	public String getFormula() {
		return TipoCuenta.INO_CONTINUAS.name() + " + " + TipoCuenta.INO_DISCONTINUAS.name();
	}

	public String getNombre() {
		return TipoIndicador.INGRESO_NETO.name();
	}
	
	@Override 
	public String toString(){
		return getNombre();
	}
        
        @Override
        public Set<String> getDependencias() {
            return Stream.of(TipoCuenta.INO_CONTINUAS.name(),TipoCuenta.INO_DISCONTINUAS.name())
                    .collect(Collectors.toSet());
        }

}

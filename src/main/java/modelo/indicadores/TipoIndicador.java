package modelo.indicadores;

import modelo.indicadores.predef.IndicadorCapitalTotal;
import modelo.indicadores.predef.IndicadorDividendos;
import modelo.indicadores.predef.IndicadorIngresoNeto;
import modelo.indicadores.predef.IndicadorMargen;
import modelo.indicadores.predef.IndicadorProporcionDeDeuda;
import modelo.indicadores.predef.IndicadorROE;

public enum TipoIndicador {
    ROE(new IndicadorROE()), 
    INGRESO_NETO(new IndicadorIngresoNeto()), 
    DIVIDENDOS(new IndicadorDividendos()), 
    CAPITALTOTAL(new IndicadorCapitalTotal()), 
    MARGEN(new IndicadorMargen()), 
    PROPORCION_DE_DEUDA(new IndicadorProporcionDeDeuda());
    
   
    private final Indicador indicador;
    
    TipoIndicador(Indicador indicador){
        this.indicador = indicador;
    }
    
    public Indicador getIndicador(){
        return indicador;
    }
	
}

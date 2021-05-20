package modelo.condiciones.primitivas;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import modelo.condiciones.CondicionPrimitiva;
import modelo.condiciones.TipoCondicion;
import modelo.indicadores.predef.IndicadorLongevidad;
import modelo.operacionesMetodologia.MayorAValor;

@Entity
@DiscriminatorValue("LongevidadTaxativa")
public class LongevidadTaxativa extends CondicionPrimitiva {
	
    public LongevidadTaxativa() {
        super(new MayorAValor(new IndicadorLongevidad(), 10.0), 
                TipoCondicion.LONGEVIDAD_TAXATIVA.name());
    }
	
}

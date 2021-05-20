package modelo.condiciones.primitivas;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import modelo.condiciones.CondicionPrimitiva;
import modelo.condiciones.TipoCondicion;
import modelo.indicadores.predef.IndicadorMargen;
import modelo.operacionesMetodologia.Creciente;

@Entity
@DiscriminatorValue("MargenCC")
public class MargenCC extends CondicionPrimitiva {

    public MargenCC() {
        super(new Creciente(new IndicadorMargen()), 
                TipoCondicion.MARGEN_CC.name());
    }

    
}
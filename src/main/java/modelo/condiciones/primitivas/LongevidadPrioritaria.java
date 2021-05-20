package modelo.condiciones.primitivas;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import modelo.condiciones.CondicionPrimitiva;
import modelo.condiciones.TipoCondicion;
import modelo.indicadores.predef.IndicadorLongevidad;
import modelo.operacionesMetodologia.MayorAEmpresa;

@Entity
@DiscriminatorValue("LongevidadPrioritaria")
public class LongevidadPrioritaria extends CondicionPrimitiva {

    public LongevidadPrioritaria() {
        super(new MayorAEmpresa(new IndicadorLongevidad()), 
                TipoCondicion.LONGEVIDAD_PRIORITARIA.name());
    }

}

package modelo.condiciones.primitivas;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import modelo.condiciones.CondicionPrimitiva;
import modelo.condiciones.TipoCondicion;
import modelo.indicadores.predef.IndicadorROE;
import modelo.operacionesMetodologia.MayorAEmpresa;

@Entity
@DiscriminatorValue("MaximizarROE")
public class MaximizarROE extends CondicionPrimitiva {

    public MaximizarROE() {
        super(new MayorAEmpresa(new IndicadorROE()), 
                TipoCondicion.MAXIMIZAR_ROE.name());
    }

}
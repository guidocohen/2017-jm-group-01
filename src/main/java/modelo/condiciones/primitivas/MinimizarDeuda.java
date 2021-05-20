package modelo.condiciones.primitivas;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import modelo.condiciones.CondicionPrimitiva;
import modelo.condiciones.TipoCondicion;
import modelo.indicadores.predef.IndicadorProporcionDeDeuda;
import modelo.operacionesMetodologia.MayorAEmpresa;

@Entity
@DiscriminatorValue("MinimizarDeuda")
public class MinimizarDeuda extends CondicionPrimitiva {

    public MinimizarDeuda() {
        super(new MayorAEmpresa(new IndicadorProporcionDeDeuda()), 
                TipoCondicion.MINIMIZAR_DEUDA.name());
    }

	
}

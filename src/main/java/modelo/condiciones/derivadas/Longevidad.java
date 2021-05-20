package modelo.condiciones.derivadas;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import modelo.condiciones.CondicionDerivada;
import modelo.condiciones.TipoCondicion;
import modelo.condiciones.primitivas.LongevidadPrioritaria;
import modelo.condiciones.primitivas.LongevidadTaxativa;

@Entity
@DiscriminatorValue("Longevidad")
public class Longevidad extends CondicionDerivada {

    public Longevidad() {
        super(TipoCondicion.LONGEVIDAD.name(),
                new LongevidadPrioritaria(), new LongevidadTaxativa());
    }

}

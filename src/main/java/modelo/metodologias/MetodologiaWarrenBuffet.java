package modelo.metodologias;

import javax.persistence.Entity;
import modelo.condiciones.derivadas.Longevidad;
import modelo.condiciones.primitivas.*;

@Entity
public class MetodologiaWarrenBuffet extends Metodologia {

    public MetodologiaWarrenBuffet() {
        super("WARREN_BUFFET", 
                new MargenCC(), 
                new MaximizarROE(),
                new MinimizarDeuda(),
                new Longevidad());
    }

}
package modelo.condiciones;

import java.util.Arrays;
import java.util.List;
import modelo.Periodo;
import modelo.metodologias.RankingEmpresa;

public abstract class CondicionDerivada extends Condicion {

    private final List<Condicion> condiciones;

    public CondicionDerivada(String nombre, Condicion... condiciones) {
        super(nombre);
        this.condiciones = Arrays.asList(condiciones);
    }

    @Override
    public List<RankingEmpresa> evaluar(List<RankingEmpresa> rankings, Periodo periodo){
        condiciones.forEach(condicion-> condicion.evaluar(rankings, periodo));
        return rankings;
    }
    
    

}

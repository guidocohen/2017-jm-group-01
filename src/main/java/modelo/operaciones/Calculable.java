package modelo.operaciones;

import java.util.Set;
import modelo.Empresa;
import modelo.Periodo;


public interface Calculable {
	
	public double getValor(Empresa empresa, Periodo periodo);

        public Set<String> getDependencias();

}

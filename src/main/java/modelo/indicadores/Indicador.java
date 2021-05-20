package modelo.indicadores;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Set;

import modelo.Empresa;
import modelo.Periodo;

@JsonIgnoreProperties({ "changeSupport", "token_source", "token", "nextToken" })
public interface Indicador {
	public String getFormula();
	public String getNombre();
        public Set<String> getDependencias();
	public Double calcularPara(Empresa empresa, Periodo periodo);
}

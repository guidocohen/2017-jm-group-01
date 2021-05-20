package views.vm;

import modelo.Empresa;
import modelo.Periodo;
import modelo.indicadores.Indicador;
import modelo.indicadores.IndicadorUsuario;
import usuarios.Usuario;

import org.uqbar.commons.utils.Observable;

@Observable
public class IndicadorAMostrar {
	private String nombre;
	private String formula;
	private Double resultado;
	private Usuario usuario;
	
	public IndicadorAMostrar(IndicadorUsuario indicador, Empresa empresa, Periodo periodo) {
		nombre = indicador.getNombre();
		formula = indicador.getFormula();
		resultado = indicador.calcularPara(empresa, periodo);
		usuario = indicador.getUsuario();
	}
	
	public IndicadorAMostrar(Indicador indicador) {
		nombre = indicador.getNombre();
		formula = indicador.getFormula();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public Double getResultado() {
		return resultado;
	}

	public void setResultado(Double resultado) {
		this.resultado = resultado;
	}
	
	@Override
	public String toString(){
		return nombre;
	}
	
	public boolean delUsuario(long id){
		if(usuario == null)
			return false;
		return usuario.getId() == id;
	}

}

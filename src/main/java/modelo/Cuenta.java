package modelo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.uqbar.commons.utils.Observable;

@Observable
@Entity
@Table(name = "CUENTA")
public class Cuenta {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	@Column(name = "resultado")
	private int resultado;
	@Column(name = "tipo")
	private String tipo;
	@OneToOne (cascade = {CascadeType.ALL})
	@JoinColumn(name = "id_periodo")
	private Periodo periodo;

	public Cuenta(String tipo, int resultado, Periodo periodo){
		this.resultado = resultado;
		this.tipo = tipo;
		this.periodo = periodo;
	}
	
	public Cuenta(){ }
	
	
	
	public int getId() {
		return id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getResultado() {
		return resultado;
	}

	public void setResultado(int resultado) {
		this.resultado = resultado;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public boolean esDe(Periodo unPeriodo) {
		return this.getPeriodo().estaIncluidoEn(unPeriodo);
	}

}

package modelo.condiciones;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import modelo.Periodo;

import modelo.metodologias.RankingEmpresa;

@Entity
@Table(name = "CONDICION")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Condicion_tipo", discriminatorType = DiscriminatorType.STRING)
public abstract class Condicion {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;

	@Column(name = "nombre")
	private String nombre;

	public Condicion(String nombre) {
		this.nombre = nombre;
	}

	protected Condicion() { }

	public String getNombre() {
		return nombre;
	};

	public abstract List<RankingEmpresa> evaluar(List<RankingEmpresa> empresas, Periodo periodo);

}
package modelo;

import org.uqbar.commons.utils.Observable;
import org.uqbarproject.jpa.java8.extras.convert.LocalDateConverter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import excepciones.CantDeAniosInvalidaException;
import excepciones.PeriodoErroneoException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Observable
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "PERIODO")
public class Periodo {
	@Id
	@GeneratedValue
	@Column (name = "id")
    private int id;

	@Transient
	private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Convert (converter = LocalDateConverter.class)
	protected LocalDate desdeFecha;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Convert (converter = LocalDateConverter.class)
	protected LocalDate hastaFecha;

	public Periodo() {
	}

	public Periodo(String desdeFecha, String hastaFecha) { // Para tests
		this.desdeFecha = LocalDate.parse(desdeFecha, dateFormat);
		this.hastaFecha = LocalDate.parse(hastaFecha, dateFormat);
		validar();
	}

	public Periodo(String anio) {
		this.desdeFecha = LocalDate.parse("01/01/" + anio, dateFormat);
		this.hastaFecha = LocalDate.parse("31/12/" + anio, dateFormat);
		validar();
	}
	
	public Periodo(LocalDate desdeFecha, LocalDate hastaFecha) {
		this.desdeFecha = desdeFecha;
		this.hastaFecha = hastaFecha;
		validar();
	}
	
	public Periodo(int ultimosNAnios) {
		ultimosNAnios(ultimosNAnios);
		validar();
	}

	public LocalDate getDesdeFecha() {
		return desdeFecha;
	}

	public void setDesdeFecha(LocalDate desdeFecha) {
		this.desdeFecha = desdeFecha;
	}

	public LocalDate getHastaFecha() {
		return hastaFecha;
	}

	public void setHastaFecha(LocalDate hastaFecha) {
		this.hastaFecha = hastaFecha;
	}

    public boolean estaIncluidoEn(Periodo otroPeriodo) {
        return comenzoDespuesDe(otroPeriodo) && terminoAntesDe(otroPeriodo);
    }

    private boolean comenzoDespuesDe(Periodo otroPeriodo) {
        return desdeFecha.isAfter(otroPeriodo.getDesdeFecha())
                || desdeFecha.isEqual(otroPeriodo.getDesdeFecha());
    }

    private boolean terminoAntesDe(Periodo otroPeriodo) {
        return hastaFecha.isBefore(otroPeriodo.getHastaFecha())
                || hastaFecha.isEqual(otroPeriodo.getHastaFecha());
    }

    private void validar() throws PeriodoErroneoException {
        boolean esValido = desdeFecha.isBefore(hastaFecha);
        if (!esValido) {
            throw new PeriodoErroneoException();
        }
    }

	public boolean esPosteriorA(Periodo otroPeriodo) {
		return desdeFecha.isAfter(otroPeriodo.getDesdeFecha());
	}

	@Override
	public String toString() {
		return desdeFecha + "~" + hastaFecha;
	}

	public String anioDesdeFecha() {
		return String.valueOf(desdeFecha.getYear());
	}

	public String anioHastaFecha() {
		return String.valueOf(hastaFecha.getYear());
	}

	public void ultimosNAnios(int cantAnios) {
		// LocalDate.now().minus(1, ChronoUnit.YEARS);
		if (cantAnios == 0) throw new CantDeAniosInvalidaException();
		int anioAnterior = LocalDate.now().minusYears(1).getYear();
		int nAniosAnteriores = LocalDate.now().minusYears(cantAnios).getYear();
		hastaFecha = LocalDate.of(anioAnterior, 12, 31);
		desdeFecha = LocalDate.of(nAniosAnteriores, 01, 01);
	}

	public int cantDeAnios() {
		int diferenciaDeAnios = hastaFecha.getYear() - desdeFecha.getYear() + 1;
		if (comprendeTodoElPeriodo()) return diferenciaDeAnios;
		else return diferenciaDeAnios - 1;
	}

	public boolean comprendeTodoElPeriodo() {
		boolean desdePrimerDia = desdeFecha.getDayOfMonth() == 01 && desdeFecha.getMonthValue() == 01;
		boolean hastaUltimoDia = hastaFecha.getDayOfMonth() == 31 && hastaFecha.getMonthValue() == 12;
		return desdePrimerDia && hastaUltimoDia;
	}
	
	public Periodo desplazar1AnioMasReciente(){
		return new Periodo(desdeFecha.plusYears(1), hastaFecha.plusYears(1));
	}
}

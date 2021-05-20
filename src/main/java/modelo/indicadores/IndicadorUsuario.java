package modelo.indicadores;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import modelo.Empresa;
import modelo.Periodo;
import modelo.operaciones.Calculable;
import modelo.operaciones.ExpresionParser;
import usuarios.Usuario;

/**
 *
 * @author David
 */
@Entity
@Table(name = "INDICADOR")
public class IndicadorUsuario implements Indicador {

    private String nombre;
    private Calculable formula;
    private Usuario usuario;

    public IndicadorUsuario(String nombre, Calculable formula, Usuario usuario) {
        this.nombre = nombre;
        this.formula = formula;
        this.usuario = usuario;
    }

    public IndicadorUsuario(String nombre, String formula, Usuario usuario) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.formula = new ExpresionParser().interpretar(formula);
    }

    @JsonCreator
    public IndicadorUsuario(@JsonProperty("nombre") String nombre, @JsonProperty("formula") String formula) {
        this.nombre = nombre;
        this.formula = new ExpresionParser().interpretar(formula);
    }

    protected IndicadorUsuario() {
        //constructor vacio para Hibernate
        nombre = null;
        formula = null;
    }

    @Override
    @Basic
    public String getFormula() {
        return formula.toString();
    }

    private void setFormula(String formula) {
        this.formula = new ExpresionParser().interpretar(formula);
    }

    @Override
    @Id
    public String getNombre() {
        return nombre;
    }

    private void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Basic
    @ManyToOne
    public Usuario getUsuario() {
        return usuario;
    }

    private void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    @Transient
    public Set<String> getDependencias() {
        return formula.getDependencias();
    }

    public void setDependencias(Set<String> dependencias) {
        //jackson te odio
    }

    @Override
    public Double calcularPara(Empresa empresa, Periodo periodo) {
        return formula.getValor(empresa, periodo);
    }

    @Override
    public String toString() {
        return nombre;
    }

}

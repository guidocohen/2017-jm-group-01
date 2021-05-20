package modelo.condiciones;

import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

import modelo.Periodo;
import modelo.metodologias.RankingEmpresa;
import modelo.operacionesMetodologia.OperacionMetodologia;

public abstract class CondicionPrimitiva extends Condicion {

	@Transient
    private final OperacionMetodologia operacion;

    public CondicionPrimitiva(OperacionMetodologia operacion, String nombre) {
        super(nombre);
        this.operacion = operacion;
    }

    @Override
    public List<RankingEmpresa> evaluar(List<RankingEmpresa> rankings, Periodo periodo) {
        return operacion.operar(rankings, periodo);
    }

}

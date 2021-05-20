package ui.metodologia;

import java.util.List;
import modelo.Empresa;
import modelo.metodologias.Metodologia;

/**
 *
 * @author David
 */
public class MetodologiaDetalleVM {

    private final Metodologia metodologia;
    private final List<Empresa> empresas;

    public MetodologiaDetalleVM(Metodologia metodologia, List<Empresa> empresas) {
        this.metodologia = metodologia;
        this.empresas = empresas;
    }

    public Metodologia getMetodologia() {
        return metodologia;
    }

    public List<Empresa> getEmpresas() {
        return empresas;
    }

}

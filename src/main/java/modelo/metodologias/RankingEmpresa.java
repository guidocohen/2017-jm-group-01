package modelo.metodologias;

import java.util.Arrays;
import java.util.List;
import static java.util.stream.Collectors.toList;
import modelo.Empresa;

import org.uqbar.commons.utils.Observable;

@Observable
public class RankingEmpresa {

    private final Empresa empresa;
    private boolean esConvenienteInvertir = true;
    private int cantCondicionesOk = 0;
    private int sumadorDePosicion = 0;

    public static List<RankingEmpresa> fromEmpresas(List<Empresa> empresas) {
        return empresas.stream().map(e -> new RankingEmpresa(e)).collect(toList());
    }
    public static List<RankingEmpresa> fromEmpresas(Empresa... empresas) {
        return Arrays.stream(empresas).map(e -> new RankingEmpresa(e)).collect(toList());
    }

    public RankingEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public boolean getEsConvenienteInvertir() {
        return esConvenienteInvertir;
    }

    public void setNoValeLaPenaInvertir() {
        esConvenienteInvertir = false;
    }

    @Override
    public String toString() {
        return String.format("%s con %d cuentas {conviene? %s, pos: %d, oks: %d}",empresa.getNombre(),empresa.getCuentas().size(), esConvenienteInvertir,sumadorDePosicion,cantCondicionesOk);
    }

    public Integer getCantCondicionesOk() {
        return cantCondicionesOk;
    }

    public void sumarCantCondicionesOk() {
        cantCondicionesOk++;
    }

    public void sumarParaOrdenar(int valor) {
        this.sumadorDePosicion += valor;
    }

    public Integer getSumadorDePosicion() {
        return sumadorDePosicion;
    }
}

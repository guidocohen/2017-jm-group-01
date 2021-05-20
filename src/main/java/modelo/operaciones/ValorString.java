package modelo.operaciones;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import modelo.Empresa;
import modelo.Periodo;
import modelo.indicadores.Indicador;
import modelo.repositorios.RepoDeIndicadores;
import modelo.repositorios.persistencia.RepoDeIndicadoresPersistencia;

public class ValorString implements Calculable {

    private final String nombreCuentaOIndicador;

    public ValorString(String nombreCuentaOIndicador) {
        this.nombreCuentaOIndicador = nombreCuentaOIndicador;
    }

    @Override
    public double getValor(Empresa empresa, Periodo periodo) {
        RepoDeIndicadores indicadores = RepoDeIndicadoresPersistencia.getInstance();
        
        Indicador indicador = indicadores.getIndicadorPorNombre(nombreCuentaOIndicador);
        
        if(indicador!=null){
            return indicador.calcularPara(empresa, periodo);
        }
        return empresa.obtenerResultadoDeCuenta(nombreCuentaOIndicador, periodo);
    }

    @Override
    public String toString() {
        return nombreCuentaOIndicador;
    }

    @Override
    public Set<String> getDependencias() {
        return Stream.of(nombreCuentaOIndicador).collect(Collectors.toSet());
    }

}

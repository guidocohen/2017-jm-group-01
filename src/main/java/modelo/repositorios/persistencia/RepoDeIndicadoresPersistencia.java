package modelo.repositorios.persistencia;

import carga.CargadorIndicadores;
import excepciones.FiltrarPorNombreException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.NoResultException;

import javax.persistence.TypedQuery;
import modelo.indicadores.GrafoIndicadores;
import modelo.indicadores.Indicador;
import modelo.indicadores.TipoIndicador;
import modelo.indicadores.predef.IndicadorCapitalTotal;
import modelo.indicadores.predef.IndicadorDividendos;
import modelo.indicadores.predef.IndicadorIngresoNeto;
import modelo.indicadores.predef.IndicadorLongevidad;
import modelo.indicadores.predef.IndicadorMargen;
import modelo.indicadores.predef.IndicadorProporcionDeDeuda;
import modelo.indicadores.predef.IndicadorROE;
import modelo.repositorios.RepoDeIndicadores;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class RepoDeIndicadoresPersistencia extends RepoPersistencia implements RepoDeIndicadores, WithGlobalEntityManager {

    private static RepoDeIndicadoresPersistencia instance;

    private GrafoIndicadores grafo;
    private Map<String,Indicador> indicadoresPredefinidos;

    private RepoDeIndicadoresPersistencia() {
        grafo = new GrafoIndicadores();
        indicadoresPredefinidos = Stream.of(
                new IndicadorCapitalTotal(),
                new IndicadorDividendos(),
                new IndicadorIngresoNeto(),
                new IndicadorLongevidad(),
                new IndicadorMargen(),
                new IndicadorProporcionDeDeuda(),
                new IndicadorROE()
        ).collect(Collectors.toMap(
                indicador -> indicador.getNombre().toLowerCase(),
                indicador -> indicador
        ));
        
    }

    public static RepoDeIndicadoresPersistencia getInstance() {
        if (instance == null) {
            instance = new RepoDeIndicadoresPersistencia();
        }
        return instance;
    }

    @Override
    public void agregar(Indicador indicador) {
        entityManager().persist(indicador);
        grafo.add(indicador);
    }

    @Override
    public void eliminar(Indicador indicador) {
        grafo.remove(indicador);
    }

    @Override
    public void cargarIndicadoresDefinidos(CargadorIndicadores cargador) throws IOException, FiltrarPorNombreException {
        cargador.cargarIndicadores().forEach(
                (k, v) -> {
                    entityManager().persist(v);
                    grafo.add(v);
                }
        );
    }

    public Indicador getById(long id) {
        return entityManager().find(Indicador.class, id);
    }

    @Override
    public Collection<Indicador> getAll() {
        Collection<Indicador> allIndicadores = getDefinidos();
        allIndicadores.addAll(getIndicadoresPredefinidos());
        return allIndicadores;
    }

    @Override
    public Collection<Indicador> getDefinidos() {
        return entityManager().createQuery("from IndicadorUsuario", Indicador.class).getResultList();
    }

    @Override
    public Indicador getIndicadorPorNombre(String nombreIndicador) {
        try {
            return obtenerIndicador(nombreIndicador);
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Map<String, Indicador> getIndicadores() {
        return getAll()
                .stream()
                .collect(Collectors.toMap(Indicador::getNombre, Function.identity()));
    }

    @Override
    public Map<String, Indicador> getIndicadoresDefinidos() {
        return getDefinidos()
                .stream()
                .collect(Collectors.toMap(Indicador::getNombre, Function.identity()));
    }

    @Override
    public void limpiarIndicadores() {
        entityManager().clear();
        grafo = new GrafoIndicadores();
    }

    @Override
    public Indicador obtenerIndicador(String nombre) {
        if(indicadoresPredefinidos.containsKey(nombre.toLowerCase())){
            return indicadoresPredefinidos.get(nombre.toLowerCase());
        }
        TypedQuery<Indicador> query = entityManager().createQuery("from IndicadorUsuario where lower(nombre) like :nombreIndicador", Indicador.class);
        query.setParameter("nombreIndicador", nombre.toLowerCase());
        return query.getSingleResult();
    }

    @Override
    public void setIndicadoresPredefinidos() {
        Arrays.asList(TipoIndicador.values()).stream()
                .map(tipoIndicador -> tipoIndicador.getIndicador())
                .forEach(indicador -> this.agregar(indicador));
    }

    public void eliminarTodosLosIndicadores() {
        entityManager().createQuery("delete from IndicadorUsuario").executeUpdate();
        grafo = new GrafoIndicadores();
    }

    @Override
    public Collection<Indicador> getIndicadoresPredefinidos() {
        return indicadoresPredefinidos.values();
    }

}

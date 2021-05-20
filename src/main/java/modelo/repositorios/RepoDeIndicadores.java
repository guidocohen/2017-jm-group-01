/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.repositorios;

import carga.CargadorIndicadores;
import excepciones.FiltrarPorNombreException;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import modelo.indicadores.Indicador;

/**
 *
 * @author David
 */
public interface RepoDeIndicadores {

    public void agregar(Indicador indicador);

    public void cargarIndicadoresDefinidos(CargadorIndicadores cargador) throws IOException, FiltrarPorNombreException;

    public Collection<Indicador> getAll();

    public Collection<Indicador> getDefinidos();

    public Indicador getIndicadorPorNombre(String nombreIndicador);

    public Map<String, Indicador> getIndicadores();

    public Map<String, Indicador> getIndicadoresDefinidos();

    public void limpiarIndicadores();

    public Indicador obtenerIndicador(String nombre);

    public void setIndicadoresPredefinidos();

    public void eliminar(Indicador indicador);

	public Collection<Indicador> getIndicadoresPredefinidos();

}

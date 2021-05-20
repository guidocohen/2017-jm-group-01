package carga;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonMappingException;

import modelo.indicadores.Indicador;

public interface CargadorIndicadores {	
	public Map<String, Indicador> cargar() throws IOException, JsonMappingException;
    public void guardar(Map<String, Indicador> indicadores) throws IOException;
	public Map<String, Indicador> cargarIndicadores() throws JsonMappingException, IOException;

}

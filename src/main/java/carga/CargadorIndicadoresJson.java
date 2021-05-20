package carga;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;

import excepciones.JsonException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import modelo.indicadores.Indicador;
import modelo.indicadores.IndicadorUsuario;

public class CargadorIndicadoresJson implements CargadorIndicadores {

	private File archivo;
	private ObjectMapper mapper;
	private TypeReference<?> typeRef;

	public CargadorIndicadoresJson(File archivo) {
		this.archivo = archivo;
		typeRef = new TypeReference<Map<String, Indicador>>() { };

		mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

		registrarModuloInterfaz();

	}

	@Override
	public Map<String,Indicador> cargar() throws IOException, JsonMappingException {
		try {
			return mapper.readValue(archivo, typeRef);
		} catch (JsonMappingException e) {
			throw new JsonException(e);
		}
	}

	@Override
	public void guardar(Map<String, Indicador> indicadores) throws IOException {
		try {
			mapper.writeValue(archivo, indicadores);
		} catch (IOException e) {
			throw new RuntimeException("Error al guardar indicadores", e);
		}
	}
	
	protected void registrarModuloInterfaz(){
		SimpleModule module = new SimpleModule("IndicadorUsuario", Version.unknownVersion());
		SimpleAbstractTypeResolver resolver = new SimpleAbstractTypeResolver();
		
		resolver.addMapping(Indicador.class, IndicadorUsuario.class);		

		module.setAbstractTypes(resolver);
		mapper.registerModule(module);
	}
	
	public Map<String, Indicador> cargarIndicadores() throws JsonMappingException, IOException{
		Map<String, Indicador> indicadoresCargados = new HashMap<String, Indicador>();
		for (Indicador indicador : cargar().values()) {
			indicadoresCargados.put(indicador.getNombre(),indicador);
		}
		return indicadoresCargados;
	}
}

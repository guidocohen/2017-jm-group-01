package carga;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import excepciones.JsonException;

import java.io.File;
import java.util.List;
import modelo.Empresa;

@SuppressWarnings("rawtypes")
public class CargadorEmpresasJson implements CargadorEmpresas {

	private final File archivo;
	private final ObjectMapper mapper;
	private final TypeReference typeRef;

	CargadorEmpresasJson(File archivo) {
		this.archivo = archivo;
		typeRef = new TypeReference<List<Empresa>>() { };

		mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}

	@Override
	public List<Empresa> cargar() throws IOException, JsonMappingException {
		List<Empresa> empresas;
		try {
			empresas = mapper.readValue(archivo, typeRef);
		} catch (JsonMappingException e) {
			throw new JsonException();
		}
		return empresas;
	}

	@Override
	public void guardar(List<Empresa> lista) throws IOException {
		try {
			mapper.writeValue(archivo, lista);
		} catch (IOException e) {
			throw new RuntimeException("Error al escribir el JSON en el archivo writeTest.json", e);
		}
	}

}


package carga;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.JsonMappingException;

import modelo.Empresa;

public interface CargadorEmpresas {
    public List<Empresa> cargar() throws IOException, JsonMappingException;
    public void guardar(List<Empresa> empresas) throws IOException;
}

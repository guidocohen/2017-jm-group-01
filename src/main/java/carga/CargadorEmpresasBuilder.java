package carga;

import java.io.File;

import excepciones.FormatoNoSoportadoException;

public class CargadorEmpresasBuilder {
    private File archivo;
    
    public CargadorEmpresasBuilder conArchivo(File archivo){
        this.archivo = archivo;
        return this;
    }
    
    public CargadorEmpresas paraFormatoJson(){
        return new CargadorEmpresasJson(archivo);
    }
    
    public CargadorEmpresas paraFormatoXml(){
        return new CargadorEmpresasXml(archivo);
    }
    
    public CargadorEmpresas paraFormatoAdecuado() {
        if(archivo.getName().endsWith(".xml")){
            return paraFormatoXml();
        }
        if(archivo.getName().endsWith(".json")){
            return paraFormatoJson();
        }
        throw new FormatoNoSoportadoException();
    }
}

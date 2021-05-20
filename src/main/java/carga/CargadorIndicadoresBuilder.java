package carga;

import java.io.File;

import excepciones.FormatoNoSoportadoException;

public class CargadorIndicadoresBuilder {
    private File archivo;
    
    public CargadorIndicadoresBuilder conArchivo(File archivo){
        this.archivo = archivo;
        return this;
    }
    
    public CargadorIndicadores paraFormatoJson(){
        return new CargadorIndicadoresJson(archivo);
    }
    
    
    public CargadorIndicadores paraFormatoAdecuado() {
        //if(archivo.getName().endsWith(".xml")){
        //    return paraFormatoXml();
        //}
        if(archivo.getName().endsWith(".json")){
            return paraFormatoJson();
        }
        throw new FormatoNoSoportadoException();
    }
}

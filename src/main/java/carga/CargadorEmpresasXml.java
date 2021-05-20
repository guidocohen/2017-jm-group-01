package carga;

import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import modelo.Cuenta;
import modelo.Empresa;
import modelo.Periodo;

@SuppressWarnings("unchecked")
public class CargadorEmpresasXml implements CargadorEmpresas {

    private final File archivo;
    private final XStream xstream;

    CargadorEmpresasXml(File archivo) {
        this.archivo = archivo;
        xstream = new XStream();
        //xstream.registerConverter(new LocalTimeConverter());
        xstream.alias("empresa", Empresa.class);
        xstream.alias("cuenta", Cuenta.class);
        xstream.alias("periodo", Periodo.class);
        xstream.omitField(Periodo.class, "dateFormat");
    }

	@Override
    public List<Empresa> cargar() throws IOException {
        return (List<Empresa>) xstream.fromXML(archivo);
    }

    @Override
    public void guardar(List<Empresa> empresas) throws IOException {
        try (FileWriter writer = new FileWriter(archivo)) {
            xstream.toXML(new ArrayList<>(empresas), writer);
        }
    }

}

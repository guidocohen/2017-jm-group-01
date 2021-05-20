package ui;

import carga.CargadorEmpresasBuilder;
import java.io.File;
import java.io.IOException;
import modelo.metodologias.MetodologiaWarrenBuffet;
import modelo.repositorios.persistencia.RepoDeEmpresasPersistencia;
import modelo.repositorios.persistencia.RepoDeMetodologiasPersistencia;
import usuarios.RepoDeUsuarios;
import usuarios.Usuario;

/**
 *
 * @author David
 */
public class CargaInicialDeDatos {

    public static void main(String[] args) throws IOException {
        RepoDeUsuarios usuarios = RepoDeUsuarios.getInstance();
        usuarios.iniciar();
        usuarios.eliminarTodosLosUsuarios();
        usuarios.agregarUsuario(new Usuario("hernan","hernan"));
        usuarios.agregarUsuario(new Usuario("anabel","anabel"));
        usuarios.confirmar();

        RepoDeEmpresasPersistencia empresas = RepoDeEmpresasPersistencia.getInstancia();
        File archivoEmpresas = new File("src/test/resources/empresas.json");
        empresas.iniciar();
        empresas.eliminarTodasLasEmpresas();
        empresas.cargarEmpresas(new CargadorEmpresasBuilder().conArchivo(archivoEmpresas).paraFormatoAdecuado());
        empresas.confirmar();
        
        RepoDeMetodologiasPersistencia metodologias = RepoDeMetodologiasPersistencia.getInstancia();
        metodologias.iniciar();
        metodologias.eliminarMetodologias();
        metodologias.agregar(new MetodologiaWarrenBuffet());
        metodologias.confirmar();

        System.out.println("TODO BIEN!");
    }
}

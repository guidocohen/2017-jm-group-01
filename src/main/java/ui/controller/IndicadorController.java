package ui.controller;

import modelo.indicadores.Indicador;
import modelo.indicadores.IndicadorUsuario;
import modelo.repositorios.*;
import modelo.repositorios.archivos.RepoDeIndicadoresArchivo;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import ui.Router;
import ui.viewModel.IndicadorVM;
import usuarios.Usuario;

public class IndicadorController {
	private static IndicadorVM indicadorVM = new IndicadorVM();

	public static ModelAndView lista(Request req, Response res) {
        //TODO: Deberia pasarle el ID de USUARIO (de la sesion)
		//Usuario usuario = Router.obtenerUsuarioDeLaSesion(req.session());
		//indicadorVM.buscarIndicadores(usuario.getId());
		indicadorVM.buscarIndicadores();
		return new ModelAndView(indicadorVM, "/Indicadores/index.hbs");
	}

    public static ModelAndView detalle(Request req, Response res) {
    	//Long id = Long.valueOf(req.params("id"));
    	//Indicador indicador = RepoDeIndicadoresPersistencia.getInstance().getById(id);

		String nombre = req.params("nombre");
		Indicador indicador = RepoDeIndicadoresArchivo.getInstancia().obtenerIndicador(nombre);
		indicadorVM.adaptar(indicador);

		return new ModelAndView(indicadorVM, "/Indicadores/detalle.hbs");
	}

    public static ModelAndView evaluar(Request req, Response res) {
    	//Long id = Long.valueOf(req.params("id"));
    	//Indicador indicador = RepoDeIndicadoresPersistencia.getInstance().getById(id);

		String nombre = req.params("nombre");
		String empresa = req.queryParams("empresa");
		String anio = req.queryParams("anio");

		indicadorVM.evaluar(nombre, empresa, anio);

		return new ModelAndView(indicadorVM, "/Indicadores/detalle.hbs");
	}

	public static ModelAndView agregarIndicador(Request req, Response res) {
		return new ModelAndView(null, "/Indicadores/new.hbs");
	}

	public static ModelAndView altaIndicador(Request req, Response res) {
		String nombre = req.queryParams("nombre");
		String formula = req.queryParams("formula");

        //TODO: aca deberia obtener el usuario de la sesion y guardarlo con el indicador
		//Usuario usuario = Router.obtenerUsuarioDeLaSesion(req.session());
		indicadorVM.altaIndicador(new IndicadorUsuario(nombre, formula, new Usuario("paco", "paco")));
		res.redirect("/Indicadores");

		return new ModelAndView(indicadorVM, "/Indicadores/new.hbs");
	}

	public static ModelAndView bajaIndicador(Request req, Response res) {
		//long id = Integer.parseInt(req.queryParams("id"));
		// RepoDeIndicadoresPersistencia.getInstance().borrarIndicador(id);
		//indicadorVM.bajaIndicador(indicador);
		String nombre = req.params("nombre");
		Indicador indicador = RepoDeIndicadoresArchivo.getInstancia().obtenerIndicador(nombre);

		indicadorVM.bajaIndicador(indicador);

		return new ModelAndView(indicadorVM, "/Indicadores/baja.hbs");
	}
}

package ui.controller;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import ui.Router;
import usuarios.RepoDeUsuarios;
import usuarios.Usuario;

public class LogInController {

	public static ModelAndView mostrar(Request req, Response res) {

		return new ModelAndView(null, "LogIn.hbs");
	}

	public static ModelAndView loginError(Request req, Response res) {
		String mensajeCodigo = req.params("mensaje");

		String mensaje = mensajeCodigo.replace("%20", " ");

		Map<String, String> model = new HashMap<>();

		model.put("excepcion", mensaje);

		return new ModelAndView(model, "loginError.hbs");
	}

	public static Void logear(Request req, Response res) {
		
		RepoDeUsuarios repositorio = RepoDeUsuarios.getInstance();
		
		String ruta = req.body();
		
		String parametros[] = ruta.split("&");
		
		String usuario;
		
		String password;
		
		if (parametros[0].split("=").length < 2)
			usuario = "";
		else
			usuario = parametros[0].split("=")[1];
		if (parametros[1].split("=").length < 2)
			password = "=";
		else
			password = parametros[1].split("=")[1];
		try {
			
			Usuario unUsuario = repositorio.loginExitoso(usuario, password);
			Router.iniciarSesion(req.session().id(), unUsuario);
			Map<String, Usuario> model = new HashMap<>();
			model.put("usuario", unUsuario);
			res.redirect("/home");
		} 
		catch (Exception e) {
			
			res.redirect("/loginError/" + e.getMessage());
		}
		return null;
	}
	
	

}

package ui.controller;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class CondicionController {
	
	public static ModelAndView mostrar(Request req, Response res) {
		return new ModelAndView(null, "Condiciones.hbs");
	}

}

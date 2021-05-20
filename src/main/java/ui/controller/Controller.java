package ui.controller;

import java.io.IOException;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class Controller {
	
	public static ModelAndView getPaginaPrincipal(Request req, Response res) throws IOException {
		return new ModelAndView(null, "index.hbs");
	}
	
}

package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import spark.Session;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import ui.HandlebarsTemplateEngineBuilder;
import ui.controller.*;
import ui.metodologia.MetodologiaController;
import usuarios.Usuario;

public class Router {

	private static List<Sesion> sesiones = new ArrayList<>();

	public static void configure() {
		HandlebarsTemplateEngine engine = HandlebarsTemplateEngineBuilder
				.create()
				.withDefaultHelpers()
				.build();
		
		Spark.staticFiles.location("/public");

		Spark.get("/home", Controller::getPaginaPrincipal, engine);
		Spark.get("/", LogInController::mostrar, engine);
		Spark.post("/login/", LogInController::logear);
		Spark.get("/loginError/:mensaje", LogInController::loginError, engine);

		Spark.get("/Empresas", EmpresaController::lista, engine);
		Spark.get("/Empresas/Cuentas", EmpresaController::mostrarCuentas, engine);

		//	INDICADORES
		Spark.get("/Indicadores", IndicadorController::lista, engine);
		Spark.get("/Indicadores/new", IndicadorController::agregarIndicador, engine);
		Spark.post("/Indicadores", IndicadorController::altaIndicador, engine);
		Spark.get("/Indicadores/:nombre", IndicadorController::detalle, engine);
		Spark.get("/Indicadores/:nombre/evaluar", IndicadorController::evaluar, engine);
		Spark.post("/Indicadores/:nombre/method=DELETE", IndicadorController::bajaIndicador, engine);

		Spark.get("/LogIn", LogInController::mostrar, engine);
		Spark.get("/Condiciones", CondicionController::mostrar, engine);

        /* Metodologias */
        MetodologiaController metodologiasController = new MetodologiaController();
        Spark.get("/metodologias", metodologiasController::lista, engine);
        Spark.get("/metodologias/:idMetodologia", metodologiasController::detalle, engine);
        Spark.get("/metodologias/:idMetodologia/evaluar", metodologiasController::evaluar, engine);
	}

	public static Usuario obtenerUsuarioDeLaSesion(Session session) {
		return sesiones.stream().filter(unaSesion -> unaSesion.getId().equals(session.id())).collect(Collectors.toList()).get(0).getUsuario();
	}
	
	public static void iniciarSesion(String idSesion, Usuario unUsuario) {
		if (sesiones.stream().anyMatch(unaSesion -> unaSesion.getId().equals(idSesion)))
			sesiones.stream().filter(unaSesion -> unaSesion.getId().equals(idSesion)).collect(Collectors.toList()).get(0).setUsuario(unUsuario);
		else
			sesiones.add(new Sesion(idSesion, unUsuario));
	}
}

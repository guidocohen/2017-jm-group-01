package ui.controller;

import java.io.IOException;

import modelo.repositorios.persistencia.RepoDeEmpresasPersistencia;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import ui.viewModel.EmpresaVM;

public class EmpresaController {
	private static EmpresaVM empresaVM = new EmpresaVM();

	public static ModelAndView lista(Request req, Response res) throws IOException {
		empresaVM.cargarEmpresasBatch();

		return new ModelAndView(empresaVM, "Empresas.hbs");
	}

	public static ModelAndView mostrarCuentas(Request req, Response res) {

		String empresa = req.queryParams("empresa");

		String anio = req.queryParams("anio");
		
		empresaVM.setEmpresas(RepoDeEmpresasPersistencia.getInstancia().getEmpresas());

		empresaVM.setEmpresaSeleccionada(empresaVM.buscarEmpresa(empresa));

		empresaVM.setAnio(Integer.valueOf(anio));

		empresaVM.buscarCuentasEmpresa();
		return new ModelAndView(empresaVM, "Empresas.hbs");

	}

    }


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;

import modelo.*;
import modelo.indicadores.*;
import modelo.indicadores.predef.*;
import modelo.repositorios.*;
import modelo.repositorios.archivos.RepoDeEmpresasArchivo;
import modelo.repositorios.archivos.RepoDeIndicadoresArchivo;

import views.vm.MainViewModel;

public class Fixture {
	protected Empresa facebook, google, apple, intel, unaEmpresa;
	protected Cuenta cuentaValida1, cuentaValida2, cuentaInvalida1;
	protected RepoDeEmpresasArchivo repoDeEmpresas;
	protected RepoDeIndicadores repoDeIndicadores;
	protected Periodo anio2016, anio2015, anio2014, periodoValidoCorto, periodoValidoLargo, periodoInvalido1;
	protected List<Cuenta> cuentasVacia, cuentas;
	protected MainViewModel empresaModel;
	protected final String directorioArchivos = "./src/test/resources/";
	protected String nombreDeArchivoEmpresas = "empresas";
	protected String nombreDeArchivoIndicadores = "indicadoresTest";
	protected List<Empresa> empresas, empresas2;
	protected Indicador indicadorRoe,indicadorIngresoNeto,indicadorDividendos,indicadorCapitalTotal,indicadorPersonalizado;
	
	@Before
	public void fixture() {
		facebook = facebook();
		google = google();
		apple = apple();
		intel = intel();
		cuentaValida1 = new Cuenta();
		cuentaValida2 = new Cuenta();
		cuentaInvalida1 = new Cuenta();
		cuentasVacia = new ArrayList<>();
		repoDeEmpresas = RepoDeEmpresasArchivo.getInstancia();
		anio2016 = todoAnio("2016");
		anio2015 = todoAnio("2015");
		anio2014 = todoAnio("2014");
		periodoValidoCorto = new Periodo("01/01/2014", "30/04/2014");
		periodoValidoLargo = new Periodo("01/01/2013", "31/12/2016");
		
		cuentaValida1.setPeriodo(anio2015);
		cuentaValida2.setPeriodo(anio2014);
		repoDeEmpresas.getEmpresas().add(facebook);
		repoDeEmpresas.getEmpresas().add(google);

		empresaModel = new MainViewModel();

		empresas = Arrays.asList(facebook,google);
		empresas2 = Arrays.asList(apple,intel);

		indicadorRoe = new IndicadorROE();
		indicadorIngresoNeto = new IndicadorIngresoNeto();
		indicadorDividendos = new IndicadorDividendos();
		indicadorCapitalTotal = new IndicadorCapitalTotal();
		repoDeIndicadores = RepoDeIndicadoresArchivo.getInstancia();
		repoDeIndicadores.setIndicadoresPredefinidos();
		indicadorPersonalizado = new IndicadorUsuario("RIE", "EBITDA+3");
		repoDeIndicadores.agregar(indicadorPersonalizado);

	}

	// facebook segun el empresas.json
	public Empresa facebook(){

		Empresa empresaFacebook = new Empresa("Facebook");
		empresaFacebook.setEdad(13);
		empresaFacebook.setCuentas(cuentasEstandar(8162, 1487, 4273, 0, 169));

		return empresaFacebook;
	}

	// google segun el empresas.json
	public Empresa google(){
		Empresa empresaGoogle = new Empresa("Google");

		empresaGoogle.setEdad(19);
		empresaGoogle.setCuentas(cuentasEstandar(24818, 30418, 3380, 200, 130));

		return empresaGoogle;
	}

	// apple segun empresas2.json
	public Empresa apple(){
		Empresa empresaApple = new Empresa("Apple");

		List<Cuenta> cuentasApple = new ArrayList<>();
		cuentasApple.add(new Cuenta(TipoCuenta.EBITDA.name(), 84505, todoAnio("2015")));
		cuentasApple.add(new Cuenta(TipoCuenta.EBITDA.name(), 73333, todoAnio("2016")));
		cuentasApple.add(new Cuenta(TipoCuenta.INO_CONTINUAS.name(), 21120, todoAnio("2015")));
		cuentasApple.add(new Cuenta(TipoCuenta.FREE_CASH_FLOW.name(), 13844, todoAnio("2014")));
		cuentasApple.add(new Cuenta(TipoCuenta.FREE_CASH_FLOW.name(), 53394, todoAnio("2015")));

		empresaApple.setEdad(41);
		empresaApple.setCuentas(cuentasApple);

		return empresaApple;
	}

	// intel segun empresas2.json
	public Empresa intel(){
		Empresa empresaIntel = new Empresa("Intel");

                List<Cuenta> cuentasIntel = new ArrayList<>();
		cuentasIntel.add(new Cuenta(TipoCuenta.EBITDA.name(), 21459, todoAnio("2016")));
		cuentasIntel.add(new Cuenta(TipoCuenta.FREE_CASH_FLOW.name(), 12183, todoAnio("2015")));
		cuentasIntel.add(new Cuenta(TipoCuenta.INO_CONTINUAS.name(), 3562, todoAnio("2016")));
		cuentasIntel.add(new Cuenta(TipoCuenta.INO_DISCONTINUAS.name(), 110, todoAnio("2016")));

		empresaIntel.setEdad(49);
		empresaIntel.setCuentas(cuentasIntel);

		return empresaIntel;
	}

	public Periodo todoAnio(String anio){
		return new Periodo(anio);
	}

	// este metodo sirve porque Google y Facebook tienen las mismas cuentas (con otros resultados)
	// cuentasEstandar son: EBITDA 2015, EBITDA 2016, INO_CONT e INO_DISCONT ambas 2016
	public List<Cuenta> cuentasEstandar(int ebitda_15, int ebitda_16, int ino_cont, int ino_disc, int fds){
		List<Cuenta> cuentasEstandar = new ArrayList<>();
		cuentasEstandar.add(new Cuenta(TipoCuenta.EBITDA.name(), ebitda_15, todoAnio("2015")));
		cuentasEstandar.add(new Cuenta(TipoCuenta.EBITDA.name(), ebitda_16, todoAnio("2016")));
		cuentasEstandar.add(new Cuenta(TipoCuenta.INO_CONTINUAS.name(), ino_cont, todoAnio("2016")));
		cuentasEstandar.add(new Cuenta(TipoCuenta.INO_DISCONTINUAS.name(), ino_disc, todoAnio("2016")));
		cuentasEstandar.add(new Cuenta(TipoCuenta.FDS.name(), fds, todoAnio("2016")));

		return cuentasEstandar;
	}

}

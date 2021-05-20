
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonMappingException;

import java.util.Arrays;
import modelo.Cuenta;
import modelo.Empresa;
import modelo.metodologias.RankingEmpresa;
import modelo.Periodo;
import modelo.condiciones.*;
import modelo.condiciones.derivadas.*;
import modelo.condiciones.primitivas.*;
import modelo.indicadores.Indicador;
import modelo.indicadores.predef.*;
import modelo.metodologias.*;
import modelo.operacionesIndicador.*;
import org.junit.After;

public class MetodologiaYCondicionTest {

    public Metodologia metodologiaWarrenBuffet;
    public Periodo ultimos3Anios;
    public Periodo anio2015;
    public Empresa google, apple, intel, facebook;
    public List<Empresa> empresasEvaluadas;

    @Before
    public void setUp() throws JsonMappingException, IOException {
        Fixture fixture = new Fixture();
        google = fixture.google();
        apple = fixture.apple();
        intel = fixture.intel();
        facebook = fixture.facebook();
        apple.getCuentas().add(new Cuenta("FDS", 100, new Periodo("13/06/2016", "14/07/2016")));
        apple.getCuentas().add(new Cuenta("INO_DISCONTINUAS", 2814, new Periodo("01/02/2014", "01/06/2014")));
        intel.getCuentas().add(new Cuenta("FDS", 130, new Periodo("01/09/2015", "30/09/2015")));
        facebook.getCuentas().add(new Cuenta("INO_CONTINUAS", 1234, new Periodo("01/01/2010", "31/06/2010")));
        facebook.getCuentas().add(new Cuenta("INO_CONTINUAS", 1234, new Periodo("01/01/2011", "31/06/2011")));
        facebook.getCuentas().add(new Cuenta("INO_CONTINUAS", 1234, new Periodo("01/01/2012", "31/06/2012")));
        facebook.getCuentas().add(new Cuenta("INO_CONTINUAS", 1234, new Periodo("01/01/2013", "31/06/2013")));
        facebook.getCuentas().add(new Cuenta("INO_DISCONTINUAS", 3030, new Periodo("01/07/2010", "31/12/2010")));
        facebook.getCuentas().add(new Cuenta("INO_DISCONTINUAS", 3300, new Periodo("01/07/2011", "31/12/2011")));
        facebook.getCuentas().add(new Cuenta("INO_DISCONTINUAS", 3300, new Periodo("01/07/2012", "31/12/2012")));
        facebook.getCuentas().add(new Cuenta("INO_DISCONTINUAS", 3300, new Periodo("01/07/2013", "31/12/2013")));
        empresasEvaluadas = Arrays.asList(google, apple, intel, facebook);
        metodologiaWarrenBuffet = new MetodologiaWarrenBuffet();
        ultimos3Anios = new Periodo(3);
        anio2015 = new Periodo("2015");
    }

    @Test
    public void testEvaluarMetodologiaWarrenBuffet() throws IOException {
        List<RankingEmpresa> empresasRankeadas = metodologiaWarrenBuffet.evaluar(empresasEvaluadas, ultimos3Anios);
        System.out.println(empresasRankeadas);
        assertEquals(apple, empresasRankeadas.get(0).getEmpresa());
        assertEquals(facebook, empresasRankeadas.get(1).getEmpresa());
        assertEquals(intel, empresasRankeadas.get(2).getEmpresa());
        assertEquals(google, empresasRankeadas.get(3).getEmpresa());
    }

    private void esLaMejor(List<RankingEmpresa> emps, Condicion condicion, Empresa laMejor) {
        condicion.evaluar(emps, ultimos3Anios);
        assertEquals(laMejor, emps.get(0).getEmpresa());
    }

    @Test // Facebook tiene mejor ROE
    public void testEvaluarCondicionMaximizarROE_FacebookEsMejor() {
        esLaMejor(
                RankingEmpresa.fromEmpresas(google, facebook),
                new MaximizarROE(),
                google
        );
    }

    @Test
    public void testEvaluarCondicionMaximizarROEParaEmpresas_AppleEsMejor() throws JsonMappingException, IOException {
        esLaMejor(
                RankingEmpresa.fromEmpresas(facebook, intel, google, apple),
                new MaximizarROE(),
                facebook
        );
    }

    @Test // En ambas vale la pena invertir (>10anios) y Google es mas antigua
    public void testEvaluarCondicionLongevidad_GoogleEsMejor() {
        esLaMejor(
                RankingEmpresa.fromEmpresas(facebook, google),
                new Longevidad(),
                facebook
        );
    }

    @Test
    public void testEvaluarCondicionMargenCC() {
        List<RankingEmpresa> emprsas = RankingEmpresa.fromEmpresas(facebook);
        new MargenCC().evaluar(emprsas, ultimos3Anios);
        assertTrue(emprsas.get(0).getEsConvenienteInvertir());
    }

    @Test
    public void testEvaluarCondicionMinimizarDeuda() {

        esLaMejor(
                RankingEmpresa.fromEmpresas(facebook, apple),
                new MinimizarDeuda(),
                facebook
        );
    }

    @Test // En ambas vale la pena invertir (>10anios) e Intel es mas antigua
    public void testEvaluarCondicionLongevidad_IntelEsMejor() {
        esLaMejor(
                RankingEmpresa.fromEmpresas(intel, apple),
                new Longevidad(),
                intel
        );
    }

    @Test
    public void testEvaluarCondicionLongevidadPrioritaria() {
        esLaMejor(
                RankingEmpresa.fromEmpresas(facebook, apple),
                new LongevidadPrioritaria(),
                facebook
        );
    }

    @Test
    public void testEvaluarCondicionLongevidadPrioritariaParaEmpresas() throws JsonMappingException, IOException {
        esLaMejor(
                RankingEmpresa.fromEmpresas(intel, apple, google, facebook),
                new LongevidadPrioritaria(),
                intel
        );

    }

    @Test
    public void testEvaluarCondicionLongevidadTaxativa() {
        List<RankingEmpresa> emprsas = RankingEmpresa.fromEmpresas(apple);
        new LongevidadTaxativa().evaluar(emprsas, ultimos3Anios);
        assertTrue(emprsas.get(0).getEsConvenienteInvertir());
    }

    private void resultadoOperacion(OperacionIndicador operacion, Periodo periodo, Indicador indicador, Empresa empresa, Double resultado) {
        Double calculo = operacion.calcular(periodo, empresa, indicador);
        assertEquals(resultado, calculo, 0);
    }

    @Test
    public void testEvaluarSumatoriaDeIngresoNeto() throws IOException {
        facebook.getCuentas().add(new Cuenta("INO_CONTINUAS", 1234, new Periodo("01/01/2014", "31/06/2014")));
        facebook.getCuentas().add(new Cuenta("INO_CONTINUAS", 1234, new Periodo("01/01/2015", "31/06/2015")));
        facebook.getCuentas().add(new Cuenta("INO_DISCONTINUAS", 3030, new Periodo("01/01/2014", "31/06/2014")));
        facebook.getCuentas().add(new Cuenta("INO_DISCONTINUAS", 3030, new Periodo("01/01/2015", "31/06/2015")));
        resultadoOperacion(
                new Sumatoria(),
                new Periodo("01/01/2011", "31/12/2016"),
                new IndicadorIngresoNeto(),
                facebook,
                26403.0
        );
    }

    @Test
    public void testEvaluarPromedioROE() throws IOException {
        resultadoOperacion(
                new Promedio(),
                new Periodo("01/01/2010", "31/12/2013"),
                new IndicadorROE(),
                facebook,
                891.3
        );
    }

    @Test
    public void testEvaluarMedianaProporcionDeDeuda() throws IOException {
        facebook.getCuentas().add(new Cuenta("FDS", 330, new Periodo("01/12/2007", "31/12/2007")));
        facebook.getCuentas().add(new Cuenta("FDS", 340, new Periodo("01/12/2008", "31/12/2008")));
        facebook.getCuentas().add(new Cuenta("FDS", 350, new Periodo("01/12/2009", "31/12/2009")));
        resultadoOperacion(
                new Mediana(),
                new Periodo("01/01/2007", "31/12/2009"),
                new IndicadorProporcionDeDeuda(),
                facebook,
                320.0
        );
    }

    @Test
    public void testEvaluarOperacionIndicadorComun() throws IOException {
        facebook.getCuentas().add(new Cuenta("INO_CONTINUAS", 2814, new Periodo("01/12/2015", "31/12/2015")));
        facebook.getCuentas().add(new Cuenta("INO_DISCONTINUAS", 3131, new Periodo("01/12/2015", "31/12/2015")));
        resultadoOperacion(
                new OperacionIndicador(),
                anio2015,
                new IndicadorROE(),
                facebook,
                1187.0
        );
    }

}

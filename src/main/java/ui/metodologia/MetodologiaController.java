package ui.metodologia;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import static java.util.stream.Collectors.toList;
import modelo.Empresa;
import modelo.Periodo;
import modelo.metodologias.Metodologia;
import modelo.metodologias.RankingEmpresa;
import modelo.repositorios.RepoDeEmpresas;
import modelo.repositorios.RepoDeMetodologias;
import modelo.repositorios.persistencia.RepoDeEmpresasPersistencia;
import modelo.repositorios.persistencia.RepoDeMetodologiasPersistencia;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class MetodologiaController {

    private final RepoDeMetodologias metodologias;
    private final RepoDeEmpresas empresas;

    public MetodologiaController() {
        metodologias = RepoDeMetodologiasPersistencia.getInstancia();
        empresas = RepoDeEmpresasPersistencia.getInstancia();
    }

    public ModelAndView lista(Request req, Response res) {
        Collection<Metodologia> metodologiaList = metodologias.getAll();
        return new ModelAndView(metodologiaList, "metodologias/listar.hbs");
    }

    public ModelAndView detalle(Request req, Response res) {
        Long id = Long.valueOf(req.params("idMetodologia"));

        Metodologia metodologia = metodologias.getById(id);
        List<Empresa> empresasAEvaluar = empresas.getEmpresas();

        return new ModelAndView(new MetodologiaDetalleVM(metodologia, empresasAEvaluar), "metodologias/detalle.hbs");
    }

    public ModelAndView evaluar(Request req, Response res) {
        Long idMetodologia = Long.valueOf(req.params("idMetodologia"));
        String[] idEmpresas = req.queryParamsValues("empresa");
        
        Metodologia metodologia = metodologias.getById(idMetodologia);
        List<Empresa> empresasAEvaluar = Arrays.stream(idEmpresas)
                .map(id-> Integer.valueOf(id))
                .map(id -> empresas.obtenerPorId(id))
                .collect(toList());
        
        List<RankingEmpresa> ranking = metodologia.evaluar(empresasAEvaluar, new Periodo(1));
        
        return new ModelAndView(ranking, "metodologias/evaluar.hbs");
    }

}

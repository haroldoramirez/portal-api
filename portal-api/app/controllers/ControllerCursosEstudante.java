package controllers;

import models.CursosEstudante;
import models.Estudante;
import play.Logger;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.CursosEstudanteRepository;
import repositories.EstudanteRepository;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ControllerCursosEstudante extends Controller {

    private final CursosEstudanteRepository cursosEstudanteRepository;
    private final EstudanteRepository estudanteRepository;
    private final Form<CursosEstudante> cursosEstudanteForm = Form.form(CursosEstudante.class);

    @Inject
    public ControllerCursosEstudante (CursosEstudanteRepository cursosEstudanteRepository, EstudanteRepository estudanteRepository) {
        this.cursosEstudanteRepository = cursosEstudanteRepository;
        this.estudanteRepository = estudanteRepository;
    }

    public Result inserir(Integer idEstudante) {
        Logger.info("salvando cursos estudante...");

        CursosEstudante cursosEstudante = Json.fromJson(request().body().asJson(), CursosEstudante.class);

        Estudante estudante = estudanteRepository.findOne(idEstudante);

        cursosEstudante.setEstudante(estudante);

        Form<CursosEstudante> form = cursosEstudanteForm.bindFromRequest(request());

        try {
             cursosEstudante = cursosEstudanteRepository.save(cursosEstudante);
        }catch (Exception e) {
            return badRequest(form.errorsAsJson());
        }

        return created(Json.toJson(cursosEstudanteRepository.findOne(cursosEstudante.getId())));
    }

    public Result remover(Integer idEstudante, Integer id) {
        Logger.info("apagando cursos estudante de id {}", id);

        CursosEstudante cursosEstudante = cursosEstudanteRepository.findOne(id);

        if (!estudanteRepository.exists(idEstudante)) {
            return notFound("Estudante não encontrado");
        }

        if (!cursosEstudanteRepository.exists(id)) {
            return notFound("Curso Estudante não encontrado");
        }

        cursosEstudanteRepository.delete(cursosEstudante);
        return ok();
    }

    public Result atualizar(Integer idEstudante, Integer id) {
        Logger.info("atualizando curso estudante com id {}", idEstudante);

        CursosEstudante cursosEstudante = Json.fromJson(request().body().asJson(), CursosEstudante.class);

        Estudante estudante = estudanteRepository.findOne(idEstudante);

        cursosEstudante.setEstudante(estudante);
        cursosEstudante.setId(id);

        return ok(Json.toJson(cursosEstudanteRepository.save(cursosEstudante)));
    }

    public Result buscaPorIdEstudante(Integer idEstudante) {
        Logger.info("busca curso estudante por id {}", idEstudante);

        return ok(Json.toJson(cursosEstudanteRepository.findCursosEstudanteByIdEstudante(idEstudante)));
    }

    public Result buscaPorIdCurso(Integer idEstudante, Integer id) {

        Logger.info("busca curso do estudante por id {}", idEstudante);

        if (!estudanteRepository.exists(idEstudante)) {
            return notFound("Estudante não encontrado");
        }

        if (!cursosEstudanteRepository.exists(id)) {
            return notFound("Curso não encontrado");
        }

        return ok(Json.toJson(cursosEstudanteRepository.findOne(id)));
    }

    public Result options() {
        return ok();
    }
}

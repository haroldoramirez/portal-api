package controllers;

import models.Estudante;
import models.Experiencia;
import play.Logger;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.EstudanteRepository;
import repositories.ExperienciaRepository;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ControllerExperiencia extends Controller {

    private final ExperienciaRepository experienciaRepository;
    private final EstudanteRepository estudanteRepository;
    private final Form<Experiencia> experienciaForm = Form.form(Experiencia.class);

    @Inject
    public ControllerExperiencia (ExperienciaRepository experienciaRepository, EstudanteRepository estudanteRepository) {
        this.experienciaRepository = experienciaRepository;
        this.estudanteRepository = estudanteRepository;
    }

    public Result inserir(Integer idEstudante) {
        Logger.info("salvando experiencia...");

        Experiencia experiencia = Json.fromJson(request().body().asJson(), Experiencia.class);

        Estudante estudante = estudanteRepository.findOne(idEstudante);

        experiencia.setEstudante(estudante);

        Form<Experiencia> form = experienciaForm.bindFromRequest(request());

        try {
            experiencia = experienciaRepository.save(experiencia);
        }catch (Exception e) {
            return badRequest(form.errorsAsJson());
        }

        return created(Json.toJson(experienciaRepository.findOne(experiencia.getId())));
    }

    public Result remover(Integer idEstudante, Integer id) {
        Logger.info("apagando a experiencia de id {}", id);

        Experiencia experiencia = experienciaRepository.findOne(id);

        if (!estudanteRepository.exists(idEstudante)) {
            return notFound("Estudante não encontrado");
        }

        if (!experienciaRepository.exists(id)) {
            return notFound("experiencia não encontrada");
        }

        experienciaRepository.delete(experiencia);
        return ok();
    }

    public Result atualizar(Integer idEstudante, Integer id) {

        Logger.info("atualizando experiencia com id {}", idEstudante);

        Experiencia experiencia = Json.fromJson(request().body().asJson(), Experiencia.class);

        Estudante estudante = estudanteRepository.findOne(idEstudante);

        experiencia.setEstudante(estudante);
        experiencia.setId(id);

        return ok(Json.toJson(experienciaRepository.save(experiencia)));
    }

    public Result buscaPorIdEstudante(Integer idEstudante) {
        Logger.info("busca experiencia  por id do estudante");

        return ok(Json.toJson(experienciaRepository.findExperienciasByIdEstudante(idEstudante)));
    }

    public Result buscaPorIdExperiencia(Integer idEstudante, Integer id) {

        Logger.info("busca experiencia do estudante por id {}", idEstudante);

        if (!estudanteRepository.exists(idEstudante)) {
            return notFound("Estudante não encontrado");
        }

        if (!experienciaRepository.exists(id)) {
            return notFound("Experiencia não encontrada");
        }

        return ok(Json.toJson(experienciaRepository.findOne(id)));
    }

    public Result options() {
        return ok();
    }
}

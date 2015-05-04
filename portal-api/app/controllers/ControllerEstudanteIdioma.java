package controllers;

import models.Estudante;
import models.EstudanteIdioma;
import play.Logger;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.EstudanteIdiomaRepository;
import repositories.EstudanteRepository;
import repositories.IdiomaRepository;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ControllerEstudanteIdioma extends Controller {

    private final EstudanteRepository estudanteRepository;
    private final EstudanteIdiomaRepository estudanteIdiomaRepository;
    private final IdiomaRepository idiomaRepository;

    private Form<EstudanteIdioma> estudanteIdiomaForm = Form.form(EstudanteIdioma.class);

    @Inject
    public ControllerEstudanteIdioma(EstudanteIdiomaRepository estudanteIdiomaRepository,
            EstudanteRepository estudanteRepository, IdiomaRepository idiomaRepository) {
        this.estudanteIdiomaRepository = estudanteIdiomaRepository;
        this.estudanteRepository = estudanteRepository;
        this.idiomaRepository = idiomaRepository;
    }

    public Result inserir(Integer idEstudante) {
        Logger.info("salvando estudanteidioma...");
        EstudanteIdioma estudanteIdioma = Json.fromJson(request().body().asJson(), EstudanteIdioma.class);

        Estudante estudante = estudanteRepository.findOne(idEstudante);

        estudanteIdioma.setEstudante(estudante);

        Form<EstudanteIdioma> form = estudanteIdiomaForm.bindFromRequest(request());

        try {
            estudanteIdioma = estudanteIdiomaRepository.save(estudanteIdioma);
        }catch (Exception e) {
            return badRequest(form.errorsAsJson());
        }

        return created(Json.toJson(estudanteIdiomaRepository.findOne(estudanteIdioma.getId())));
    }

    public Result remover(Integer idEstudante, Integer id) {
        Logger.info("apagando cursos estudante de id {}", id);

        EstudanteIdioma estudanteIdioma = estudanteIdiomaRepository.findOne(id);

        if (!estudanteRepository.exists(idEstudante)) {
            return notFound("Estudante não encontrado");
        }

        if (!estudanteIdiomaRepository.exists(id)) {
            return notFound("Estudante idioma não encontrado");
        }

        estudanteIdiomaRepository.delete(id);
        return ok();
    }

    public Result atualizar(Integer idEstudante, Integer id) {

        Logger.info("atualizando idioma estudante com id {}", idEstudante);

        EstudanteIdioma estudanteIdioma = Json.fromJson(request().body().asJson(), EstudanteIdioma.class);

        Estudante estudante = estudanteRepository.findOne(idEstudante);

        estudanteIdioma.setEstudante(estudante);
        estudanteIdioma.setId(id);

        return ok(Json.toJson(estudanteIdiomaRepository.save(estudanteIdioma)));
    }

    public Result buscaPorIdEstudante(Integer idEstudante) {
        Logger.info("busca idiomas pelo id do estudante id {}", idEstudante);

        return ok(Json.toJson(estudanteIdiomaRepository.findByIdEstudante(idEstudante)));
    }

    public Result buscaPorIdIdioma(Integer idEstudante, Integer id) {

        Logger.info("busca idioma do estudante por id {}", idEstudante);

        if (!estudanteRepository.exists(idEstudante)) {
            return notFound("Estudante não encontrado");
        }

        if (!estudanteIdiomaRepository.exists(id)) {
            return notFound("Idioma não encontrado");
        }

        return ok(Json.toJson(estudanteIdiomaRepository.findOne(id)));
    }

    public Result options() {
        return ok();
    }

}

package controllers;

import models.Estudante;
import models.EstudanteConhecimentoInformatica;
import play.Logger;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.ConhecimentoInformaticaRepository;
import repositories.EstudanteConhecimentoInformaticaRepository;
import repositories.EstudanteRepository;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ControllerEstudanteConhecimentoInformatica extends Controller {

    private final EstudanteConhecimentoInformaticaRepository estudanteConhecimentoInformaticaRepository;
    private final EstudanteRepository estudanteRepository;
    private final ConhecimentoInformaticaRepository conhecimentoInformaticaRepository;

    private Form<EstudanteConhecimentoInformatica>
            estudanteConhecimentoInformaticaForm = Form.form(EstudanteConhecimentoInformatica.class);

    @Inject
    public ControllerEstudanteConhecimentoInformatica(EstudanteConhecimentoInformaticaRepository estudanteConhecimentoInformaticaRepository,
                      EstudanteRepository estudanteRepository, ConhecimentoInformaticaRepository conhecimentoInformaticaRepository) {
        this.estudanteConhecimentoInformaticaRepository = estudanteConhecimentoInformaticaRepository;
        this.conhecimentoInformaticaRepository = conhecimentoInformaticaRepository;
        this.estudanteRepository = estudanteRepository;
    }

    public Result inserir(Integer idEstudante) {
        Logger.info("salvando estudante conhecimento informatica...");

        EstudanteConhecimentoInformatica estudanteConhecimentoInformatica = Json.fromJson(request().body().asJson(), EstudanteConhecimentoInformatica.class);

        Estudante estudante = estudanteRepository.findOne(idEstudante);

        estudanteConhecimentoInformatica.setEstudante(estudante);

        Form<EstudanteConhecimentoInformatica> form = estudanteConhecimentoInformaticaForm.bindFromRequest(request());

        try {
            estudanteConhecimentoInformatica = estudanteConhecimentoInformaticaRepository.save(estudanteConhecimentoInformatica);
        }catch (Exception e) {
            return badRequest(form.errorsAsJson());
        }

        return created(Json.toJson(estudanteConhecimentoInformatica));
    }

    public Result remover(Integer idEstudante, Integer id) {
        Logger.info("apagando estudante conhecimento informatica de id {}", id);

        EstudanteConhecimentoInformatica estudanteConhecimentoInformatica = estudanteConhecimentoInformaticaRepository.findOne(id);

        if (!estudanteRepository.exists(idEstudante)) {
            return notFound("Estudante não encontrado");
        }

        if (!estudanteConhecimentoInformaticaRepository.exists(id)) {
            return notFound("Estudante conhecimento informatica não encontrado");
        }

        estudanteConhecimentoInformaticaRepository.delete(estudanteConhecimentoInformatica);
        return ok();
    }

    public Result atualizar(Integer idEstudante, Integer id) {
        Logger.info("atualizando estudante conhecimento informatica com id {}", id);

        EstudanteConhecimentoInformatica estudanteConhecimentoInformatica = Json.fromJson(request().body().asJson(), EstudanteConhecimentoInformatica.class);

        Estudante estudante = estudanteRepository.findOne(idEstudante);

        estudanteConhecimentoInformatica.setEstudante(estudante);
        estudanteConhecimentoInformatica.setId(id);

        return ok(Json.toJson(estudanteConhecimentoInformaticaRepository.save(estudanteConhecimentoInformatica)));
    }

    public Result buscaPorIdEstudante(Integer idEstudante) {
        Logger.info("busca todos os estudante conhecimento informatica...");

        return ok(Json.toJson(estudanteConhecimentoInformaticaRepository.findConhecimentoInformaticaByIdEstudante(idEstudante)));
    }

    public Result buscaPorIdInformatica(Integer idEstudante, Integer id) {
        Logger.info("busca estudante conhecimento informatica por id {}", id);

        if (!estudanteRepository.exists(idEstudante)) {
            return notFound("Estudante não encontrado");
        }

        if (!estudanteConhecimentoInformaticaRepository.exists(id)) {
            return notFound("estudante conhecimento informatica não encontrado");
        }

        return ok(Json.toJson(estudanteConhecimentoInformaticaRepository.findOne(id)));
    }

    public Result options() {
        return ok();
    }
}

package controllers;

import models.ConhecimentoInformatica;
import models.GrupoConhecimento;
import play.Logger;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.ConhecimentoInformaticaRepository;
import repositories.GrupoConhecimentoRepository;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ControllerConhecimentoInformatica extends Controller {

    private final ConhecimentoInformaticaRepository conhecimentoInformaticaRepository;
    private final GrupoConhecimentoRepository grupoConhecimentoRepository;
    private final Form<ConhecimentoInformatica> conhecimentoInformaticaForm = Form.form(ConhecimentoInformatica.class);

    @Inject
    public ControllerConhecimentoInformatica(ConhecimentoInformaticaRepository conhecimentoInformaticaRepository,
                GrupoConhecimentoRepository grupoConhecimentoRepository) {
        this.conhecimentoInformaticaRepository = conhecimentoInformaticaRepository;
        this.grupoConhecimentoRepository = grupoConhecimentoRepository;
    }

    public Result inserir(Integer idGrupo) {
        Logger.info("salvando conhecimentoinformatica...");
        ConhecimentoInformatica conhecimentoInformatica = Json.fromJson(request().body().asJson(), ConhecimentoInformatica.class);

        GrupoConhecimento grupoConhecimento = grupoConhecimentoRepository.findOne(idGrupo);

        conhecimentoInformatica.setGrupoConhecimento(grupoConhecimento);

        Form<ConhecimentoInformatica> form = conhecimentoInformaticaForm.bindFromRequest(request());

        try {
            conhecimentoInformatica = conhecimentoInformaticaRepository.save(conhecimentoInformatica);
        }catch (Exception e) {
            return badRequest(form.errorsAsJson());
        }

        return created(Json.toJson(conhecimentoInformatica));
    }

    public Result remover(Integer idGrupo, Integer id) {
        Logger.info("apagando conhecimento informatica de id {}", id);

        ConhecimentoInformatica conhecimentoInformatica = conhecimentoInformaticaRepository.findOne(id);

        if (!grupoConhecimentoRepository.exists(idGrupo)) {
            return notFound("Grupo Conhecimento não encontrado");
        }

        if (!conhecimentoInformaticaRepository.exists(id)) {
            return notFound("Conhecimento Informatica não encontrado");
        }

        conhecimentoInformaticaRepository.delete(conhecimentoInformatica);
        return ok();
    }

    public Result atualizar(Integer idGrupo, Integer id) {
        Logger.info("atualizando conhecimento informatica com id {}", id);

        ConhecimentoInformatica conhecimentoInformatica = Json.fromJson(request().body().asJson(), ConhecimentoInformatica.class);

        GrupoConhecimento grupoConhecimento = grupoConhecimentoRepository.findOne(idGrupo);

        conhecimentoInformatica.setGrupoConhecimento(grupoConhecimento);
        conhecimentoInformatica.setId(id);

        return ok(Json.toJson(conhecimentoInformaticaRepository.save(conhecimentoInformatica)));
    }

    public Result buscaPorIdGrupo(Integer idGrupo) {
        Logger.info("busca conhecimento informatica por id {}", idGrupo);

        return ok(Json.toJson(conhecimentoInformaticaRepository.findByIdGrupo(idGrupo)));
    }

    public Result buscaPorIdConhecimentoInformatica(Integer idGrupo, Integer id) {
        Logger.info("busca todos os conhecimentos informatica...");

        if (!grupoConhecimentoRepository.exists(idGrupo)) {
            return notFound("Grupo não encontrado");
        }

        if (!conhecimentoInformaticaRepository.exists(id)) {
            return notFound("conhecimento informatica não encontrado");
        }

        return ok(Json.toJson(conhecimentoInformaticaRepository.findOne(id)));
    }

    public Result options() {
        return ok();
    }
}

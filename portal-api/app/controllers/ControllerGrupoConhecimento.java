package controllers;

import models.GrupoConhecimento;
import play.Logger;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.GrupoConhecimentoRepository;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ControllerGrupoConhecimento extends Controller {

    private final GrupoConhecimentoRepository grupoConhecimentoRepository;
    private final Form<GrupoConhecimento> grupoConhecimentoForm = Form.form(GrupoConhecimento.class);

    @Inject
    public ControllerGrupoConhecimento(GrupoConhecimentoRepository grupoConhecimentoRepository) {
        this.grupoConhecimentoRepository = grupoConhecimentoRepository;
    }

    public Result inserir() {
        Logger.info("salvando grupoconhecimento...");

        GrupoConhecimento grupoConhecimento = Json.fromJson(request().body().asJson(), GrupoConhecimento.class);

        Form<GrupoConhecimento> form = grupoConhecimentoForm.bindFromRequest(request());

        try {
            grupoConhecimento = grupoConhecimentoRepository.save(grupoConhecimento);
        }catch (Exception e) {
            return badRequest(form.errorsAsJson());
        }

        return created(Json.toJson(grupoConhecimento));
    }

    public Result atualizar(Integer id) {
        Logger.info("atualizando grupo conhecimento com id {}", id);

        GrupoConhecimento grupoConhecimento = Json.fromJson(request().body().asJson(), GrupoConhecimento.class);

        Form<GrupoConhecimento> form = grupoConhecimentoForm.bindFromRequest(request());

        if (!grupoConhecimentoRepository.exists(id)) {
            return notFound("grupo conhecimento não encontrado");
        }else {
            grupoConhecimento.setId(id);
        }

        return ok(Json.toJson(grupoConhecimentoRepository.save(grupoConhecimento)));
    }

    public Result buscaPorId(Integer id) {
        Logger.info("busca grupo conhecimento por id {}", id);

        GrupoConhecimento grupoConhecimento = grupoConhecimentoRepository.findOne(id);

        if (grupoConhecimento == null) {
            return notFound("grupo conhecimento não encontrado");
        }

        return ok(Json.toJson(grupoConhecimento));
    }

    public Result buscaTodos() {
        Logger.info("busca todos os grupos de conhecimento...");

        return ok(Json.toJson(grupoConhecimentoRepository.findAll()));
    }

    public Result options() {
        return ok();
    }
}

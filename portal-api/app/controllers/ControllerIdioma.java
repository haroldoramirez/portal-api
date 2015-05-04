package controllers;

import models.Idioma;
import play.Logger;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.IdiomaRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class ControllerIdioma extends Controller {

    private final IdiomaRepository idiomaRepository;
    private final Form<Idioma> idiomaForm = Form.form(Idioma.class);

    @Inject
    public ControllerIdioma(IdiomaRepository idiomaRepository) {
        this.idiomaRepository = idiomaRepository;
    }

    public Result inserir() {
        Logger.info("salvando idioma...");

        Idioma idioma = Json.fromJson(request().body().asJson(), Idioma.class);

        Form<Idioma> form = idiomaForm.bindFromRequest(request());

        try {
            idioma = idiomaRepository.save(idioma);
        }catch (Exception e) {
            return badRequest(form.errorsAsJson());
        }

        return created(Json.toJson(idioma));
    }

    public Result atualizar(Integer id) {
        Logger.info("atualizando idioma com id {}", id);

        Idioma idioma = Json.fromJson(request().body().asJson(), Idioma.class);

        Form<Idioma> form = idiomaForm.bindFromRequest(request());

        if (!idiomaRepository.exists(id)) {
            return notFound("idioma não encontrado");
        }else {
            idioma.setId(id);
        }

        return ok(Json.toJson(idiomaRepository.save(idioma)));
    }

    public Result buscaPorId(Integer id) {
        Logger.info("busca idioma por id {}", id);

        Idioma idioma = idiomaRepository.findOne(id);

        if (idioma == null) {
            return notFound("idioma não encontrado");
        }

        return ok(Json.toJson(idioma));
    }

    public Result buscaTodos() {
        Logger.info("busca todos os idiomas...");

        return ok(Json.toJson(idiomaRepository.findAll()));
    }

    public Result options() {
        return ok();
    }
}

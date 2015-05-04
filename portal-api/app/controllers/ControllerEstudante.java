package controllers;

import models.Estudante;
import play.Logger;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.EstudanteRepository;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ControllerEstudante extends Controller {

    private final EstudanteRepository estudanteRepository;
    private final Form<Estudante> estudanteForm = Form.form(Estudante.class);

    @Inject
    public ControllerEstudante(EstudanteRepository estudanteRepository) {
        this.estudanteRepository = estudanteRepository;
    }

    public Result inserir() {
        Logger.info("salvando estudante...");

        Estudante estudante =  Json.fromJson(request().body().asJson(), Estudante.class);

        Form<Estudante> form = estudanteForm.bindFromRequest(request());

        if (estudanteRepository.findByCpf(estudante.getCpf()) != null) {
            return badRequest(form.errorsAsJson());
        }

        if (estudanteRepository.findByEmail(estudante.getUsuario().getEmail()) != null) {
            return badRequest(form.errorsAsJson());
        }

        try {
            estudante = estudanteRepository.save(estudante);
        } catch (Exception e){
            return badRequest(form.errorsAsJson());
        }

        //TODO ver melhor forma de fazer pois retorna data em timestamp
        estudante = estudanteRepository.findOne(estudante.getId());

        return created(Json.toJson(estudante));
    }

    //TODO somente para testes
    public Result remover(Integer id) {
        Logger.info("apagando estudante de id {}", id);

        Estudante estudante = estudanteRepository.findOne(id);

        if (!estudanteRepository.exists(id)) {
            return notFound("estudante não encontrado");
        }

        estudanteRepository.delete(estudante);
        return ok();
    }

    public Result atualizar(Integer id) {
        Logger.info("atualizando estudante com id {}", id);

        Estudante estudante = Json.fromJson(request().body().asJson(), Estudante.class);

        Form<Estudante> form = estudanteForm.bindFromRequest(request());

        if (estudante.getId() == null) {
            return badRequest("falta o parâmetro id");
        }

        if (!id.equals(estudante.getId())) {
            return badRequest("dados inconsistentes");
        }

        if (!estudanteRepository.exists(id)) {
            return notFound("estudante não encontrado");
        }

        //TODO ver melhor forma de fazer
        Estudante estudanteAux = estudanteRepository.findOne(id);

        estudante.setUsuario(estudanteAux.getUsuario());

        try {
            estudante = estudanteRepository.save(estudante);
        }catch (Exception e) {
            return badRequest(form.errorsAsJson());
        }

        //TODO ver melhor forma de fazer pois retorna data em timestamp
        estudante = estudanteRepository.findOne(estudante.getId());

        return ok(Json.toJson(estudante));
    }

    public Result buscaPorId(Integer id) {
        Logger.info("busca estudante por id {}", id);

        Estudante estudante = estudanteRepository.findOne(id);

        if (estudante == null) {
            return notFound("estudante não encontrado");
        }

        return ok(Json.toJson(estudante));
    }

    public Result buscaTodos() {
        Logger.info("busca todos os estudantes...");

        return ok(Json.toJson(estudanteRepository.findAll()));
    }

    public Result options() {
        return ok();
    }

}
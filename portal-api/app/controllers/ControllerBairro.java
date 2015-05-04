package controllers;

import models.Bairro;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.BairroRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class ControllerBairro extends Controller {

    private final BairroRepository bairroRepository;

    @Inject
    public ControllerBairro(BairroRepository bairroRepository) {
        this.bairroRepository = bairroRepository;
    }

    public Result buscaPorId(Integer id) {
        Logger.debug("busca Bairro por id {}", id);

        Bairro bairro = bairroRepository.findOne(id);

        if (bairro == null) {
            return notFound("Bairro não encontrado");
        }

        return ok(Json.toJson(bairro));
    }

    public Result buscaPorCidade(Integer cidadeId) {
        Logger.debug("busca Bairro por cidade {}", cidadeId);

        List<Bairro> bairros = bairroRepository.findByCidade(cidadeId);
//        if (bairros.isEmpty()) {
//            return notFound("Bairro não encontrado");
//        }

        return ok(Json.toJson(bairros));
    }

    //TODO somente para testes
    public Result buscaTodos() {
        Logger.info("busca todos os Bairros...");

        List<Bairro> bairros = bairroRepository.findAll();

        return ok(Json.toJson(bairros));
    }

    public Result options() {
        return ok();
    }
}

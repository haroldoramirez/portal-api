package controllers;

import models.Cidade;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.CidadeRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class ControllerCidade extends Controller {

    private final CidadeRepository cidadeRepository;

    @Inject
    public ControllerCidade(CidadeRepository cidadeRepository) {
        this.cidadeRepository = cidadeRepository;
    }

    public Result buscaPorId(Integer id) {
        Logger.debug("busca Cidade por id {}", id);

        Cidade cidade = cidadeRepository.findOne(id);

        if (cidade == null) {
            return notFound("Cidade não encontrado");
        }

        return ok(Json.toJson(cidade));
    }

    public Result buscaPorUf(String uf) {
        List<Cidade> cidades = cidadeRepository.findByUf(uf);

        if (cidades == null) {
            return notFound("Cidade não encontrada");
        }

        return ok(Json.toJson(cidades));
    }

    public Result options() {
        return ok();
    }


}

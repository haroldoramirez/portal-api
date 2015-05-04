package controllers;

import models.Endereco;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.EnderecoRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class ControllerEndereco extends Controller {

    private final EnderecoRepository enderecoRepository;

    @Inject
    public ControllerEndereco(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    public Result buscaPorId(Integer id) {
        Logger.info("busca Endereco por id {}", id);

        Endereco endereco = enderecoRepository.findOne(id);

        if (endereco == null) {
            return notFound("Endereço não encontrado");
        }

        return ok(Json.toJson(endereco));
    }

    public Result buscaPorCep(String cep) {
        Logger.info("busca Endereco por cep {}", cep);

        List<Endereco> enderecos = enderecoRepository.findByCep(cep);

        if (enderecos.isEmpty()) {
            return notFound("Endereço não encontrado");
        }

        return ok(Json.toJson(enderecos));
    }

    //TODO somente para testes
    public Result buscaTodos() {
        Logger.info("busca todos os Enderecos...");

        return ok(Json.toJson(enderecoRepository.findAll()));
    }

    public Result options() {
        return ok();
    }
}
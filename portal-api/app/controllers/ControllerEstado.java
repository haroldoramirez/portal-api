package controllers;

import models.Estado;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.EstadoRepository;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ControllerEstado extends Controller {

    private final EstadoRepository estadoRepository;

    @Inject
    public ControllerEstado(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    public Result buscaPorId(Integer id) {
        Logger.debug("busca Estado por id {}", id);

        Estado estado = estadoRepository.findOne(id);

        if (estado == null) {
            return notFound("Estado não encontrado");
        }
        
        return ok(Json.toJson(estado));
    }

    public Result buscaTodos() {
        Logger.info("busca todos os Estados...");

        return ok(Json.toJson(  estadoRepository.findAll()));
    }

    public Result options() {
        return ok();
    }
}

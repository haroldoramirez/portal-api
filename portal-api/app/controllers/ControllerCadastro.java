package controllers;

import models.Cadastro;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.CadastroRepository;
import repositories.EstudanteRepository;
import repositories.UsuarioRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.LinkedList;
import java.util.List;

@Named
public class ControllerCadastro extends Controller {

    private final EstudanteRepository estudanteRepository;
    private final CadastroRepository cadastroRepository;
    private final UsuarioRepository usuarioRepository;

    @Inject
    public ControllerCadastro(EstudanteRepository estudanteRepository,
           CadastroRepository cadastroRepository, UsuarioRepository usuarioRepository) {
        this.estudanteRepository = estudanteRepository;
        this.cadastroRepository = cadastroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Result inserir() {
        Cadastro cadastro = Json.fromJson(request().body().asJson(), Cadastro.class);
        String email = cadastro.getUsuario().getEmail();
        String document = cadastro.getDocumento();

        List<String> listString = new LinkedList<String>();

        if (cadastroRepository.findByDocumento(document) != null) {
            listString.add("Número do CPF já solicitado para cadastro");
        }

        if (cadastroRepository.findByEmail(email) != null) {
            listString.add("Endereço de Email já solicitado para cadastro");
        }

        if (estudanteRepository.findByCpf(document) != null) {
            listString.add("Número do CPF já cadastrado");
        }

        if (estudanteRepository.findByEmail(email) != null) {
            listString.add("Endereço de Email cadastrado");
        }

        if (listString.isEmpty()){
            cadastroRepository.save(cadastro);
            return ok(Json.toJson(cadastro));
        }else {
            return badRequest(Json.toJson(listString));
        }
    }
}

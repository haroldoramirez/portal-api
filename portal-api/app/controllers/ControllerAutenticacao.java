package controllers;

import models.Usuario;
import play.Logger;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.UsuarioRepository;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ControllerAutenticacao extends Controller {

    private final UsuarioRepository usuarioRepository;
    private final Form<Usuario> usuarioForm = Form.form(Usuario.class);

    @Inject
    public ControllerAutenticacao(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Result autenticar(String login, String senha) {

        Logger.info(login +  senha);
        Usuario usuario = usuarioRepository.findByLoginAndSenha(login, senha);

        if (usuario!=null){
            return ok(Json.toJson(usuario));
        }

        return badRequest("Falha da Autenticação");
    }
}


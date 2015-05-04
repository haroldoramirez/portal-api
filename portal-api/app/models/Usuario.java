package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(schema = "public", name = "usuario")
public class Usuario implements Serializable {

    @Id
    @Column(name = "id_usuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Constraints.Required( message = "Você precisa inserir o login")
    @Column(name = "login", nullable = false)
    private String login;

    @Constraints.Required( message = "Você precisa inserir o email")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Constraints.Required( message = "Você precisa inserir a senha")
    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "data_acesso")
    @Temporal(TemporalType.DATE)
    private Date dataUltimoAcesso;

    @ManyToOne
    @JoinColumn(name = "id_perfil")
    private Perfil perfil;

    @JsonIgnore
    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //TODO JsonIgnore removido para realizar testes na controller
    //@JsonIgnore
    public String getSenha() {
        return senha;
    }

    @JsonProperty
    public void setSenha(String senha) {
        this.senha = senha;
    }
}

package models;

import play.data.validation.Constraints;
import play.libs.Json;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "grupoconhecimento")
public class GrupoConhecimento implements Serializable {

    @Id
    @Column(name = "id_grupoconhecimento")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Constraints.Required(message = "Você precisa inserir a descricao")
    @Column(name = "nome", nullable = false)
    private String descricao;

    @Column(name = "posicao")
    private Integer posicao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
    }

    @Override
    public String toString() {
        return Json.toJson(this).toString();
    }
}

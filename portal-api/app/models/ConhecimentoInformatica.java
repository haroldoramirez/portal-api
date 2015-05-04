package models;

import play.data.validation.Constraints;
import play.libs.Json;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "conhecimentoinformatica")
public class ConhecimentoInformatica implements Serializable {

    @Id
    @Column(name = "id_conhecimentoinformatica")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Constraints.Required(message = "Você precisa inserir a descricao")
    @Column(name = "nome", nullable = false)
    private String descricao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_grupoconhecimento")
    private GrupoConhecimento grupoConhecimento;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GrupoConhecimento getGrupoConhecimento() {
        return grupoConhecimento;
    }

    public void setGrupoConhecimento(GrupoConhecimento grupoConhecimento) {
        this.grupoConhecimento = grupoConhecimento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return Json.toJson(this).toString();
    }
}

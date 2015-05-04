package models;

import play.libs.Json;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "localidade", name = "bairro")
public class Bairro implements Serializable {

    @Id
    @Column(name = "id_bairro")
    private Integer id;

    @Column(name = "descricao")
    private String nome;

    @ManyToOne
    @JoinColumn(name = "id_cidade")
    private Cidade cidade;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    @Override
    public String toString() {
        return Json.toJson(this).toString();
    }
}

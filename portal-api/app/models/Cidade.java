package models;

import play.libs.Json;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "localidade", name = "cidade")
public class Cidade implements Serializable {

    @Id
    @Column(name = "id_cidade")
    private Integer id;

    @Column(name = "descricao")
    private String nome;

    @Column(name = "cep")
    private String cep;

    @ManyToOne
    @JoinColumn(name = "id_uf")
    private Estado estado;

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

    //@JsonIgnore
    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return Json.toJson(this).toString();
    }
}

package models;

import play.libs.Json;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "localidade", name = "uf")
public class Estado implements Serializable {

    @Id
    @Column(name = "id_uf")
    private Integer id;

    @Column(name = "sigla")
    private String sigla;

    @Column(name = "descricao")
    private String nome;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return Json.toJson(this).toString();
    }
}

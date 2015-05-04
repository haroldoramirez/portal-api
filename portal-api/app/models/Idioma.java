package models;

import play.data.validation.Constraints;
import play.libs.Json;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "idioma")
public class Idioma implements Serializable {

    @Id
    @Column(name = "id_idioma")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Constraints.Required(message = "Você precisa inserir um idioma")
    @Column(name = "nome", nullable = false)
    private String nomeIdioma;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeIdioma() {
        return nomeIdioma;
    }

    public void setNomeIdioma(String nomeIdioma) {
        this.nomeIdioma = nomeIdioma;
    }

    @Override
    public String toString() {
        return Json.toJson(this).toString();
    }
}

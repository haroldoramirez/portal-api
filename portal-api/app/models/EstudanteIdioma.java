package models;

import play.libs.Json;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "estudanteidioma", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_idioma" , "id_estudante"})
})
public class EstudanteIdioma implements Serializable {

    @Id
    @Column(name = "id_estudanteidioma")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "le")
    private Integer le;

    @Column(name = "fala")
    private Integer fala;

    @Column(name = "escreve")
    private Integer escreve;

    @Column(name = "compreende")
    private Integer compreende;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_idioma")
    private Idioma idioma;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_estudante")
    private Estudante estudante;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Estudante getEstudante() {
        return estudante;
    }

    public void setEstudante(Estudante estudante) {
        this.estudante = estudante;
    }

    public Integer getLe() {
        return le;
    }

    public void setLe(Integer le) {
        this.le = le;
    }

    public Integer getFala() {
        return fala;
    }

    public void setFala(Integer fala) {
        this.fala = fala;
    }

    public Integer getEscreve() {
        return escreve;
    }

    public void setEscreve(Integer escreve) {
        this.escreve = escreve;
    }

    public Integer getCompreende() {
        return compreende;
    }

    public void setCompreende(Integer compreende) {
        this.compreende = compreende;
    }

    @Override
    public String toString() {
        return Json.toJson(this).toString();
    }
}

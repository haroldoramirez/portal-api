package models;

import play.libs.Json;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "estudanteconhecimentoinformatica")
public class EstudanteConhecimentoInformatica implements Serializable {

    @Id
    @Column(name = "id_estudanteconhecimentoinformatica")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name ="nivel")
    private Integer nivel;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_conhecimentoinformatica")
    private ConhecimentoInformatica conhecimentoInformatica;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_estudante")
    private Estudante estudante;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ConhecimentoInformatica getConhecimentoInformatica() {
        return conhecimentoInformatica;
    }

    public void setConhecimentoInformatica(ConhecimentoInformatica conhecimentoInformatica) {
        this.conhecimentoInformatica = conhecimentoInformatica;
    }

    public Estudante getEstudante() {
        return estudante;
    }

    public void setEstudante(Estudante estudante) {
        this.estudante = estudante;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return Json.toJson(this).toString();
    }
}


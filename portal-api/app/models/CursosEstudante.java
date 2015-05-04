package models;

import play.data.validation.Constraints;
import play.libs.Json;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "cursotecnico")
public class CursosEstudante implements Serializable {

    @Id
    @Column(name = "id_cursotecnico")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_inicio")
    private Date dataInicio;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_termino")
    private Date dataTermino;

    @Constraints.Required(message = "Você precisa inserir um nome")
    @Column(name = "nome", nullable = false)
    private String nome;

    @Constraints.Required(message = "Você precisa inserir uma instituição")
    @Column(name = "instituicao", nullable = false)
    private String instituicao;

    @Column(name = "carga_horaria")
    private Integer cargaHoraria;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_estudante")
    private Estudante estudante;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Estudante getEstudante() {
        return estudante;
    }

    public void setEstudante(Estudante estudante) {
        this.estudante = estudante;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(Date dataTermino) {
        this.dataTermino = dataTermino;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    @Override
    public String toString() {
        return Json.toJson(this).toString();
    }
}

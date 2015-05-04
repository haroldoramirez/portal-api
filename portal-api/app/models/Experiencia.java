package models;

import play.data.validation.Constraints;
import play.libs.Json;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "experiencia")
public class Experiencia implements Serializable {

    @Id
    @Column(name = "id_experiencia")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_inicio")
    private Date dataInicio;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_termino")
    private Date dataTermino;

    @Constraints.Required(message = "Você precisa inserir a empresa")
    @Column(name = "empresa", nullable = false)
    private String empresa;

    @Constraints.Required(message = "Você precisa inserir o cargo")
    @Column(name = "cargo", nullable = false)
    private String cargo;

    @Constraints.Required(message = "Você precisa inserir uma atividade")
    @Column(name = "atividade", nullable = false)
    private String atividade;

    @Constraints.Required(message = "Você precisa inserir um vinculo")
    @Column(name = "vinculo", nullable = false)
    private String vinculo;

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

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getAtividade() {
        return atividade;
    }

    public void setAtividade(String atividade) {
        this.atividade = atividade;
    }

    public String getVinculo() {
        return vinculo;
    }

    public void setVinculo(String vinculo) {
        this.vinculo = vinculo;
    }

    @Override
    public String toString() {
        return Json.toJson(this).toString();
    }
}

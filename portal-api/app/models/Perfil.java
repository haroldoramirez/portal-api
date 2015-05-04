package models;

import javax.persistence.*;

@Entity
@Table(schema = "public", name = "perfil")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String descricao;

    private Integer hierarquia;

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getHierarquia() {
        return hierarquia;
    }
}

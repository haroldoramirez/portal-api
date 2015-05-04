package models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema = "api", name = "cadastro")
public class Cadastro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String documento;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date validade;

    @JoinColumn(name = "id_usuario")
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Usuario usuario;

    public Cadastro() {
        validade = new Date();
    }

    public Long getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}

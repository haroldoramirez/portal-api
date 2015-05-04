package models;

import play.data.validation.Constraints;
import play.libs.Json;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "estudante")
public class Estudante implements Serializable {
	
	@Id
    @Column(name = "id_estudante")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Constraints.Required(message = "Você precisa inserir um nome")
    @Column(name = "nome", nullable = false)
    private String nomeCompleto;

    @Constraints.Required(message = "Você precisa inserir o nome da mãe")
    @Column(name="nome_mae", nullable = false)
    private String nomeDaMae;

    @Constraints.Required( message = "Você precisa inserir o cpf")
    @Column(name="cpf", unique = true)
    private String cpf;

    @Constraints.Required(message = "Você precisa inserir a data de nascimento")
    @Column(name="data_nasc", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;

    @Constraints.Required(message = "Você precisa inserir o gênero")
    @Enumerated(EnumType.STRING)
    @Column(name="sexo", nullable = false)
    private Genero genero;

    @Constraints.Required(message = "Você precisa inserir o estado civil")
    @Enumerated(EnumType.STRING)
    @Column(name="estado_civil", nullable = false)
    private EstadoCivil estadoCivil;

    @Constraints.Required(message = "Você precisa inserir a nacionalidade")
    @Column(name="nacionalidade", nullable = false)
    private String nacionalidade;

    @Constraints.Required(message = "Você precisa inserir a data de atualização")
    @Column(name = "data_atualizacao", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataAtualizacao;

    @Constraints.Required(message = "Você precisa inserir a situação do estudante")
    @Column(name = "trabalhando", nullable = false)
    private String trabalhando;

    @Column(name = "pne")
    private String pne;

    @Column(name = "pne_descricao")
    private String pneTipo;

    @Column(name="nome_pai")
    private String nomeDoPai;

    @Column(name = "foto")
    private String foto;

    @Column(name = "observacao")
    private String observacao;

    @Column(name = "cep")
    private String cep;

    @Column(name = "logradouro")
    private String logradouro;

    @Column(name = "numero")
    private String numero;

    @Column(name ="complemento")
    private String complemento;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "estado")
    private String estado;

    @Column(name = "pais")
    private String pais;

    @Column(name = "caixa_postal")
    private String caixaPostal;

    @Column(name = "rg_numero")
    private String rg;

    @Column(name = "rg_uf")
    private String orgaoExpedidor;

    @Column(name = "objetivos")
    private String objetivos;

    @Column(name = "telefone_residencial")
    private String telefoneResidencial;

    @Column(name = "telefone_comercial")
    private String telefoneComercial;

    @Column(name = "telefone_celular")
    private String telefoneCelular;

    @Column(name = "disponibilidade")
    private String disponibilidade;

    @Column(name = "ativo")
    private Integer ativo;

    @Column(name = "link_cnpq")
    private String linkCnpq;

    @Column(name = "termo_compromisso")
    private String termoCompromisso;

    @Column(name = "data_cadastro")
    @Temporal(TemporalType.DATE)
    private Date dataCadastro;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "estudanteusuario",
            joinColumns = @JoinColumn(name = "id_estudante"),
            inverseJoinColumns = @JoinColumn(name = "id_usuario"))
    private Usuario usuario;


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getNomeDaMae() {
        return nomeDaMae;
    }

    public void setNomeDaMae(String nomeDaMae) {
        this.nomeDaMae = nomeDaMae;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public String getTrabalhando() {
        return trabalhando;
    }

    public void setTrabalhando(String trabalhando) {
        this.trabalhando = trabalhando;
    }

    public String getNomeDoPai() {
        return nomeDoPai;
    }

    public void setNomeDoPai(String nomeDoPai) {
        this.nomeDoPai = nomeDoPai;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getPne() {
        return pne;
    }

    public void setPne(String pne) {
        this.pne = pne;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCaixaPostal() {
        return caixaPostal;
    }

    public void setCaixaPostal(String caixaPostal) {
        this.caixaPostal = caixaPostal;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getOrgaoExpedidor() {
        return orgaoExpedidor;
    }

    public void setOrgaoExpedidor(String orgaoExpedidor) {
        this.orgaoExpedidor = orgaoExpedidor;
    }

    public String getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(String objetivos) {
        this.objetivos = objetivos;
    }

    public String getPneTipo() {
        return pneTipo;
    }

    public void setPneTipo(String pneTipo) {
        this.pneTipo = pneTipo;
    }

    public String getTelefoneResidencial() {
        return telefoneResidencial;
    }

    public void setTelefoneResidencial(String telefoneResidencial) {
        this.telefoneResidencial = telefoneResidencial;
    }

    public String getTelefoneComercial() {
        return telefoneComercial;
    }

    public void setTelefoneComercial(String telefoneComercial) {
        this.telefoneComercial = telefoneComercial;
    }

    public String getTelefoneCelular() {
        return telefoneCelular;
    }

    public void setTelefoneCelular(String telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
    }

    public String getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(String disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public Integer getAtivo() {
        return ativo;
    }

    public void setAtivo(Integer ativo) {
        this.ativo = ativo;
    }

    public String getLinkCnpq() {
        return linkCnpq;
    }

    public void setLinkCnpq(String linkCnpq) {
        this.linkCnpq = linkCnpq;
    }

    public String getTermoCompromisso() {
        return termoCompromisso;
    }

    public void setTermoCompromisso(String termoCompromisso) {
        this.termoCompromisso = termoCompromisso;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return Json.toJson(this).toString();
    }

}

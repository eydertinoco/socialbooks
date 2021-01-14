package com.algaworks.socialbooks.domain;

import com.fasterxml.jackson.annotation.JsonInclude; //@JsonInclude(JsonInclude.Include.NON_NULL)
import com.fasterxml.jackson.annotation.JsonInclude.Include; //@JsonInclude(Include.NON_NULL)


import javax.persistence.*;

import javax.annotation.processing.Generated;
import java.util.Date;
import java.util.List;

@Entity
// A classe livro se tornar uma entidade JPA
public class Livro {

    // JsonInclude faz com que a informação não seja chamada se for nulo.
    @JsonInclude(Include.NON_NULL)
    @Id //Identificador do JPA
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Politica de como o identificador vai trabalhar.
    private Long id;
    @JsonInclude(Include.NON_NULL)
    private String nome;
    @JsonInclude(Include.NON_NULL)
    private Date dataPublicacao;
    @JsonInclude(Include.NON_NULL)
    private String editora;
    @JsonInclude(Include.NON_NULL)
    private String resumo;
    @JsonInclude(Include.NON_NULL)
    @Transient //Evita o JPA fazer comunicação entre Livro e Comentário.
    private List<Comentario> comentarios;
    @JsonInclude(Include.NON_NULL)
    private String autor;

    public Livro() {
    }

    public Livro(String nome) {
        this.nome = nome;
    }

    public Livro(Long id, String nome, Date dataPublicacao, String editora, String resumo,
                 List<Comentario> comentarios, String autor) {
        this.id = id;
        this.nome = nome;
        this.dataPublicacao = dataPublicacao;
        this.editora = editora;
        this.resumo = resumo;
        this.comentarios = comentarios;
        this.autor = autor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(Date dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
}

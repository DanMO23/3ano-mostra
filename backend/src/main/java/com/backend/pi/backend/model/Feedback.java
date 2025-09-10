package com.backend.pi.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   @Column(length = 500)
    private String descricao; // Mensagem que aparece para o jogador

    @Enumerated(EnumType.STRING)
    private FeedbackTipo feedbackTipo; // Enum para feedback positivo ou negativo

    @ManyToOne
    @JoinColumn(name = "arco_id", nullable = false) // Referência para a entidade Arco
    @JsonBackReference 
    private Arco arco;

    // Construtores
    public Feedback() {
    }

    public Feedback(String descricao, FeedbackTipo feedbackTipo, Arco arco) {
        this.descricao = descricao;
        this.feedbackTipo = feedbackTipo;
        this.arco = arco;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public FeedbackTipo getFeedbackTipo() {
        return feedbackTipo;
    }

    public void setFeedbackTipo(FeedbackTipo feedbackTipo) {
        this.feedbackTipo = feedbackTipo;
    }

    public Arco getArco() {
        return arco;
    }

    public void setArco(Arco arco) {
        this.arco = arco;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", feedbackTipo=" + feedbackTipo +
                ", arco=" + arco +
                '}';
    }
}
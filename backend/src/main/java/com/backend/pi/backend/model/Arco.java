package com.backend.pi.backend.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "arcos")
public class Arco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany(mappedBy = "arco", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Carta> conjuntoCartas = new ArrayList<>();

    @OneToMany(mappedBy = "arco", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Feedback> conjuntoFeedback = new ArrayList<>();

    @Enumerated(EnumType.STRING) // Armazenar o enum como String no banco de dados
    private CategoriaArco categoria; // Categoria do arco

    @Column(name = "background_arco")
    private String backgroundArco;

    // Construtor padrão
    public Arco() {
    }

    // Construtor com parâmetros
    public Arco(String nome, CategoriaArco categoria) {
        this.nome = nome;
        this.categoria = categoria;
    }

    // Getters e Setters
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

    public List<Carta> getConjuntoCartas() {
        return conjuntoCartas;
    }

    public void setConjuntoCartas(List<Carta> cartas) {
        this.conjuntoCartas = cartas;
    }

    public List<Feedback> getConjuntoFeedback() {
        return conjuntoFeedback;
    }

    public void setConjuntoFeedback(List<Feedback> feedbacks) {
        this.conjuntoFeedback = feedbacks;
    }

    public CategoriaArco getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaArco categoria) {
        this.categoria = categoria;
    }

    // Método para carregar cartas e feedbacks
    public void carregarArco(List<Carta> cartas, List<Feedback> feedbacks) {
        this.conjuntoCartas.addAll(cartas);
        this.conjuntoFeedback.addAll(feedbacks);
    }

    // Método para finalizar o arco
    public void finalizarArco() {
        // Lógica para finalizar o arco
        System.out.println("Arco finalizado.");
    }

    public String getBackgroundArco() {
        return backgroundArco;
    }

    public void setBackgroundArco(String backgroundArco) {
        this.backgroundArco = backgroundArco;
    }

    @Override
    public String toString() {
        return "Arco{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", categoria=" + categoria +
                ", conjuntoCartas=" + conjuntoCartas +
                ", conjuntoFeedback=" + conjuntoFeedback +
                '}';
    }
}
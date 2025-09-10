package com.backend.pi.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "decisao")
public class Decisao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_decisao")
    private Long id;

    @Column(name = "id_carta")
    private int idCarta; // Referência à carta (você pode usar um objeto Carta se preferir)

    private boolean resposta;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIdCarta() {
        return idCarta;
    }

    public void setIdCarta(int idCarta) {
        this.idCarta = idCarta;
    }

    public boolean isResposta() {
        return resposta;
    }

    public void setResposta(boolean resposta) {
        this.resposta = resposta;
    }
}
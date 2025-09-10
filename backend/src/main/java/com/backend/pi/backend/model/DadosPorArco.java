package com.backend.pi.backend.model;

import java.util.List;

public class DadosPorArco {

    private Long arcoId;
    private List<Decisao> decisoes;

    // Getters e Setters
    public Long getArcoId() {
        return arcoId;
    }

    public void setArcoId(Long arcoId) {
        this.arcoId = arcoId;
    }

    public List<Decisao> getDecisoes() {
        return decisoes;
    }

    public void setDecisoes(List<Decisao> decisoes) {
        this.decisoes = decisoes;
    }
}
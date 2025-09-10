package com.backend.pi.backend.model;

import java.util.List;
import java.util.Map;

public class IniciarJogoRequest {
    
    private Long arcoId;
    private Long usuarioId; 
    private int duracao; 
    private List<Integer> arcosConcluidos; 
    private Map<String, Integer> atributosFinais;
    private Object feedback;
    private List<DadosPorArco> dadosPorArco;

    public Long getArcoId() {
        return arcoId;
    }

    public void setArcoId(Long arcoId) {
        this.arcoId = arcoId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public List<Integer> getArcosConcluidos() {
        return arcosConcluidos;
    }

    public void setArcosConcluidos(List<Integer> arcosConcluidos) {
        this.arcosConcluidos = arcosConcluidos;
    }

    public Map<String, Integer> getAtributosFinais() {
        return atributosFinais;
    }

    public void setAtributosFinais(Map<String, Integer> atributosFinais) {
        this.atributosFinais = atributosFinais;
    }

    public Object getFeedback() {
        return feedback;
    }

    public void setFeedback(Object feedback) {
        this.feedback = feedback;
    }

    public List<DadosPorArco> getDadosPorArco() {
        return dadosPorArco;
    }

    public void setDadosPorArco(List<DadosPorArco> dadosPorArco) {
        this.dadosPorArco = dadosPorArco;
    }
}
package com.backend.pi.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cartas")
public class Carta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private LadoCorreto ladoCorreto;

    @ManyToOne
    @JoinColumn(name = "arco_id")
    @JsonBackReference
    private Arco arco;
    //@JsonIgnore

    @Enumerated(EnumType.STRING)
    private NivelCarta nivelCarta;

    @Enumerated(EnumType.STRING)
    private TipoCarta tipoCarta;

    private String descricao;

    private String urlImagem;

    private String mensagemEsquerda;
    private String mensagemDireita;

    private String nomeCarta;

    private int idCartaDire;
    private int idCartaEsq;

    private String identificador;

    public Carta() {

    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LadoCorreto getLadoCorreto() {
        return ladoCorreto;
    }

    public void setLadoCorreto(LadoCorreto ladoCorreto) {
        this.ladoCorreto = ladoCorreto;
    }

    public NivelCarta getNivelCarta() {
        return nivelCarta;
    }

    public void setNivelCarta(NivelCarta nivelCarta) {
        this.nivelCarta = nivelCarta;
    }

    public TipoCarta getTipoCarta() {
        return tipoCarta;
    }

    public void setTipoCarta(TipoCarta tipoCarta) {
        this.tipoCarta = tipoCarta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Arco getArco(){
        return arco;
    }

    public void setArco(Arco arco){
        this.arco = arco;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public String getMensagemEsquerda() {
        return mensagemEsquerda;
    }

    public void setMensagemEsquerda(String mensagemEsquerda) {
        this.mensagemEsquerda = mensagemEsquerda;
    }

    public String getMensagemDireita() {
        return mensagemDireita;
    }

    public void setMensagemDireita(String mensagemDireita) {
        this.mensagemDireita = mensagemDireita;
    }

    public String getNomeCarta() {
        return nomeCarta;
    }

    public void setNomeCarta(String nomeCarta) {
        this.nomeCarta = nomeCarta;
    }

    public int getIdCartaDire() {
        return idCartaDire;
    }

    public void setIdCartaDire(int idCartaDire) {
        this.idCartaDire = idCartaDire;
    }

    public int getIdCartaEsq() {
        return idCartaEsq;
    }

    public void setIdCartaEsq(int idCartaEsq) {
        this.idCartaEsq = idCartaEsq;
    }

    public String getIdentificador() {
        return identificador;
    }   

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }
}
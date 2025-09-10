package com.backend.pi.backend.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "relatorio_turma")
public class RelatorioTurma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_relatorio_turma")
    private Long id;

    @Column(name = "id_turma", nullable = false)
    private Long idTurma;

    @Column(name = "id_professor")
    private Long idProfessor;
    
    @OneToMany(mappedBy = "relatorioTurmaId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RelatorioAluno> relatoriosAlunos;
    //@Column(name = "media_pontuacao")
    //private float mediaPontuacao;
//
    //@Column(nullable = false)
    //private float desvioPadraoPontuacao;
//
    //@Column(nullable = false)
    //private int maiorPontuacao;
//
    //@Column(nullable = false)
    //private int menorPontuacao;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(Long idTurma) {
        this.idTurma = idTurma;
    }

    public Long getIdProfessor(){
        return idProfessor;
    }

    public void setIdProfessor(long idProfessor){
        this.idProfessor = idProfessor;
    }

    //public float getMediaPontuacao() {
    //    return mediaPontuacao;
    //}
//
    //public void setMediaPontuacao(float mediaPontuacao) {
    //    this.mediaPontuacao = mediaPontuacao;
    //}
//
    //public float getDesvioPadraoPontuacao() {
    //    return desvioPadraoPontuacao;
    //}
//
    //public void setDesvioPadraoPontuacao(float desvioPadraoPontuacao) {
    //    this.desvioPadraoPontuacao = desvioPadraoPontuacao;
    //}
//
    //public int getMaiorPontuacao() {
    //    return maiorPontuacao;
    //}
//
    //public void setMaiorPontuacao(int maiorPontuacao) {
    //    this.maiorPontuacao = maiorPontuacao;
    //}
//
    //public int getMenorPontuacao() {
    //    return menorPontuacao;
    //}
//
    //public void setMenorPontuacao(int menorPontuacao) {
    //    this.menorPontuacao = menorPontuacao;
    //}

    public List<RelatorioAluno> getRelatoriosAlunos() {
        return relatoriosAlunos;
    }

    public void setRelatoriosAlunos(List<RelatorioAluno> relatoriosAlunos) {
        this.relatoriosAlunos = relatoriosAlunos;
    }
}
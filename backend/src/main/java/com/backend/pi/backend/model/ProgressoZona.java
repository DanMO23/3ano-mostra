package com.backend.pi.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "progresso_zona")
public class ProgressoZona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "arco_id", nullable = false)
    private Arco arco; // Arco atuando como Zona do Mapa

    private boolean concluida;

    public ProgressoZona() {
    }

    public ProgressoZona(Aluno aluno, Arco arco, boolean concluida) {
        this.aluno = aluno;
        this.arco = arco;
        this.concluida = concluida;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Arco getArco() {
        return arco;
    }

    public void setArco(Arco arco) {
        this.arco = arco;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }
}

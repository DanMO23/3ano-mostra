package com.backend.pi.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "turma")
public class Turma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_turma")
    private Long id;

    @Column(name = "nome_turma")
    private String nome_turma;

    @Column(name = "codigo_turma")
    private Long codigo_turma;

    @Column(name = "id_professor", nullable = false)
    private Long idProfessor;  // Alteração: apenas o ID do professor

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeTurma() {
        return nome_turma;
    }

    public void setNomeTurma(String nomeTurma) {
        this.nome_turma = nomeTurma;
    }

    public Long getCodigoTurma() {
        return codigo_turma;
    }

    public void setCodigoTurma(Long codigoTurma) {
        this.codigo_turma = codigoTurma;
    }

    public Long getIdProfessor() {
        return idProfessor;  // Método getter para o ID do professor
    }

    public void setIdProfessor(Long idProfessor) {
        this.idProfessor = idProfessor;  // Método setter para o ID do professor
    }
}
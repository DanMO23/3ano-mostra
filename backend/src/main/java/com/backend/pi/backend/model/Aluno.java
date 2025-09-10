package com.backend.pi.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "aluno") 
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aluno") 
    private Long id;
    
    @Column(name = "nome_aluno") 
    private String nome;

    @Column(name = "class_code") 
    private String classCode;

    @Column(name = "id_turma")
    private Long id_turma;

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

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public Long getIdTurma() {
        return id_turma;
    }

    public void setIdTurma(Long idTurma) {
        this.id_turma = idTurma;
    }
}
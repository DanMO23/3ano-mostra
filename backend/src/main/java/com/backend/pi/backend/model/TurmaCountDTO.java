package com.backend.pi.backend.model;

public class TurmaCountDTO {
    private Turma turma;
    private Long alunoCount;

    public TurmaCountDTO(Turma turma, Long alunoCount) {
        this.turma = turma;
        this.alunoCount = alunoCount;
    }

    // Getters e Setters
    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public Long getAlunoCount() {
        return alunoCount;
    }

    public void setAlunoCount(Long alunoCount) {
        this.alunoCount = alunoCount;
    }
}

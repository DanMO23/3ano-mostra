package com.backend.pi.backend.repository;

import com.backend.pi.backend.model.RelatorioTurma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelatorioTurmaRepository extends JpaRepository<RelatorioTurma, Long> {
    List<RelatorioTurma> findByIdTurma(int idTurma);

    // Método para encontrar relatórios de turma por ID do professor
    List<RelatorioTurma> findByIdProfessor(Long idProfessor);
}
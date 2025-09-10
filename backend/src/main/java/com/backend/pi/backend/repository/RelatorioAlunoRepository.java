package com.backend.pi.backend.repository;

import com.backend.pi.backend.model.RelatorioAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface RelatorioAlunoRepository extends JpaRepository<RelatorioAluno, Long> {
   List<RelatorioAluno> findByRelatorioTurmaId(Long relatorioTurmaId);

   @Query("SELECT r FROM RelatorioAluno r LEFT JOIN FETCH r.partidas WHERE r.id = :id")
   Optional<RelatorioAluno> findByIdWithPartidas(@Param("id") Long id);

}
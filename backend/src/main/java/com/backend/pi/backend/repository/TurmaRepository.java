package com.backend.pi.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.pi.backend.model.Turma;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {
    // Método para encontrar turmas de um professor usando id_professor
    @Query("SELECT t FROM Turma t WHERE t.idProfessor = :professorId")
    List<Turma> findTurmasByProfessorId(@Param("professorId") Long professorId);

    // Método para verificar se existe uma turma com o código especificado usando @Query
    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Turma t WHERE t.codigo_turma = :codigoTurma")
    boolean existsByCodigoTurma(@Param("codigoTurma") Long codigoTurma);

    // Método para contar alunos em cada turma de um professor
    @Query("SELECT t, COUNT(a) FROM Turma t LEFT JOIN Aluno a ON t.id = a.id_turma WHERE t.idProfessor = :professorId GROUP BY t.id")
    List<Object[]> findTurmasWithAlunoCountByProfessorId(@Param("professorId") Long professorId);

    // Método para buscar uma turma pelo classCode
    @Query("SELECT t FROM Turma t WHERE t.codigo_turma = :codigoTurma")
    Optional<Turma> findByCodigoTurma(@Param("codigoTurma") String codigoTurma);

}


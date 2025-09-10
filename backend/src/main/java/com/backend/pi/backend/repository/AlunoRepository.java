package com.backend.pi.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.pi.backend.model.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
   Optional<Aluno> findByClassCode(String classCode);

   // List<Aluno> findByTurmaId(Long turmaId);
}
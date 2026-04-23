package com.backend.pi.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.pi.backend.model.InventarioAluno;
import java.util.List;

@Repository
public interface InventarioAlunoRepository extends JpaRepository<InventarioAluno, Long> {
    List<InventarioAluno> findByAlunoId(Long alunoId);
}

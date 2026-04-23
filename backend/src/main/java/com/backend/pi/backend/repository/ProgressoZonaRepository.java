package com.backend.pi.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.pi.backend.model.ProgressoZona;
import java.util.List;

@Repository
public interface ProgressoZonaRepository extends JpaRepository<ProgressoZona, Long> {
    List<ProgressoZona> findByAlunoId(Long alunoId);
}

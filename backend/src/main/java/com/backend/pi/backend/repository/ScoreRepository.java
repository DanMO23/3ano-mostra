package com.backend.pi.backend.repository;

import com.backend.pi.backend.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    // Você pode adicionar métodos personalizados aqui, se necessário
}
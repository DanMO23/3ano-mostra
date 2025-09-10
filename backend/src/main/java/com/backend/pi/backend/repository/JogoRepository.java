package com.backend.pi.backend.repository;

import com.backend.pi.backend.model.Jogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JogoRepository extends JpaRepository<Jogo, Long> {
    // Métodos personalizados podem ser adicionados aqui, se necessário
}
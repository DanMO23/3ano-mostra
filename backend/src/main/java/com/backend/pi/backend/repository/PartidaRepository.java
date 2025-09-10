package com.backend.pi.backend.repository;

import com.backend.pi.backend.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long> {
    // Você pode adicionar métodos personalizados aqui, se necessário
}
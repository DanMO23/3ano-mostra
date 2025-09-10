package com.backend.pi.backend.repository;

import com.backend.pi.backend.model.Carta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartaRepository extends JpaRepository<Carta, Long> {
    List<Carta> findByArcoId(Long arcoId); // Aceita um objeto Arco
}
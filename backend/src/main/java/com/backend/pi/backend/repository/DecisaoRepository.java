package com.backend.pi.backend.repository;

import com.backend.pi.backend.model.Decisao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DecisaoRepository extends JpaRepository<Decisao, Long> {
    // Você pode adicionar métodos personalizados aqui, se necessário
}
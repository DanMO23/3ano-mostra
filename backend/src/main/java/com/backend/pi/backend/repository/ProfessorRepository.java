package com.backend.pi.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.pi.backend.model.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Professor findByEmail(String email);
}

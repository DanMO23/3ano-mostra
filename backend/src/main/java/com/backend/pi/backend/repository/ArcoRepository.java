package com.backend.pi.backend.repository;

import com.backend.pi.backend.model.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArcoRepository extends JpaRepository<Arco, Long> {

}
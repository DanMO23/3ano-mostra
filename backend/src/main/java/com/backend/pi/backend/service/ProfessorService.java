package com.backend.pi.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.backend.pi.backend.model.Professor;
import com.backend.pi.backend.repository.ProfessorRepository;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Professor saveProfessor(Professor professor) {
        if (professorRepository.findByEmail(professor.getEmail()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já cadastrado.");
        }

        professor.setSenha(passwordEncoder.encode(professor.getSenha()));
        return professorRepository.save(professor);
    }

    public Professor validateProfessorLogin(String email, String senha) {
        Professor professor = professorRepository.findByEmail(email);
        if (professor != null && passwordEncoder.matches(senha, professor.getSenha())) {
            return professor;
        }
        return null;
    }

    public List<Professor> getAllProfessores() {
        return professorRepository.findAll();
    }

    public Professor getProfessorById(Long id) {
        return professorRepository.findById(id).orElse(null);
    }

    public void deleteProfessor(Long id) {
        professorRepository.deleteById(id);
    }
}
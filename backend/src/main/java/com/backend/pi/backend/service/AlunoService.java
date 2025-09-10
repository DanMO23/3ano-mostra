package com.backend.pi.backend.service;

import com.backend.pi.backend.model.Aluno;
import com.backend.pi.backend.model.Turma; // Importar a entidade Turma
import com.backend.pi.backend.repository.AlunoRepository;
import com.backend.pi.backend.repository.TurmaRepository; // Importar o repositório de Turma
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private TurmaRepository turmaRepository; // Injetar o repositório de Turma

    public Aluno registrarAluno(Aluno aluno) {
        // Buscar a turma pelo classCode
        Optional<Turma> turmaOpt = turmaRepository.findByCodigoTurma(aluno.getClassCode());

        if (turmaOpt.isPresent()) {
            // Se a turma existir, definir o id_turma no aluno
            aluno.setIdTurma(turmaOpt.get().getId());
            return alunoRepository.save(aluno); // Salvar o aluno
        } else {
            throw new RuntimeException("Class Code não encontrado.");
        }
    }

    public List<Aluno> listarAlunos() {
        return alunoRepository.findAll();
    }

    public Aluno encontrarPorId(Long id) {
        return alunoRepository.findById(id).orElse(null);
    }
}
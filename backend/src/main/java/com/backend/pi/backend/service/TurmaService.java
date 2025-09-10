package com.backend.pi.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.pi.backend.model.Professor;
import com.backend.pi.backend.model.Turma;
import com.backend.pi.backend.model.TurmaCountDTO;
import com.backend.pi.backend.repository.AlunoRepository;
import com.backend.pi.backend.repository.ProfessorRepository;
import com.backend.pi.backend.repository.TurmaRepository;

@Service
public class TurmaService {

   private final TurmaRepository turmaRepository;

   @Autowired
   private ProfessorRepository professorRepository;

   @Autowired
    private AlunoRepository alunoRepository;

   @Autowired
   public TurmaService(TurmaRepository turmaRepository) {
      this.turmaRepository = turmaRepository;
   }

   public List<Turma> getAllTurmas() {
      return turmaRepository.findAll();
   }

   public Optional<Turma> findById(Long id) {
      return turmaRepository.findById(id);
   }

   public Turma save(Turma turma) {
      return turmaRepository.save(turma);
   }

   public void deleteById(Long id) {
      turmaRepository.deleteById(id);
   }

   public Turma createTurma(Turma turma) {
      // Verifica se o ID do professor está presente
      if (turma.getIdProfessor() == null) {
         throw new IllegalArgumentException("ID do professor não fornecido.");
      }

      // Verifica se já existe uma turma com o mesmo código
      if (turmaRepository.existsByCodigoTurma(turma.getCodigoTurma())) {
         throw new IllegalArgumentException("Código da turma já existe: " + turma.getCodigoTurma());
      }

      Optional<Professor> professorOptional = professorRepository.findById(turma.getIdProfessor());
      if (!professorOptional.isPresent()) {
         throw new IllegalArgumentException("Professor não encontrado.");
      }

      return turmaRepository.save(turma);
   }

   // public Turma updateById(Long id, Turma turma) {
   //
   // return turmaRepository.findById(id).map(existingTurma -> {
   // if (turma.getNomeTurma() != null && !turma.getNomeTurma().isEmpty()) {
   // existingTurma.setNomeTurma(turma.getNomeTurma());
   // }
   // if (turma.getCodigoTurma() != null && !turma.getCodigoTurma().isEmpty()) {
   // existingTurma.setCodigoTurma(turma.getCodigoTurma());
   // }
   // if (turma.getIdProfessor() != null && turma.getIdProfessor() > 0) {
   // existingTurma.setIdProfessor(turma.getIdProfessor());
   // }
   //
   // return turmaRepository.save(existingTurma);
   // }).orElseThrow(() -> new ResourceNotFoundException("Turma não encontrada com
   // ID: " + id));
   // }

   public List<Turma> getTurmasByProfessor(Long professorId) {
      return turmaRepository.findTurmasByProfessorId(professorId);
   }

   // Função para obter turmas e contagem de alunos
   public List<TurmaCountDTO> getTurmasWithAlunoCount(Long professorId) {
      List<Object[]> results = turmaRepository.findTurmasWithAlunoCountByProfessorId(professorId);
      List<TurmaCountDTO> turmaCountList = new ArrayList<>();

      for (Object[] result : results) {
         Turma turma = (Turma) result[0];
         Long alunoCount = (Long) result[1];
         turmaCountList.add(new TurmaCountDTO(turma, alunoCount));
      }
      return turmaCountList;
   }

   // Método para buscar uma turma pelo classCode
   public Optional<Turma> buscarTurmaPorCodigo(String codigoTurma) {
      return turmaRepository.findByCodigoTurma(codigoTurma);
   }

   // public List<Aluno> findAlunosByTurmaId(Long turmaId) {
   //      return alunoRepository.findByTurmaId(turmaId);
   //  }
}

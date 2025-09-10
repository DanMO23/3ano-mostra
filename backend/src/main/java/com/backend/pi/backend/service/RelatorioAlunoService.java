package com.backend.pi.backend.service;

import com.backend.pi.backend.model.Decisao;
import com.backend.pi.backend.model.Partida;
import com.backend.pi.backend.model.RelatorioAluno;
import com.backend.pi.backend.repository.RelatorioAlunoRepository;
import com.backend.pi.backend.repository.RelatorioTurmaRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RelatorioAlunoService {

   @Autowired
   private RelatorioAlunoRepository relatorioAlunoRepository;

   @Autowired
   private RelatorioTurmaRepository relatorioTurmaRepository;

   public RelatorioAluno salvar(RelatorioAluno relatorioAluno) {
      // Verifica se o objeto relatorioAluno não é nulo
      if (relatorioAluno == null) {
         throw new IllegalArgumentException("Relatório de Aluno não pode ser nulo.");
      }

      // Verifica se o ID do relatorioTurma não é nulo
      Long relatorioTurmaId = relatorioAluno.getRelatorioTurmaId();
      if (relatorioTurmaId == null) {
         throw new IllegalArgumentException("ID do Relatório de Turma não pode ser nulo.");
      }

      // Verifica se o relatorioTurma existe
      if (!relatorioTurmaRepository.existsById(relatorioTurmaId)) {
         throw new EntityNotFoundException("Relatório de Turma com ID " + relatorioTurmaId + " não encontrado.");
      }

      // Salva e retorna o relatório de aluno
      return relatorioAlunoRepository.save(relatorioAluno);
   }

   // Listar todos os relatórios de aluno
   public List<RelatorioAluno> listar() {
      return relatorioAlunoRepository.findAll();
   }

   // Encontrar um relatório de aluno por ID
   @Transactional(readOnly = true)
   public RelatorioAluno encontrarPorId(Long id) {
      return relatorioAlunoRepository.findByIdWithPartidas(id).orElse(null);
   }

   // Deletar um relatório de aluno
   public void deletar(Long id) {
      relatorioAlunoRepository.deleteById(id);
   }

   // Encontrar relatórios de aluno por ID do relatório de turma
   public List<RelatorioAluno> encontrarPorRelatorioTurma(Long relatorioTurmaId) {
      return relatorioAlunoRepository.findByRelatorioTurmaId(relatorioTurmaId);
   }

   // Listar relatórios de aluno por ID da turma
   public List<RelatorioAluno> listarPorTurmaId(Long turmaId) {
      return relatorioAlunoRepository.findByRelatorioTurmaId(turmaId);
   }

   // Adicionar uma partida a um relatório de aluno
   public void adicionarPartida(Long relatorioAlunoId, Partida partida) {
      RelatorioAluno relatorioAluno = encontrarPorId(relatorioAlunoId);
      if (relatorioAluno != null) {
         relatorioAluno.getPartidas().add(partida); // Adicione a partida à lista
         relatorioAlunoRepository.save(relatorioAluno); // Salve as alterações
      } else {
         throw new IllegalArgumentException("Relatório de Aluno não encontrado.");
      }
   }

   // Listar partidas de um relatório de aluno
   public List<Partida> listarPartidas(Long relatorioAlunoId) {
      RelatorioAluno relatorioAluno = encontrarPorId(relatorioAlunoId);
      if (relatorioAluno != null) {
         return relatorioAluno.getPartidas();
      }
      throw new IllegalArgumentException("Relatório de Aluno não encontrado.");
   }

   // Remover uma partida de um relatório de aluno
   public void removerPartida(Long relatorioAlunoId, Long partidaId) {
      RelatorioAluno relatorioAluno = encontrarPorId(relatorioAlunoId);
      if (relatorioAluno != null) {
         relatorioAluno.getPartidas().removeIf(partida -> partida.getId().equals(partidaId));
         relatorioAlunoRepository.save(relatorioAluno); // Salve as alterações
      } else {
         throw new IllegalArgumentException("Relatório de Aluno não encontrado.");
      }
   }

   // Listar decisões de uma partida de um aluno
   public List<Decisao> listarDecisoes(Long alunoId, Long partidaId) {
      RelatorioAluno relatorioAluno = encontrarPorId(alunoId);
      if (relatorioAluno != null) {
         return relatorioAluno.getPartidas().stream()
               .filter(partida -> partida.getId().equals(partidaId))
               .flatMap(partida -> partida.getDecisoes().stream()) // Supondo que Partida tem um método
                                                                   // getDecisoes()
               .toList();
      }
      throw new IllegalArgumentException("Relatório de Aluno não encontrado.");
   }
}
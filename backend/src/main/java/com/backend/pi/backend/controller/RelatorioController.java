package com.backend.pi.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin; // Supondo que você tenha uma classe Decisao
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.pi.backend.model.Decisao;
import com.backend.pi.backend.model.Partida;
import com.backend.pi.backend.model.RelatorioAluno;
import com.backend.pi.backend.model.RelatorioTurma;
import com.backend.pi.backend.service.RelatorioAlunoService;
import com.backend.pi.backend.service.RelatorioTurmaService;

@RestController
@RequestMapping("/api/relatorios")
@CrossOrigin(origins = {"http://localhost:3000", "https://68c1c23d19f4d8c28d979090--integrador-3ano-mostra.netlify.app"})
public class RelatorioController {

   @Autowired
   private RelatorioTurmaService relatorioTurmaService;

   @Autowired
   private RelatorioAlunoService relatorioAlunoService;

   // Endpoint para salvar um novo relatório de turma
   @PostMapping("/turma")
   public ResponseEntity<RelatorioTurma> criarRelatorioTurma(@RequestBody RelatorioTurma relatorioTurma) {
      RelatorioTurma novoRelatorio = relatorioTurmaService.salvar(relatorioTurma);
      return ResponseEntity.ok(novoRelatorio);
   }

   // Endpoint para listar todos os relatórios de turma
   @GetMapping("/turma")
   public ResponseEntity<List<RelatorioTurma>> listarRelatoriosTurma() {
      List<RelatorioTurma> relatorios = relatorioTurmaService.listar();
      return ResponseEntity.ok(relatorios);
   }

   // Endpoint para encontrar um relatório de turma por ID
   @GetMapping("/turma/{id}")
   public ResponseEntity<RelatorioTurma> encontrarRelatorioTurmaPorId(@PathVariable Long id) {
      RelatorioTurma relatorio = relatorioTurmaService.encontrarPorId(id);
      return relatorio != null ? ResponseEntity.ok(relatorio) : ResponseEntity.notFound().build();
   }

   // Endpoint para deletar um relatório de turma
   @DeleteMapping("/turma/{id}")
   public ResponseEntity<Void> deletarRelatorioTurma(@PathVariable Long id) {
      relatorioTurmaService.deletar(id);
      return ResponseEntity.noContent().build();
   }

   // Endpoint para mostrar os relatórios de turma por id do professor
   @GetMapping("/turma/professor/{idProfessor}")
   public ResponseEntity<List<RelatorioTurma>> listarRelatoriosTurmaPorProfessor(@PathVariable Long idProfessor) {
      List<RelatorioTurma> relatorios = relatorioTurmaService.listarPorProfessorId(idProfessor);
      return ResponseEntity.ok(relatorios);
   }

   // Endpoint para salvar um novo relatório de aluno
   @PostMapping("/aluno")
   public ResponseEntity<RelatorioAluno> criarRelatorioAluno(@RequestBody RelatorioAluno relatorioAluno) {
      RelatorioAluno novoRelatorio = relatorioAlunoService.salvar(relatorioAluno);
      return ResponseEntity.ok(novoRelatorio);
   }

   // Endpoint para listar todos os relatórios de aluno
   @GetMapping("/aluno")
   public ResponseEntity<List<RelatorioAluno>> listarRelatoriosAluno() {
      List<RelatorioAluno> relatorios = relatorioAlunoService.listar();
      return ResponseEntity.ok(relatorios);
   }

   // Endpoint para encontrar um relatório de aluno por ID
   @GetMapping("/aluno/{id}")
   public ResponseEntity<RelatorioAluno> encontrarRelatorioAlunoPorId(@PathVariable Long id) {
      RelatorioAluno relatorio = relatorioAlunoService.encontrarPorId(id);
      return relatorio != null ? ResponseEntity.ok(relatorio) : ResponseEntity.notFound().build();
   }

   // Endpoint para deletar um relatório de aluno
   @DeleteMapping("/aluno/{id}")
   public ResponseEntity<Void> deletarRelatorioAluno(@PathVariable Long id) {
      relatorioAlunoService.deletar(id);
      return ResponseEntity.noContent().build();
   }

   // Endpoint para mostrar os relatórios de alunos de uma turma
   @GetMapping("/aluno/turma/{turmaId}")
   public ResponseEntity<List<RelatorioAluno>> listarRelatoriosAlunoPorTurma(@PathVariable Long turmaId) {
      List<RelatorioAluno> relatorios = relatorioAlunoService.listarPorTurmaId(turmaId);
      return ResponseEntity.ok(relatorios);
   }

   // Endpoint para listar partidas de um relatório de aluno
   @GetMapping("/aluno/{id}/partidas")
   public ResponseEntity<List<Partida>> listarPartidas(@PathVariable Long id) {
      List<Partida> partidas = relatorioAlunoService.listarPartidas(id);
      return ResponseEntity.ok(partidas);
   }

   // Endpoint para mostrar a lista de decisões de uma determinada partida de um
   // aluno
   @GetMapping("/aluno/{alunoId}/partida/{partidaId}/decisoes")
   public ResponseEntity<List<Decisao>> listarDecisoes(@PathVariable Long alunoId, @PathVariable Long partidaId) {
      List<Decisao> decisoes = relatorioAlunoService.listarDecisoes(alunoId, partidaId);
      return ResponseEntity.ok(decisoes);
   }
}
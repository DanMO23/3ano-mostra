package com.backend.pi.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.pi.backend.model.RelatorioTurma;
import com.backend.pi.backend.model.Turma;
import com.backend.pi.backend.model.TurmaCountDTO;
import com.backend.pi.backend.service.RelatorioTurmaService;
import com.backend.pi.backend.service.TurmaService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/api/turmas")
@CrossOrigin(origins = "*")
public class TurmaController {

   @Autowired
   private TurmaService turmaService;

   @Autowired
   private RelatorioTurmaService relatorioTurmaService;

   @Autowired
   public TurmaController(TurmaService turmaService) {
      this.turmaService = turmaService;
   }

   @GetMapping
   public ResponseEntity<List<Turma>> getAllTurmas() {
      List<Turma> turmas = turmaService.getAllTurmas();
      return ResponseEntity.ok(turmas);
   }

   @GetMapping("/{id}")
   public ResponseEntity<Turma> getTurmaById(@PathVariable Long id) {
      return turmaService.findById(id)
            .map(turma -> ResponseEntity.ok().body(turma))
            .orElse(ResponseEntity.notFound().build());
   }

   @PostMapping
   public ResponseEntity<?> createTurma(@RequestBody Turma turma) throws JsonProcessingException {
      try {
         // 1. Criar a nova turma
         Turma novaTurma = turmaService.createTurma(turma);

         // 2. Criar o RelatorioTurma
         RelatorioTurma relatorioTurma = new RelatorioTurma();
         relatorioTurma.setIdTurma(novaTurma.getId()); // Associe o ID da nova turma
         relatorioTurma.setIdProfessor(turma.getIdProfessor()); // Ou outro campo que você tenha

         // 3. Salvar o RelatorioTurma usando o serviço existente
         relatorioTurmaService.salvar(relatorioTurma);

         // 4. Retornar a resposta com os dados da turma e do relatório
         return ResponseEntity.status(HttpStatus.CREATED).body(novaTurma);
      } catch (IllegalArgumentException e) {
         return ResponseEntity.badRequest().body(e.getMessage());
      } catch (DataAccessException e) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
               .body("Erro ao acessar o banco de dados: " + e.getMessage());
      } catch (Exception e) {
         e.printStackTrace(); // Para depuração
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
               .body("Erro ao criar a turma: " + e.getMessage());
      }
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deleteTurma(@PathVariable Long id) {
      turmaService.deleteById(id);
      return ResponseEntity.noContent().build();
   }

   // @PutMapping("/{id}")
   // public ResponseEntity<Turma> updateTurma(@PathVariable Long id, @RequestBody
   // Turma turma) {
   // Turma updatedTurma = turmaService.updateById(id, turma);
   // return ResponseEntity.ok().body(updatedTurma);
   // }

   @GetMapping("/professor/{professorId}")
   public ResponseEntity<List<Turma>> getTurmasByProfessor(@PathVariable Long professorId) {
      List<Turma> turmas = turmaService.getTurmasByProfessor(professorId);
      return ResponseEntity.ok(turmas);
   }

   // Endpoint para obter turmas com contagem de alunos
   @GetMapping("/professor/{professorId}/alunos")
   public ResponseEntity<List<TurmaCountDTO>> getTurmasWithAlunoCount(@PathVariable Long professorId) {
      List<TurmaCountDTO> turmasComContagem = turmaService.getTurmasWithAlunoCount(professorId);
      return ResponseEntity.ok(turmasComContagem);
   }

   // Rota para buscar todos os alunos de uma turma
   //  @GetMapping("/{turmaId}/alunos")
   //  public ResponseEntity<List<Aluno>> getAlunosByTurma(@PathVariable Long turmaId) {
   //      List<Aluno> alunos = turmaService.findAlunosByTurmaId(turmaId);
   //      return ResponseEntity.ok(alunos);
   //  }
}

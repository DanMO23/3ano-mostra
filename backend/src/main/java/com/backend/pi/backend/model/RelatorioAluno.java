package com.backend.pi.backend.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "relatorio_aluno")
public class RelatorioAluno {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id_relatorio_aluno")
   private Long id;

   @JoinColumn(name = "id_relatorio_turma", nullable = false)
   private Long relatorioTurmaId;

   @Column(name = "id_aluno", nullable = false)
   private Long idAluno; // Referência ao aluno

   @OneToMany(mappedBy = "relatorioAlunoId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   private List<Partida> partidas; // Lista de partidas

   // Getters e Setters
   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getRelatorioTurmaId() {
      return relatorioTurmaId;
   }

   public void setRelatorioTurmaId(Long relatorioTurmaId) {
      this.relatorioTurmaId = relatorioTurmaId;
   }

   public Long getIdAluno() {
      return idAluno;
   }

   public void setIdAluno(Long idAluno) {
      this.idAluno = idAluno;
   }

   public List<Partida> getPartidas() {
      return partidas;
   }

   public void setPartidas(List<Partida> partidas) {
      this.partidas = partidas;
   }
}
package com.backend.pi.backend.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "partidas") // Certifique-se de que o nome da tabela está correto
public class Partida {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id_partida")
   private Long id;

   @OneToMany(cascade = CascadeType.ALL)
   @JoinColumn(name = "id_partida")
   private List<Decisao> decisoes;

   @OneToMany(cascade = CascadeType.ALL)
   @JoinColumn(name = "id_partida")
   private List<Score> scores;

   @Column(name = "id_relatorio_aluno") // Armazenando apenas o ID do RelatorioAluno
   private Long relatorioAlunoId;

   @Column(name = "id_aluno")
   private Long alunoId; // Referência ao ID do aluno

   @Column(name = "duracao_partida")
   private int duracaoPartida;

   // Getters e Setters
   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public List<Decisao> getDecisoes() {
      return decisoes;
   }

   public void setDecisoes(List<Decisao> decisoes) {
      this.decisoes = decisoes;
   }

   public List<Score> getScores() {
      return scores;
   }

   public void setScores(List<Score> scores) {
      this.scores = scores;
   }

   public Long getRelatorioAlunoId() {
      return relatorioAlunoId;
   }

   public void setRelatorioAlunoId(Long relatorioAlunoId) {
      this.relatorioAlunoId = relatorioAlunoId;
   }

   public Long getAlunoId() {
      return alunoId;
   }

   public void setAlunoId(Long alunoId) {
      this.alunoId = alunoId;
   }

   public int getDuracaoPartida() {
      return duracaoPartida;
   }

   public void setDuracaoPartida(int duracaoPartida) {
      this.duracaoPartida = duracaoPartida;
   }
}
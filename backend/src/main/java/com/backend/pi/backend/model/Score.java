package com.backend.pi.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "score")
public class Score {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id_score")
   private Long id;

   @Column(name = "valor_score")
   private int valorScore;

   @Enumerated(EnumType.STRING)
   private TipoScore tipoScore;

   // Construtor padrão
   public Score() {
   }

   // Construtor com parâmetros
   public Score(int valorScore, TipoScore tipoScore) {
      this.valorScore = valorScore;
      this.tipoScore = tipoScore;
   }

   // Getters e Setters
   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public int getValorScore() {
      return valorScore;
   }

   public void setValorScore(int valorScore) {
      this.valorScore = valorScore;
   }

   public TipoScore getTipoScore() {
      return tipoScore;
   }

   public void setTipoScore(TipoScore tipoScore) {
      this.tipoScore = tipoScore;
   }
}
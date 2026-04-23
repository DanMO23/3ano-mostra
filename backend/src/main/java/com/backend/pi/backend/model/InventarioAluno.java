package com.backend.pi.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "inventario_aluno")
public class InventarioAluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private ItemInventario item;

    public InventarioAluno() {
    }

    public InventarioAluno(Aluno aluno, ItemInventario item) {
        this.aluno = aluno;
        this.item = item;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public ItemInventario getItem() {
        return item;
    }

    public void setItem(ItemInventario item) {
        this.item = item;
    }
}

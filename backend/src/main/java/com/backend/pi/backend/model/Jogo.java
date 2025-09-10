package com.backend.pi.backend.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
public class Jogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Arco> conjuntoArcos = new ArrayList<>();

    public Jogo() {
        this.conjuntoArcos = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public List<Arco> getConjuntoArcos() {
        return conjuntoArcos;
    }

    public void setConjuntoArcos(List<Arco> conjuntoArcos) {
        this.conjuntoArcos = conjuntoArcos;
    }
}
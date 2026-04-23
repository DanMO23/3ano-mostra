package com.backend.pi.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "item_inventario")
public class ItemInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    
    @Column(name = "icone_url")
    private String iconeUrl;

    @Column(name = "tipo_item")
    private String tipoItem; // ex: ESCUDO, LUPA, CHAVE

    public ItemInventario() {
    }

    public ItemInventario(String nome, String descricao, String iconeUrl, String tipoItem) {
        this.nome = nome;
        this.descricao = descricao;
        this.iconeUrl = iconeUrl;
        this.tipoItem = tipoItem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getIconeUrl() {
        return iconeUrl;
    }

    public void setIconeUrl(String iconeUrl) {
        this.iconeUrl = iconeUrl;
    }

    public String getTipoItem() {
        return tipoItem;
    }

    public void setTipoItem(String tipoItem) {
        this.tipoItem = tipoItem;
    }
}

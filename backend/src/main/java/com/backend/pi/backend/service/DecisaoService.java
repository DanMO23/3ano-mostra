package com.backend.pi.backend.service;

import com.backend.pi.backend.model.Decisao;
import com.backend.pi.backend.repository.DecisaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DecisaoService {

    @Autowired
    private DecisaoRepository decisaoRepository;

    public Decisao salvarDecisao(Decisao decisao) {
        return decisaoRepository.save(decisao);
    }

    public List<Decisao> listarDecisoes() {
        return decisaoRepository.findAll();
    }

    public Decisao encontrarDecisaoPorId(Long id) {
        return decisaoRepository.findById(id).orElse(null);
    }

    public void deletarDecisao(Long id) {
        decisaoRepository.deleteById(id);
    }
}
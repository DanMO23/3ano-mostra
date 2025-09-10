package com.backend.pi.backend.service;

import com.backend.pi.backend.model.Jogo;
import com.backend.pi.backend.repository.JogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JogoService {

    @Autowired
    private JogoRepository jogoRepository;

    public Jogo criarJogo(Jogo jogo) {
        return jogoRepository.save(jogo);
    }

    public List<Jogo> listarJogos() {
        return jogoRepository.findAll();
    }

    public void deletarJogo(Long id) {
        jogoRepository.deleteById(id);
    }

    public Jogo encontrarJogoPorId(Long id) {
        return jogoRepository.findById(id).orElse(null);
    }
}
package com.backend.pi.backend.service;

import com.backend.pi.backend.model.Partida;
import com.backend.pi.backend.repository.PartidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartidaService {

    @Autowired
    private PartidaRepository partidaRepository;

    public Partida criarPartida(Partida partida) {
        return partidaRepository.save(partida);
    }

    public List<Partida> listarPartidas() {
        return partidaRepository.findAll();
    }

    public Partida encontrarPartidaPorId(Long id) {
        return partidaRepository.findById(id).orElse(null);
    }

    public void deletarPartida(Long id) {
        partidaRepository.deleteById(id);
    }
}
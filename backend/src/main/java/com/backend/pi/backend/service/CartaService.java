package com.backend.pi.backend.service;

import com.backend.pi.backend.model.Carta;
import com.backend.pi.backend.repository.CartaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartaService {
    
    @Autowired 
    private CartaRepository cartaRepository;

    // Método para listar todas as cartas
    public List<Carta> listarCartas() {
        return cartaRepository.findAll();
    }

    // Método para adicionar uma nova carta
    public Carta adicionarCarta(Carta carta) {
        if (carta == null) {
            throw new IllegalArgumentException("Carta não pode ser nula.");
        }
        return cartaRepository.save(carta);
    }

    // Método para buscar uma carta pelo ID
    public Carta buscarCartaPorId(Long id) {
        return cartaRepository.findById(id).orElse(null); // Retorna null se não encontrar
    }

    // Método para remover uma carta pelo ID
    public void removerCarta(Long id) {
        if (!cartaRepository.existsById(id)) {
            throw new IllegalArgumentException("Carta com ID " + id + " não encontrada.");
        }
        cartaRepository.deleteById(id);
    }

    // Método para listar cartas por arco
    public List<Carta> listarCartasPorArco(Long arcoId) {
        return cartaRepository.findByArcoId(arcoId);
    }
}
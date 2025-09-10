package com.backend.pi.backend.service;

import com.backend.pi.backend.model.Arco;
import com.backend.pi.backend.model.Carta;
import com.backend.pi.backend.model.Feedback;
import com.backend.pi.backend.repository.ArcoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArcoService {

    @Autowired
    private ArcoRepository arcoRepository;

    // Método para criar e salvar um arco
    public Arco criarArco(Arco arco) {
        if (arco == null) {
            throw new IllegalArgumentException("Arco não pode ser nulo.");
        }
        return arcoRepository.save(arco);
    }

    // Método para buscar todos os arcos
    public List<Arco> buscarArcos() {
        return arcoRepository.findAll();
    }

    // Método para encontrar um arco por ID
    public Arco encontrarArcoPorId(Long id) {
        return arcoRepository.findById(id).orElse(null);
    }

    // Método para deletar um arco
    public void deletarArco(Long id) {
        arcoRepository.deleteById(id);
    }

    // Método para criar um arco com cartas e feedbacks
    public Arco criarArcoComCartasEFeedbacks(List<Carta> cartas, List<Feedback> feedbacks) {
        if (cartas == null || feedbacks == null) {
            throw new IllegalArgumentException("Cartas e feedbacks não podem ser nulos.");
        }
        
        Arco arco = new Arco();
        arco.setConjuntoCartas(cartas); // Supondo que você tenha um método para setar cartas
        arco.setConjuntoFeedback(feedbacks); // Supondo que você tenha um método para setar feedbacks
        return arcoRepository.save(arco); // Salva o arco criado
    }

    // Método para finalizar um arco
    public void finalizarArco(Arco arco) {
        if (arco == null) {
            throw new IllegalArgumentException("Arco não pode ser nulo.");
        }
        arco.finalizarArco();
        arcoRepository.save(arco); // Salva o estado final do arco, se necessário
    }
}
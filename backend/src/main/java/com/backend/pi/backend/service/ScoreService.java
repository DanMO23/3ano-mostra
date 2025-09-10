package com.backend.pi.backend.service;

import com.backend.pi.backend.model.Score;
import com.backend.pi.backend.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    public Score criarScore(Score score) {
        return scoreRepository.save(score);
    }

    public List<Score> listarScores() {
        return scoreRepository.findAll();
    }

    public Score encontrarScorePorId(Long id) {
        return scoreRepository.findById(id).orElse(null);
    }

    public void deletarScore(Long id) {
        scoreRepository.deleteById(id);
    }
}
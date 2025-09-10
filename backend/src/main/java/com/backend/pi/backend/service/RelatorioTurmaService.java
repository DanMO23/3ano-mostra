package com.backend.pi.backend.service;

import com.backend.pi.backend.model.RelatorioTurma;
import com.backend.pi.backend.model.RelatorioAluno;
import com.backend.pi.backend.repository.RelatorioTurmaRepository;
import com.backend.pi.backend.repository.RelatorioAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelatorioTurmaService {

    @Autowired
    private RelatorioTurmaRepository relatorioTurmaRepository;

    @Autowired
    private RelatorioAlunoRepository relatorioAlunoRepository;

    // Salvar um novo relatório de turma
    public RelatorioTurma salvar(RelatorioTurma relatorioTurma) {
        return relatorioTurmaRepository.save(relatorioTurma);
    }

    // Listar todos os relatórios de turma
    public List<RelatorioTurma> listar() {
        return relatorioTurmaRepository.findAll();
    }

    // Encontrar um relatório de turma por ID
    public RelatorioTurma encontrarPorId(Long id) {
        return relatorioTurmaRepository.findById(id).orElse(null);
    }

    // Deletar um relatório de turma
    public void deletar(Long id) {
        relatorioTurmaRepository.deleteById(id);
    }

    // Listar relatórios de turma por ID do professor
    public List<RelatorioTurma> listarPorProfessorId(Long idProfessor) {
        return relatorioTurmaRepository.findByIdProfessor(idProfessor);
    }

    // Calcular a média de pontuação dos alunos associados
    public float calcularMediaPontuacao(Long relatorioTurmaId) {
        List<RelatorioAluno> relatoriosAlunos = relatorioAlunoRepository.findByRelatorioTurmaId(relatorioTurmaId);
        return (float) relatoriosAlunos.stream()
                .flatMap(aluno -> aluno.getPartidas().stream()) // Acessa as partidas de cada aluno
                .flatMap(partida -> partida.getScores().stream()) // Acessa os scores de cada partida
                .mapToInt(score -> score.getValorScore()) // Supondo que `getValorScore()` retorna o valor do score
                .average()
                .orElse(0);
    }

    // Calcular a maior pontuação dos alunos associados
    public int calcularMaiorPontuacao(Long relatorioTurmaId) {
        List<RelatorioAluno> relatoriosAlunos = relatorioAlunoRepository.findByRelatorioTurmaId(relatorioTurmaId);
        return relatoriosAlunos.stream()
                .flatMap(aluno -> aluno.getPartidas().stream())
                .flatMap(partida -> partida.getScores().stream())
                .mapToInt(score -> score.getValorScore())
                .max()
                .orElse(0);
    }

    // Calcular a menor pontuação dos alunos associados
    public int calcularMenorPontuacao(Long relatorioTurmaId) {
        List<RelatorioAluno> relatoriosAlunos = relatorioAlunoRepository.findByRelatorioTurmaId(relatorioTurmaId);
        return relatoriosAlunos.stream()
                .flatMap(aluno -> aluno.getPartidas().stream())
                .flatMap(partida -> partida.getScores().stream())
                .mapToInt(score -> score.getValorScore())
                .min()
                .orElse(0);
    }

    // Calcular o desvio padrão das pontuações dos alunos associados
    public float calcularDesvioPadraoPontuacao(Long relatorioTurmaId) {
        List<RelatorioAluno> relatoriosAlunos = relatorioAlunoRepository.findByRelatorioTurmaId(relatorioTurmaId);
        List<Integer> pontuacoes = relatoriosAlunos.stream()
                .flatMap(aluno -> aluno.getPartidas().stream())
                .flatMap(partida -> partida.getScores().stream())
                .map(score -> score.getValorScore())
                .toList();

        double mean = pontuacoes.stream().mapToInt(Integer::intValue).average().orElse(0);
        return (float) Math.sqrt(pontuacoes.stream()
                .mapToDouble(p -> Math.pow(p - mean, 2))
                .average()
                .orElse(0));
    }
}
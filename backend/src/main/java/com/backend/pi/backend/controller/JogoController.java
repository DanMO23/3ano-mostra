package com.backend.pi.backend.controller;

import com.backend.pi.backend.model.*;
import com.backend.pi.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jogo")
@CrossOrigin(origins = "http://localhost:3000")
public class JogoController {

   @Autowired
   private JogoService jogoService;

   @Autowired
   private CartaService cartaService;

   @Autowired
   private FeedbackService feedbackService;

   @Autowired
   private ArcoService arcoService;

   @Autowired
   private ScoreService scoreService;

   @Autowired
   private DecisaoService decisaoService;

   @Autowired
   private PartidaService partidaService;

   @Autowired
   private AlunoService alunoService;

   @Autowired
   private RelatorioAlunoService relatorioAlunoService;

   private Arco arcoAtual;

   @GetMapping("/cartas")
   public List<Carta> listarCartas() {
      return cartaService.listarCartas();
   }

   @PostMapping("/cartas")
   public Carta adicionarCarta(@RequestBody Carta carta) {
      return cartaService.adicionarCarta(carta);
   }

   // @GetMapping("/cartas/{id}")
   // public ResponseEntity<Carta> buscarCartaPorId(@PathVariable Long id) {
   // Carta carta = cartaService.buscarCartaPorId(id);
   // return carta != null ? ResponseEntity.ok(carta) :
   // ResponseEntity.notFound().build();
   // }

   @DeleteMapping("/cartas/{id}")
   public ResponseEntity<Void> removerCarta(@PathVariable Long id) {
      cartaService.removerCarta(id);
      return ResponseEntity.noContent().build();
   }

   // Método para buscar feedbacks por tipo
   // @GetMapping("/feedbacks/tipo/{tipo}")
   // public List<Feedback> getFeedbackByTipo(@PathVariable FeedbackTipo tipo) {
   // return feedbackService.findByFeedbackTipo(tipo);
   // }

   // Métodos para os arcos

   @PostMapping("/iniciarArco")
   public ResponseEntity<Arco> iniciarArco(@RequestBody Arco arco) {
      arcoAtual = new Arco();
      arcoAtual.carregarArco(arco.getConjuntoCartas(), arco.getConjuntoFeedback());
      arcoAtual.setCategoria(arco.getCategoria()); // Define a categoria do arco
      Arco novoArco = arcoService.criarArco(arcoAtual); // Salva o arco no banco de dados
      return ResponseEntity.ok(novoArco);
   }

   // Método para finalizar o arco atual
   @PostMapping("/finalizarArco")
   public ResponseEntity<Void> finalizarArco() {
      if (arcoAtual != null) {
         arcoService.finalizarArco(arcoAtual);
         arcoAtual = null; // Reseta o arco atual
         return ResponseEntity.ok().build();
      }
      return ResponseEntity.badRequest().build(); // Retorna Bad Request se arcoAtual for null
   }

   // Método para listar todos os arcos
   @GetMapping("/arcos")
   public ResponseEntity<List<Arco>> buscarArcos() {
      List<Arco> arcos = arcoService.buscarArcos();
      return ResponseEntity.ok(arcos);
   }

   // Método para deletar um arco por ID
   @DeleteMapping("/arcos/{id}")
   public ResponseEntity<Void> deletarArco(@PathVariable Long id) {
      arcoService.deletarArco(id);
      return ResponseEntity.ok().build();
   }

   // Método para o model Jogo

   @PostMapping("/iniciar")
   public ResponseEntity<Arco> iniciarJogo(@RequestBody IniciarJogoRequest request) {
      Long arcoId = request.getArcoId();
      Arco arco = arcoService.encontrarArcoPorId(arcoId);

      if (arco == null) {
         return ResponseEntity.notFound().build(); // Retorna 404 se o arco não for encontrado
      }
      return ResponseEntity.ok(arco); // Retorna o arco selecionado
   }

   @GetMapping("/cartas/{arcoId}/{cartaId}")
   public ResponseEntity<Map<String, Object>> obterCarta(@PathVariable Long arcoId, @PathVariable Long cartaId) {
      List<Carta> cartas = cartaService.listarCartasPorArco(arcoId);

      if (cartas.isEmpty() || cartaId < 1 || cartaId > cartas.size()) {
         return ResponseEntity.notFound().build(); // Retorna 404 se não houver cartas ou ID inválido
      }

      Carta carta = cartas.get(cartaId.intValue() - 1); // Pega a carta com base no índice

      // Obter feedbacks do arco
      Arco arco = arcoService.encontrarArcoPorId(arcoId);
      String feedback = (arco.getConjuntoFeedback().isEmpty()) ? "Sem feedback disponível"
            : arco.getConjuntoFeedback().get(0).getDescricao();

      // Cria a resposta com a descrição da carta e o feedback do arco
      Map<String, Object> response = new HashMap<>();
      response.put("descricao", carta.getDescricao());
      response.put("feedback", feedback);

      return ResponseEntity.ok(response);
   }

   @GetMapping("/feedbacks/tipo/{tipo}")
   public ResponseEntity<List<Feedback>> getFeedbackByTipo(@PathVariable String tipo) {
      FeedbackTipo feedbackTipo = FeedbackTipo.valueOf(tipo.toUpperCase());
      List<Feedback> feedbacks = feedbackService.findByFeedbackTipo(feedbackTipo);
      return ResponseEntity.ok(feedbacks);
   }

   @GetMapping("/cartas/proxima/{arcoId}/{cartaId}")
   public ResponseEntity<Carta> proximaCarta(@PathVariable Long arcoId, @PathVariable Long cartaId) {
      List<Carta> cartas = cartaService.listarCartasPorArco(arcoId);
      if (cartas.isEmpty() || cartaId < 1 || cartaId >= cartas.size()) {
         return ResponseEntity.notFound().build(); // Retorna 404 se não houver cartas ou ID inválido
      }
      Carta proximaCarta = cartas.get(cartaId.intValue()); // Pega a próxima carta
      return ResponseEntity.ok(proximaCarta);
   }

   @GetMapping("/cartas/{id}")
   public ResponseEntity<Carta> obterCarta(@PathVariable Long id) {
      Carta carta = cartaService.listarCartasPorArco(id).get(0); // Simplesmente pega a primeira para o teste
      return ResponseEntity.ok(carta);
   }

   @PostMapping("/criar")
   public ResponseEntity<Jogo> criarJogo(@RequestBody Jogo jogo) {
      Jogo novoJogo = jogoService.criarJogo(jogo);
      return ResponseEntity.ok(novoJogo);
   }

   @GetMapping("/listar")
   public ResponseEntity<List<Jogo>> listarJogos() {
      List<Jogo> jogos = jogoService.listarJogos();
      return ResponseEntity.ok(jogos);
   }

   @DeleteMapping("/deletar/{id}")
   public ResponseEntity<Void> deletarJogo(@PathVariable Long id) {
      jogoService.deletarJogo(id);
      return ResponseEntity.ok().build();
   }

   @GetMapping("/{id}")
   public ResponseEntity<Jogo> encontrarJogoPorId(@PathVariable Long id) {
      Jogo jogo = jogoService.encontrarJogoPorId(id);
      return jogo != null ? ResponseEntity.ok(jogo) : ResponseEntity.notFound().build();
   }

   // Método para criar um novo Score
   @PostMapping("/scores")
   public ResponseEntity<Score> criarScore(@RequestBody Score score) {
      Score novoScore = scoreService.criarScore(score);
      return ResponseEntity.ok(novoScore);
   }

   // Método para listar todos os Scores
   @GetMapping("/scores")
   public ResponseEntity<List<Score>> listarScores() {
      List<Score> scores = scoreService.listarScores();
      return ResponseEntity.ok(scores);
   }

   // Método para encontrar um Score por ID
   @GetMapping("/scores/{id}")
   public ResponseEntity<Score> encontrarScorePorId(@PathVariable Long id) {
      Score score = scoreService.encontrarScorePorId(id);
      return score != null ? ResponseEntity.ok(score) : ResponseEntity.notFound().build();
   }

   // Método para deletar um Score por ID
   @DeleteMapping("/scores/{id}")
   public ResponseEntity<Void> deletarScore(@PathVariable Long id) {
      scoreService.deletarScore(id);
      return ResponseEntity.ok().build();
   }

   // Método para criar uma nova Decisão
   @PostMapping("/decisoes")
   public ResponseEntity<Decisao> criarDecisao(@RequestBody Decisao decisao) {
      Decisao novaDecisao = decisaoService.salvarDecisao(decisao);
      return ResponseEntity.ok(novaDecisao);
   }

   // Método para listar todas as Decisões
   @GetMapping("/decisoes")
   public ResponseEntity<List<Decisao>> listarDecisoes() {
      List<Decisao> decisoes = decisaoService.listarDecisoes();
      return ResponseEntity.ok(decisoes);
   }

   // Método para encontrar uma Decisão por ID
   @GetMapping("/decisoes/{id}")
   public ResponseEntity<Decisao> encontrarDecisaoPorId(@PathVariable Long id) {
      Decisao decisao = decisaoService.encontrarDecisaoPorId(id);
      return decisao != null ? ResponseEntity.ok(decisao) : ResponseEntity.notFound().build();
   }

   // Método para deletar uma Decisão por ID
   @DeleteMapping("/decisoes/{id}")
   public ResponseEntity<Void> deletarDecisao(@PathVariable Long id) {
      decisaoService.deletarDecisao(id);
      return ResponseEntity.ok().build();
   }

   // Método para criar uma nova Partida
   @PostMapping("/partidas")
   public ResponseEntity<Partida> criarPartida(@RequestBody Partida partida) {
      Partida novaPartida = partidaService.criarPartida(partida);
      return ResponseEntity.ok(novaPartida);
   }

   // Método para listar todas as Partidas
   @GetMapping("/partidas")
   public ResponseEntity<List<Partida>> listarPartidas() {
      List<Partida> partidas = partidaService.listarPartidas();
      return ResponseEntity.ok(partidas);
   }

   // Método para encontrar uma Partida por ID
   @GetMapping("/partidas/{id}")
   public ResponseEntity<Partida> encontrarPartidaPorId(@PathVariable Long id) {
      Partida partida = partidaService.encontrarPartidaPorId(id);
      return partida != null ? ResponseEntity.ok(partida) : ResponseEntity.notFound().build();
   }

   // Método para deletar uma Partida por ID
   @DeleteMapping("/partidas/{id}")
   public ResponseEntity<Void> deletarPartida(@PathVariable Long id) {
      partidaService.deletarPartida(id);
      return ResponseEntity.ok().build();
   }

   @SuppressWarnings("null")
   @PostMapping("/salvar")
   public ResponseEntity<String> salvarJogo(@RequestBody IniciarJogoRequest request) {
      try {
         // 1. Salvar ou encontrar o usuário
         Aluno aluno = alunoService.encontrarPorId(request.getUsuarioId());
         if (aluno == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                  .body("Aluno não encontrado.");
         }

         // Crie o RelatorioAluno
         RelatorioAluno relatorioAluno = new RelatorioAluno();
         relatorioAluno.setIdAluno(aluno.getId());
         relatorioAluno.setRelatorioTurmaId(aluno.getIdTurma());

         relatorioAlunoService.salvar(relatorioAluno);

         // 4. Criar uma nova Partida
         Partida partida = new Partida();
         partida.setAlunoId(aluno.getId());
         partida.setDuracaoPartida(request.getDuracao());

         // 5. Salvar dados por arco
         List<Decisao> decisoes = new ArrayList<>();
         for (DadosPorArco dados : request.getDadosPorArco()) {
            Arco arco = arcoService.encontrarArcoPorId(dados.getArcoId());
            if (arco == null) {
               continue; // Continue se o arco não for encontrado
            }

            for (Decisao decisao : dados.getDecisoes()) {
               Decisao novaDecisao = new Decisao();
               novaDecisao.setIdCarta(decisao.getIdCarta());
               novaDecisao.setResposta(decisao.isResposta());
               decisoes.add(novaDecisao);
            }
         }
         partida.setDecisoes(decisoes);

         List<Score> scores = new ArrayList<>();
         scores.add(new Score(request.getAtributosFinais().get("CONTEUDO"), TipoScore.CONTEUDO));
         scores.add(new Score(request.getAtributosFinais().get("CONDUTA"), TipoScore.SEGURANCA));
         scores.add(new Score(request.getAtributosFinais().get("CONTATO"), TipoScore.CONTATO));
         partida.setScores(scores);

         partida.setRelatorioAlunoId(relatorioAluno.getId());
         // 7. Persistir a partida
         partidaService.criarPartida(partida);

         return ResponseEntity.ok().build(); // Retorna 200 OK se tudo foi salvo com sucesso
      } catch (Exception e) {
         e.printStackTrace(); // Log de erro
         return ResponseEntity.status(500).build(); // Retorna 500 em caso de erro
      }
   }
}

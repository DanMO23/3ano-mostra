package com.backend.pi.backend;

import com.backend.pi.backend.controller.*;
import com.backend.pi.backend.model.*;
import com.backend.pi.backend.repository.*;
import com.backend.pi.backend.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JogoController.class)
public class JogoControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(JogoControllerTest.class);

    @MockBean
    private CartaService cartaService;

    @MockBean
    private FeedbackService feedbackService;

    @MockBean
    private JogoService jogoService;

    @MockBean
    private ArcoService arcoService;

    private Jogo jogo;

    @InjectMocks
    private JogoController jogoController;

    @MockBean
    private FeedbackRepository feedbackRepository;

    @Autowired
    private MockMvc mockMvc;

    private Carta carta;

    @BeforeEach
    public void setUp() {
        jogo = new Jogo();

        MockitoAnnotations.openMocks(this);
        // Não configure o MockMvc aqui, o Spring já faz isso quando você usa
        // @WebMvcTest
        carta = new Carta();
        carta.setId(1L);
        carta.setDescricao("Carta de Teste");

        Feedback feedback = new Feedback();
        feedback.setDescricao("Feedback positivo");
        feedback.setFeedbackTipo(FeedbackTipo.POSITIVO);
        when(feedbackRepository.findByFeedbackTipo(FeedbackTipo.POSITIVO)).thenReturn(List.of(feedback));
    }

    @Test
    public void testAdicionarCarta() throws Exception {
        logger.info("Iniciando teste: testAdicionarCarta");
        when(cartaService.adicionarCarta(any(Carta.class))).thenReturn(carta);

        mockMvc.perform(post("/api/jogo/cartas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(carta)))
                .andExpect(status().isOk());

        logger.info("Carta adicionada: {}", carta);
    }

    @Test
    public void testListarCartas() throws Exception {
        logger.info("Iniciando teste: testListarCartas");
        when(cartaService.listarCartas()).thenReturn(Arrays.asList(carta));

        mockMvc.perform(get("/api/jogo/cartas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        logger.info("Cartas listadas: {}", Arrays.asList(carta));
    }

    @Test
    public void testBuscarCartaPorId() throws Exception {
        logger.info("Iniciando teste: testBuscarCartaPorId");
        when(cartaService.buscarCartaPorId(1L)).thenReturn(carta);

        mockMvc.perform(get("/api/jogo/cartas/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        logger.info("Carta encontrada: {}", carta);
    }

    @Test
    public void testRemoverCarta() throws Exception {
        logger.info("Iniciando teste: testRemoverCarta");
        doNothing().when(cartaService).removerCarta(1L);

        mockMvc.perform(delete("/api/jogo/cartas/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        logger.info("Carta removida com ID: {}", 1L);
        verify(cartaService, times(1)).removerCarta(1L);
    }

    @Test
    public void testGetFeedbackByTipo() throws Exception {
        Feedback feedback1 = new Feedback();
        feedback1.setId(1L);
        feedback1.setDescricao("Bom trabalho!");
        feedback1.setFeedbackTipo(FeedbackTipo.POSITIVO);

        when(feedbackService.findByFeedbackTipo(FeedbackTipo.POSITIVO))
                .thenReturn(Arrays.asList(feedback1));

        logger.info("Iniciando teste para o endpoint de feedbacks do tipo POSITIVO");

        mockMvc.perform(get("/api/jogo/feedbacks/tipo/POSITIVO")) // Corrigindo a URL
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].descricao").value("Bom trabalho!"));

        logger.info("Teste do endpoint concluído com sucesso.");
    }

    @Test
    public void testCriarJogo() throws Exception {
        when(jogoService.criarJogo(any(Jogo.class))).thenReturn(jogo);

        mockMvc.perform(post("/api/jogo/criar")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"conjuntoArcos\": []}")) // Adicione detalhes conforme necessário
                .andExpect(status().isOk());

        verify(jogoService, times(1)).criarJogo(any(Jogo.class));
    }

    @Test
    public void testListarJogos() throws Exception {
        List<Jogo> jogos = new ArrayList<>();
        jogos.add(jogo);
        when(jogoService.listarJogos()).thenReturn(jogos);

        mockMvc.perform(get("/api/jogo/listar")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(jogoService, times(1)).listarJogos();
    }

    @Test
    public void testDeletarJogo() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/jogo/deletar/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(jogoService, times(1)).deletarJogo(id);
    }

    @Test
    public void testEncontrarJogoPorId() throws Exception {
        Long id = 1L;
        when(jogoService.encontrarJogoPorId(id)).thenReturn(jogo);

        mockMvc.perform(get("/api/jogo/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(jogoService, times(1)).encontrarJogoPorId(id);
    }

    @Test
    public void testFluxoCompletoDoJogo() throws Exception {
        // Simulando dados fictícios
        Arco arco = new Arco();
        arco.setId(1L);
        arco.setNome("Arco do Conhecimento");

        Feedback feedback1 = new Feedback();
        feedback1.setId(1L);
        feedback1.setDescricao("Ótima pergunta!");
        feedback1.setFeedbackTipo(FeedbackTipo.POSITIVO);

        Feedback feedback2 = new Feedback();
        feedback2.setId(2L);
        feedback2.setDescricao("Esse conceito é fundamental.");
        feedback2.setFeedbackTipo(FeedbackTipo.POSITIVO);

        arco.setConjuntoFeedback(Arrays.asList(feedback1, feedback2)); // Associando feedbacks ao arco

        Carta carta1 = new Carta();
        carta1.setId(1L);
        carta1.setDescricao("O que é a Programação?");

        Carta carta2 = new Carta();
        carta2.setId(2L);
        carta2.setDescricao("Como funciona uma máquina virtual?");

        List<Carta> cartas = Arrays.asList(carta1, carta2);

        when(arcoService.encontrarArcoPorId(1L)).thenReturn(arco);
        when(cartaService.listarCartasPorArco(1L)).thenReturn(cartas);

        // Iniciar o jogo
        mockMvc.perform(post("/api/jogo/iniciar")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"arcoId\": 1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(arco.getId()));

        // Exibir a primeira carta e feedback do arco
        mockMvc.perform(get("/api/jogo/cartas/{arcoId}/{cartaId}", 1, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricao").value("O que é a Programação?"))
                .andExpect(jsonPath("$.feedback").value("Ótima pergunta!"));

        // Iterar para a próxima carta
        mockMvc.perform(get("/api/jogo/cartas/{arcoId}/{cartaId}", 1, 2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricao").value("Como funciona uma máquina virtual?"))
                .andExpect(jsonPath("$.feedback").value("Ótima pergunta!")); // Aqui você pode ajustar conforme
                                                                             // necessário
    }
}
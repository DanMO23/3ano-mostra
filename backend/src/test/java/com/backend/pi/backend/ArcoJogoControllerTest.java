package com.backend.pi.backend;

import com.backend.pi.backend.model.Arco;
import com.backend.pi.backend.model.Carta; // Certifique-se de ter uma classe Carta
import com.backend.pi.backend.model.CategoriaArco;
import com.backend.pi.backend.model.Feedback; // Certifique-se de ter uma classe Feedback
import com.backend.pi.backend.service.ArcoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ArcoJogoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArcoService arcoService;

    @BeforeEach
    public void setup() {
        // Setup mock behavior if needed
    }

    @Test
    public void testIniciarArco() throws Exception {
        Arco arco = new Arco();
        List<Carta> cartas = new ArrayList<>(); // Adicione cartas conforme necessário
        List<Feedback> feedbacks = new ArrayList<>(); // Adicione feedbacks conforme necessário
        arco.setCategoria(CategoriaArco.Game_Perigoso); // Defina uma categoria

        arco.carregarArco(cartas, feedbacks);
        when(arcoService.criarArco(any(Arco.class))).thenReturn(arco);

        mockMvc.perform(post("/api/jogo/iniciarArco")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"conjuntoCartas\": [], \"conjuntoFeedback\": [], \"categoria\": \"CATEGORIA_A\"}")) // Ajuste
                                                                                                                // conforme
                                                                                                                // necessário
                .andExpect(status().isOk());

        verify(arcoService, times(1)).criarArco(any(Arco.class));
    }

    @Test
    public void testFinalizarArco() throws Exception {
        // Primeiro, iniciar o arco
        Arco arco = new Arco();
        arco.carregarArco(new ArrayList<>(), new ArrayList<>()); // Ajuste conforme necessário

        // Inicie o arco
        mockMvc.perform(post("/api/jogo/iniciarArco")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"conjuntoCartas\": [], \"conjuntoFeedback\": []}")) // Ajuste conforme necessário
                .andExpect(status().isOk());

        // Configurar o mock para o método void
        doNothing().when(arcoService).finalizarArco(any(Arco.class)); // Usar doNothing() para métodos void

        // Agora finalize o arco
        mockMvc.perform(post("/api/jogo/finalizarArco")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(arcoService, times(1)).finalizarArco(any(Arco.class));
    }

    @Test
    public void testbuscarArcos() throws Exception {
        List<Arco> arcos = new ArrayList<>();
        when(arcoService.buscarArcos()).thenReturn(arcos);

        mockMvc.perform(get("/api/jogo/arcos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(arcoService, times(1)).buscarArcos();
    }

    @Test
    public void testDeletarArco() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/jogo/arcos/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(arcoService, times(1)).deletarArco(id);
    }
}
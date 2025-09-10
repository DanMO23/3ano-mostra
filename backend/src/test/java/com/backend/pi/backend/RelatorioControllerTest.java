package com.backend.pi.backend;

import com.backend.pi.backend.model.RelatorioTurma;
import com.backend.pi.backend.model.RelatorioAluno;
import com.backend.pi.backend.service.RelatorioTurmaService;
import com.backend.pi.backend.service.RelatorioAlunoService;
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
public class RelatorioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RelatorioTurmaService relatorioTurmaService;

    @MockBean
    private RelatorioAlunoService relatorioAlunoService;

    private RelatorioTurma relatorioTurma;
    private RelatorioAluno relatorioAluno;

    @BeforeEach
    public void setup() {
        // Inicializando objetos comuns
        relatorioTurma = new RelatorioTurma();
        relatorioTurma.setId(1L);
        relatorioTurma.setIdTurma((long) 101);
        relatorioTurma.setIdProfessor(1L);

        relatorioAluno = new RelatorioAluno();
        relatorioAluno.setId(1L);
        relatorioAluno.setIdAluno(1L);
        relatorioAluno.setRelatorioTurmaId(relatorioTurma.getId());
    }

    @Test
    public void testCriarRelatorioTurma() throws Exception {
        when(relatorioTurmaService.salvar(any(RelatorioTurma.class))).thenReturn(relatorioTurma);

        mockMvc.perform(post("/api/relatorios/turma")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idTurma\": 101, \"idProfessor\": 1}"))
                .andExpect(status().isOk());

        verify(relatorioTurmaService, times(1)).salvar(any(RelatorioTurma.class));
    }

    @Test
    public void testListarRelatoriosTurma() throws Exception {
        List<RelatorioTurma> relatorios = new ArrayList<>();
        relatorios.add(relatorioTurma);
        when(relatorioTurmaService.listar()).thenReturn(relatorios);

        mockMvc.perform(get("/api/relatorios/turma")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(relatorioTurmaService, times(1)).listar();
    }

    @Test
    public void testBuscarRelatorioTurmaPorId() throws Exception {
        when(relatorioTurmaService.encontrarPorId(1L)).thenReturn(relatorioTurma);

        mockMvc.perform(get("/api/relatorios/turma/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(relatorioTurmaService, times(1)).encontrarPorId(1L);
    }

    @Test
    public void testDeletarRelatorioTurma() throws Exception {
        doNothing().when(relatorioTurmaService).deletar(1L);

        mockMvc.perform(delete("/api/relatorios/turma/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(relatorioTurmaService, times(1)).deletar(1L);
    }

    @Test
    public void testCriarRelatorioAluno() throws Exception {
        when(relatorioAlunoService.salvar(any(RelatorioAluno.class))).thenReturn(relatorioAluno);

        mockMvc.perform(post("/api/relatorios/aluno")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idAluno\": 1, \"relatorioTurma\": {\"id\": 1}}"))
                .andExpect(status().isOk());

        verify(relatorioAlunoService, times(1)).salvar(any(RelatorioAluno.class));
    }

    @Test
    public void testListarRelatoriosAluno() throws Exception {
        List<RelatorioAluno> relatorios = new ArrayList<>();
        relatorios.add(relatorioAluno);
        when(relatorioAlunoService.listar()).thenReturn(relatorios);

        mockMvc.perform(get("/api/relatorios/aluno")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(relatorioAlunoService, times(1)).listar();
    }

    @Test
    public void testBuscarRelatorioAlunoPorId() throws Exception {
        when(relatorioAlunoService.encontrarPorId(1L)).thenReturn(relatorioAluno);

        mockMvc.perform(get("/api/relatorios/aluno/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(relatorioAlunoService, times(1)).encontrarPorId(1L);
    }

    @Test
    public void testDeletarRelatorioAluno() throws Exception {
        doNothing().when(relatorioAlunoService).deletar(1L);

        mockMvc.perform(delete("/api/relatorios/aluno/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(relatorioAlunoService, times(1)).deletar(1L);
    }
}
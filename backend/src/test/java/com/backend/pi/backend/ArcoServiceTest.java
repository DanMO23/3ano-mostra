package com.backend.pi.backend;

import com.backend.pi.backend.model.Arco;
import com.backend.pi.backend.service.ArcoService;
import com.backend.pi.backend.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ArcoServiceTest {

    @InjectMocks
    private ArcoService arcoService;

    @Mock
    private ArcoRepository arcoRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCriarArco() {
        Arco arco = new Arco();
        when(arcoRepository.save(any(Arco.class))).thenReturn(arco);

        Arco resultado = arcoService.criarArco(arco);

        assertEquals(arco, resultado);
        verify(arcoRepository, times(1)).save(arco);
    }

    @Test
    public void testbuscarArcos() {
        List<Arco> arcos = new ArrayList<>();
        when(arcoRepository.findAll()).thenReturn(arcos);

        List<Arco> resultado = arcoService.buscarArcos();

        assertEquals(arcos, resultado);
        verify(arcoRepository, times(1)).findAll();
    }

    @Test
    public void testDeletarArco() {
        Long id = 1L;

        arcoService.deletarArco(id);

        verify(arcoRepository, times(1)).deleteById(id);
    }

    @Test
    public void testFinalizarArco() {
        Arco arco = new Arco();
        arcoService.finalizarArco(arco);

        // Aqui você pode verificar se a lógica de finalização foi chamada, se necessário
        // Por exemplo, se você tiver um método de persistência dentro de finalizarArco
        // verify(arcoRepository, times(1)).save(arco); // Se você estiver salvando o arco após a finalização
    }
}
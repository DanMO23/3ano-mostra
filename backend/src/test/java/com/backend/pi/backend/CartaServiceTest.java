package com.backend.pi.backend;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.backend.pi.backend.model.Carta;
import com.backend.pi.backend.model.LadoCorreto;
import com.backend.pi.backend.model.NivelCarta;
import com.backend.pi.backend.model.TipoCarta;
import com.backend.pi.backend.repository.CartaRepository;
import com.backend.pi.backend.service.CartaService;

public class CartaServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(CartaServiceTest.class);

    @Mock
    private CartaRepository cartaRepository;

    @InjectMocks
    private CartaService cartaService;

    private Carta carta;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        carta = new Carta();
        carta.setId(1L);
        carta.setDescricao("Carta de Teste");
        carta.setLadoCorreto(LadoCorreto.DIREITA);
        carta.setNivelCarta(NivelCarta.MEDIO);
        carta.setTipoCarta(TipoCarta.CONDUTA);
    }

    @Test
    public void testAdicionarCarta() {
        logger.info("Iniciando teste: testAdicionarCarta");
        when(cartaRepository.save(carta)).thenReturn(carta);
        
        Carta result = cartaService.adicionarCarta(carta);
        
        logger.info("Carta adicionada: {}", result);
        assertNotNull(result);
        assertEquals("Carta de Teste", result.getDescricao());
    }

    @Test
    public void testListarCartas() {
        logger.info("Iniciando teste: testListarCartas");
        when(cartaRepository.findAll()).thenReturn(Arrays.asList(carta));
        
        List<Carta> cartas = cartaService.listarCartas();
        
        logger.info("Cartas listadas: {}", cartas);
        assertEquals(1, cartas.size());
        assertEquals("Carta de Teste", cartas.get(0).getDescricao());
    }

    @Test
    public void testBuscarCartaPorId() {
        logger.info("Iniciando teste: testBuscarCartaPorId");
        when(cartaRepository.findById(1L)).thenReturn(Optional.of(carta));
        
        Carta result = cartaService.buscarCartaPorId(1L);
        
        logger.info("Carta encontrada: {}", result);
        assertNotNull(result);
        assertEquals("Carta de Teste", result.getDescricao());
    }

    @Test
    public void testRemoverCarta() {
        logger.info("Iniciando teste: testRemoverCarta");
        doNothing().when(cartaRepository).deleteById(1L);
        
        cartaService.removerCarta(1L);
        
        logger.info("Carta removida com ID: {}", 1L);
        verify(cartaRepository, times(1)).deleteById(1L);
    }
}
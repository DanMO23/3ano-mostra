package com.backend.pi.backend;

import com.backend.pi.backend.model.*;
import com.backend.pi.backend.repository.*;
import com.backend.pi.backend.service.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class FeedbackServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(FeedbackServiceTest.class);

    @InjectMocks
    private FeedbackService feedbackService;

    @Mock
    private FeedbackRepository feedbackRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByFeedbackTipo() {
        Feedback feedback1 = new Feedback();
        feedback1.setId(1L);
        feedback1.setDescricao("Bom trabalho!");
        feedback1.setFeedbackTipo(FeedbackTipo.POSITIVO);

        when(feedbackRepository.findByFeedbackTipo(FeedbackTipo.POSITIVO))
            .thenReturn(Arrays.asList(feedback1));

        logger.info("Iniciando teste para buscar feedback do tipo POSITIVO");
        List<Feedback> feedbacks = feedbackService.findByFeedbackTipo(FeedbackTipo.POSITIVO);
        
        assertEquals(1, feedbacks.size());
        assertEquals("Bom trabalho!", feedbacks.get(0).getDescricao());
        logger.info("Teste concluído com sucesso: feedback encontrado.");
    }
}

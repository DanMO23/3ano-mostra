package com.backend.pi.backend.service;

import com.backend.pi.backend.model.*;
import com.backend.pi.backend.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    public Feedback save(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public Feedback findById(Long id) {
        return feedbackRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        feedbackRepository.deleteById(id);
    }

    public List<Feedback> findByFeedbackTipo(FeedbackTipo feedbackTipo){
        return feedbackRepository.findByFeedbackTipo(feedbackTipo);
    }
}

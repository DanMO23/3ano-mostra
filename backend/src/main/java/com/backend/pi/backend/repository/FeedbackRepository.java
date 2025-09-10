package com.backend.pi.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.pi.backend.model.Feedback;
import com.backend.pi.backend.model.FeedbackTipo;

public interface FeedbackRepository extends JpaRepository<Feedback, Long>{
    List<Feedback> findByFeedbackTipo(FeedbackTipo feedbackTipo);
}

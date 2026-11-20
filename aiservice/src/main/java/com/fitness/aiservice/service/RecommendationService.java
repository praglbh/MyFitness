package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.repository.AiRepo;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Builder
public class RecommendationService {
    private final AiRepo aiRepo;
    public List<Recommendation> getUserRecommendation(String userId) {
        return aiRepo.findByUserId(userId);
    }

    public Recommendation getActivityRecommendation(String activityId)  {
        return aiRepo.findByActivityId(activityId).orElseThrow(()->
                new RuntimeException("Recommendation not found for activity: "+ activityId));
    }
}

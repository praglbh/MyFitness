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
public class AiService {
    private final AiRepo aiRepo;
    public List<Recommendation> getUserRecommendation(String userId) {
        return aiRepo.getUserById(userId);
    }

    public Recommendation geActivityRecommendation(String activityId)  {
        return aiRepo.getActivityById(activityId).orElseThrow(()->
                new RuntimeException("Recommendation not found for activity: "+ activityId));
    }
}

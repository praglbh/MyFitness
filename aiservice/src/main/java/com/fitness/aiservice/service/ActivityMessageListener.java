package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.repository.AiRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener {

    private final ActivityAIService activityAIService;
    private final AiRepo aiRepo;

    @RabbitListener(queues = "activity.queue")
    public void processActivity(Activity activity) {
        if(aiRepo.existsByActivityId(activity.getId())) {
            log.info("Recommendation already exists for activity: {}",
                    activity.getId());
            return;
        }
        log.info("Received Activity: {}", activity.getId());
        Recommendation recommendation = activityAIService.generateRecommendation(activity);
        aiRepo.save(recommendation);
    }
}

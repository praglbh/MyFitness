package com.fitness.aiservice.repository;

import com.fitness.aiservice.model.Recommendation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AiRepo extends MongoRepository<Recommendation,String> {

    List<Recommendation> getUserById(String userId);

    Optional<Recommendation> getActivityById(String activityId);
}

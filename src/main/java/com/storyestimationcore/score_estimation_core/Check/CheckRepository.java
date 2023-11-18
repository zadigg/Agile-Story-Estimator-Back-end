package com.storyestimationcore.score_estimation_core.Check;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CheckRepository extends MongoRepository<Check, String> {
    // You can add custom query methods here if needed
    Check findByUsername(String username);
    void deleteByUsername(String username);
    boolean existsByUsername(String username);
}

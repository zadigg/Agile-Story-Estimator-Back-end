package com.storyestimationcore.score_estimation_core.repository;

import com.storyestimationcore.score_estimation_core.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    // You can add custom query methods here if needed.
    User findByUsername(String username);
    void deleteByUsername(String username);
    boolean existsByUsername(String username);

}

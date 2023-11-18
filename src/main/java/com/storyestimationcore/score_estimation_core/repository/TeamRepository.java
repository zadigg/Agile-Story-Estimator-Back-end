package com.storyestimationcore.score_estimation_core.repository;

import com.storyestimationcore.score_estimation_core.domain.Team;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeamRepository extends MongoRepository<Team, String> {
    Team findByTeamName(String teamName);
}


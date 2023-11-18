package com.storyestimationcore.score_estimation_core.domain;

import com.storyestimationcore.score_estimation_core.domain.common.Score;
import com.storyestimationcore.score_estimation_core.domain.common.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Developer {
    private String name;
    private String status = Status.INACTIVE.name();
    private int score = Score.ZERO.getValue();


    // Other fields, getters, setters
}
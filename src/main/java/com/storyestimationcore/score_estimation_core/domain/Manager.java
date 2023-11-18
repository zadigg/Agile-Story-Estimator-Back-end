package com.storyestimationcore.score_estimation_core.domain;

import com.storyestimationcore.score_estimation_core.domain.common.Score;
import com.storyestimationcore.score_estimation_core.domain.common.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manager {
    private String name;
    private String status = Status.INACTIVE.toString();
    private int score = Score.ZERO.getValue();
}

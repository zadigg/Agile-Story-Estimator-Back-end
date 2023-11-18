package com.storyestimationcore.score_estimation_core.domain;

import com.storyestimationcore.score_estimation_core.domain.common.Score;
import com.storyestimationcore.score_estimation_core.domain.common.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductOwner {
    private String name;
    private String status = Status.ACTIVE.toString();
    private int score = Score.ZERO.getValue();
}

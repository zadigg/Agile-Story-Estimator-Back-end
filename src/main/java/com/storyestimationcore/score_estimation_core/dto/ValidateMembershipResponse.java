package com.storyestimationcore.score_estimation_core.dto;

import lombok.Data;

@Data
public class ValidateMembershipResponse {

    private boolean isMember;

    public ValidateMembershipResponse(boolean isMember) {
        this.isMember = isMember;
    }
}

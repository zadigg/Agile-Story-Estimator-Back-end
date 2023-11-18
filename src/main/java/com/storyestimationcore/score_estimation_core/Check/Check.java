package com.storyestimationcore.score_estimation_core.Check;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "checks")
public class Check {
    @Id
    private String id;
    private String username;
    private String status;
}

package com.storyestimationcore.score_estimation_core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "teams")
public class Team {
    @Id
    private String id;

    @Field("Team name")
    @Indexed(unique = true)
    private String teamName;

    @Field("revealEstimation")
    private Boolean revealEstimation = false;

    private Manager manager;

    @Field("ProductOwner")
    private ProductOwner productOwner;

    @Field("Scrum master")
    private ScrumMaster scrumMaster;

    private List<Developer> developers;



}




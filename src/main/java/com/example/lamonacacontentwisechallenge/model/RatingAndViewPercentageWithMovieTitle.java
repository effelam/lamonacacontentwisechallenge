package com.example.lamonacacontentwisechallenge.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
public class RatingAndViewPercentageWithMovieTitle {
    private String movieTitle;
    private int rating;
    @Field("view_percentage")
    private int viewPercentage;
}

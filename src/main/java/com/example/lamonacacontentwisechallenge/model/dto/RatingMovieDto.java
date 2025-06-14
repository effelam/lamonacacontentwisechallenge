package com.example.lamonacacontentwisechallenge.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RatingMovieDto {
    private int userId;
    private int movieId;
    private Integer rating;
    private Integer viewPercentage;
}

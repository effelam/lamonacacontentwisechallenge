package com.example.lamonacacontentwisechallenge.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SearchMovie {
    private int movieId;
    private String title;
    private List<String> genres;
    private Double averageRating;
}

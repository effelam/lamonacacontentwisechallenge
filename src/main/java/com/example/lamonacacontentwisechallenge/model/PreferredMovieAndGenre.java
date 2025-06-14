package com.example.lamonacacontentwisechallenge.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PreferredMovieAndGenre {
    private Integer movieId;
    private Set<String> genres;
}

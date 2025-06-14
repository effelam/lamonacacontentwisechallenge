package com.example.lamonacacontentwisechallenge.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "movies")
public class Movie {
    @Id
    private String id;
    @Field("movie_id")
    private int movieId;
    private String title;
    private List<String> genres;
}

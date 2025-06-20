package com.example.lamonacacontentwisechallenge.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "ratings")
public class Rating {
    @Id
    private String id;
    @Field("user_id")
    private int userId;
    @Field("movie_id")
    private int movieId;
    private Integer rating;
    @Field("view_percentage")
    private Integer viewPercentage;
}

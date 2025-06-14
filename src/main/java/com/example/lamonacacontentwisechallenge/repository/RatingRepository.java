package com.example.lamonacacontentwisechallenge.repository;

import com.example.lamonacacontentwisechallenge.model.RatingAndViewPercentageWithMovieTitle;
import com.example.lamonacacontentwisechallenge.model.dto.RatingMovieDto;
import com.example.lamonacacontentwisechallenge.model.entity.Rating;
import com.example.lamonacacontentwisechallenge.utils.UserHistoryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RatingRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    public void upsertDocument(RatingMovieDto dto) {
        Query query = new Query(Criteria.where("user_id").is(dto.getUserId()).and("movie_id").is(dto.getMovieId()));
        Update update = new Update();

        if(dto.getRating() != null) {
            update = update.set("rating", dto.getRating());
        }

        if(dto.getViewPercentage() != null) {
            update = update.set("view_percentage", dto.getViewPercentage());
        }

        mongoTemplate.findAndModify(query, update, FindAndModifyOptions.options().upsert(true), Rating.class);
    }

    public List<RatingAndViewPercentageWithMovieTitle> findUserRatings(int userId, UserHistoryType historyType) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();

        aggregationOperations.add(Aggregation.match(Criteria.where("user_id").is(userId)));

        switch (historyType) {
            case RATING -> aggregationOperations.add(Aggregation.match(Criteria.where("rating").exists(true)));
            case VIEW_PERCENTAGE -> aggregationOperations.add(Aggregation.match(Criteria.where("view_percentage").exists(true)));
        }

        aggregationOperations.add(LookupOperation.newLookup()
                .from("movies")
                .localField("movie_id")
                .foreignField("movie_id")
                .as("movieDetails"));

        aggregationOperations.add(Aggregation.project()
                .andInclude("rating", "view_percentage")
                .and(ArrayOperators.arrayOf("movieDetails.title").elementAt(0)).as("movieTitle"));

        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);

        AggregationResults<RatingAndViewPercentageWithMovieTitle> results =
                mongoTemplate.aggregate(aggregation, "ratings", RatingAndViewPercentageWithMovieTitle.class);

        return results.getMappedResults();
    }

}

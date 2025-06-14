package com.example.lamonacacontentwisechallenge.repository;

import com.example.lamonacacontentwisechallenge.model.PreferredMovieAndGenre;
import com.example.lamonacacontentwisechallenge.model.SearchMovie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class MovieRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<SearchMovie> search(String title, List<String> genres) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();

        title = title.replace(" ", "|");

        if(genres == null) {
            genres = new ArrayList<>();
        }

        aggregationOperations.add(Aggregation.match(new Criteria()
                .orOperator(
                        Criteria.where("title").regex(title, "i"),
                        Criteria.where("genres").in(genres)
                )
        ));

        addMovieAverageRatingStatistics(aggregationOperations);

        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);

        AggregationResults<SearchMovie> results =
                mongoTemplate.aggregate(aggregation, "movies", SearchMovie.class);

        return results.getMappedResults();

    }

    public List<SearchMovie> findMoviesByGenreOrAverageRating(String genre, Integer minRating, Integer maxRating) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();

        if(genre != null) {
            aggregationOperations.add(Aggregation.match(Criteria
                    .where("genres").regex(genre, "i")));
        }

        addMovieAverageRatingStatistics(aggregationOperations);

        if(minRating != null) {
            aggregationOperations.add(Aggregation.match(Criteria
                    .where("averageRating").gte(minRating)));
        }
        if(maxRating != null) {
            aggregationOperations.add(Aggregation.match(Criteria
                    .where("averageRating").lte(maxRating)));
        }

        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);

        AggregationResults<SearchMovie> results =
                mongoTemplate.aggregate(aggregation, "movies", SearchMovie.class);

        return results.getMappedResults();
    }

    public List<PreferredMovieAndGenre> findPreferredMoviesAndGenres(int userId) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        aggregationOperations.add(Aggregation.match(Criteria.where("user_id").is(userId)));
        aggregationOperations.add(Aggregation.match(new Criteria()
                .orOperator(
                        Criteria.where("rating").gte(4),
                        Criteria.where("view_percentage").gte(60).andOperator(Criteria.where("rating").is(0))
                )
        ));

        aggregationOperations.add(LookupOperation.newLookup()
                .from("movies")
                .localField("movie_id")
                .foreignField("movie_id")
                .as("movieDetails"));

        aggregationOperations.add(Aggregation.project()
                .andInclude("_id")
                .and(ArrayOperators.arrayOf("movieDetails.movie_id").elementAt(0)).as("movieId")
                .and(ArrayOperators.arrayOf("movieDetails.genres").elementAt(0)).as("genres"));

        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);

        AggregationResults<PreferredMovieAndGenre> results =
                mongoTemplate.aggregate(aggregation, "ratings", PreferredMovieAndGenre.class);

        return results.getMappedResults();
    }

    public List<SearchMovie> findRecommendedMovies(List<PreferredMovieAndGenre> preferredMoviesAndGenres) {
        Set<Integer> viewedMovies = preferredMoviesAndGenres.stream().map(PreferredMovieAndGenre::getMovieId).collect(Collectors.toSet());
        Set<String> viewedGenres = preferredMoviesAndGenres.stream()
                .map(PreferredMovieAndGenre::getGenres)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());

        List<AggregationOperation> aggregationOperations = new ArrayList<>();

        aggregationOperations.add(Aggregation.match(Criteria.where("movie_id").not().in(viewedMovies)));
        aggregationOperations.add(Aggregation.match(Criteria.where("genres").in(viewedGenres)));

        addMovieAverageRatingStatistics(aggregationOperations);

        aggregationOperations.add(Aggregation.sort(
                Sort.by(Sort.Order.desc("count"), Sort.Order.desc("averageRating"))
        ));
        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);

        AggregationResults<SearchMovie> results =
                mongoTemplate.aggregate(aggregation, "movies", SearchMovie.class);

        return results.getMappedResults();
    }

    private void addMovieAverageRatingStatistics(List<AggregationOperation> aggregationOperations) {
        aggregationOperations.add(LookupOperation.newLookup()
                .from("ratings")
                .localField("movie_id")
                .foreignField("movie_id")
                .as("movieRatings"));

        aggregationOperations.add(Aggregation.unwind("movieRatings", true));
        aggregationOperations.add(Aggregation.group("_id")
                .first("title").as("title")
                .first("genres").as("genres")
                .avg("movieRatings.rating").as("averageRating")
                .count().as("count")
        );
    }

}

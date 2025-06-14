package com.example.lamonacacontentwisechallenge.controller;

import com.example.lamonacacontentwisechallenge.model.SearchMovie;
import com.example.lamonacacontentwisechallenge.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping("/search")
    public ResponseEntity<List<SearchMovie>> search(@RequestParam(required = false) String title,
                                                    @RequestParam(required = false) List<String> genres) {
        return ResponseEntity.ok(movieService.search(title, genres));
    }

    @GetMapping
    public ResponseEntity<List<SearchMovie>> findMoviesByGenreOrAverageRating(@RequestParam(required = false) String genre,
                                                                              @RequestParam(required = false) Integer minRating,
                                                                              @RequestParam(required = false) Integer maxRating) {
        return ResponseEntity.ok(movieService.findMoviesByGenreOrAverageRating(genre, minRating, maxRating));
    }

    @GetMapping("/recommended")
    public ResponseEntity<List<SearchMovie>> findRecommendedMovies(@RequestParam int userId) {
        return ResponseEntity.ok(movieService.findRecommendedMovies(userId));
    }
}

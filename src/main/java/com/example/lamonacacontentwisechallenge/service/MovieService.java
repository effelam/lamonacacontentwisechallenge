package com.example.lamonacacontentwisechallenge.service;

import com.example.lamonacacontentwisechallenge.model.PreferredMovieAndGenre;
import com.example.lamonacacontentwisechallenge.model.SearchMovie;
import com.example.lamonacacontentwisechallenge.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public List<SearchMovie> search(String title, List<String> genres) {
        return movieRepository.search(title, genres);
    }

    public List<SearchMovie> findMoviesByGenreOrAverageRating(String genre, Integer minRating, Integer maxRating) {
        return movieRepository.findMoviesByGenreOrAverageRating(genre, minRating, maxRating);
    }

    public List<SearchMovie> findRecommendedMovies(int userId) {
        List<PreferredMovieAndGenre> preferredMoviesAndGenres = movieRepository.findPreferredMoviesAndGenres(userId);
        return movieRepository.findRecommendedMovies(preferredMoviesAndGenres);
    }
}

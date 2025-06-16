package com.example.lamonacacontentwisechallenge.repository;

import com.example.lamonacacontentwisechallenge.model.SearchMovie;
import com.example.lamonacacontentwisechallenge.model.entity.Movie;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieRepositoryTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private MovieRepository searchMovieService;

    @BeforeEach
    void setUp() {
        // Crea dati fake per la risposta mockata
        SearchMovie searchMovie = new SearchMovie();
        searchMovie.setMovieId(1);
        searchMovie.setTitle("The Matrix");
        searchMovie.setGenres(List.of("Action"));
        List<SearchMovie> mockResult = List.of(searchMovie);
        AggregationResults<SearchMovie> results = new AggregationResults<>(mockResult, new Document());

        // Simula la chiamata a MongoTemplate, restituendo i dati fake
        doReturn(results).when(mongoTemplate).aggregate(any(Aggregation.class), anyString(), any());
    }

    @Test
    void search() {
        // Esegui il metodo da testare
        List<SearchMovie> result = searchMovieService.search("Matrix", List.of("Action"));

        // Verifica che il servizio ha restituito i dati mockati
        assertEquals(1, result.size());
        assertEquals("The Matrix", result.get(0).getTitle());

        // Assicura che l'aggregation sia stata chiamata correttamente
        verify(mongoTemplate).aggregate(any(Aggregation.class), anyString(), any());
    }

    @Test
    void findMoviesByGenreOrAverageRating() {
        // Esegui il metodo da testare
        List<SearchMovie> result = searchMovieService.findMoviesByGenreOrAverageRating("Action", 1, 5);

        // Verifica che il servizio ha restituito i dati mockati
        assertEquals(1, result.size());
        assertEquals("The Matrix", result.get(0).getTitle());

        // Assicura che l'aggregation sia stata chiamata correttamente
        verify(mongoTemplate).aggregate(any(Aggregation.class), anyString(), any());
    }

    @Test
    void findRecommendedMovies() {
        // Esegui il metodo da testare
        List<SearchMovie> result = searchMovieService.findRecommendedMovies(new ArrayList<>());

        // Verifica che il servizio ha restituito i dati mockati
        assertEquals(1, result.size());
        assertEquals("The Matrix", result.get(0).getTitle());

        // Assicura che l'aggregation sia stata chiamata correttamente
        verify(mongoTemplate).aggregate(any(Aggregation.class), anyString(), any());
    }
}
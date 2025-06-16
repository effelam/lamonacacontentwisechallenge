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

    /**
     * Search endpoint per i film presenti in catalogo. Tutti i valori in input sono opzionali e, se non
     *      * passati, vengono ignorati
     * @param title una o più parole da ritrovare all'interno del titolo (è indifferente l'ordine o il
     *              maiuscolo/minuscolo)
     * @param genres uno o più generi da filtrare (è essenziale l'esatta formattazione presente a db, con le
     *               maiuscole e minuscole corrette)
     * @return una lista di film che presentano almeno una parola di quelle in input nel titolo o almeno un genere di
     * quelli passati
     */
    @GetMapping("/search")
    public ResponseEntity<List<SearchMovie>> search(@RequestParam(required = false) String title,
                                                    @RequestParam(required = false) List<String> genres) {
        return ResponseEntity.ok(movieService.search(title, genres));
    }

    /**
     * Ricerca di un film filtrato per genere e per rating medio. Tutti i valori in input sono opzionali e, se non
     * passati, vengono ignorati
     * @param genre il genere voluto
     * @param minRating il rating medio minimo accettato
     * @param maxRating il rating medio massimo accettato
     * @return una lista di film del genere passato (se presente) e il cui rating medio sia compreso tra il valore
     * minimo e quello massimo
     */
    @GetMapping
    public ResponseEntity<List<SearchMovie>> findMoviesByGenreOrAverageRating(@RequestParam(required = false) String genre,
                                                                              @RequestParam(required = false) Integer minRating,
                                                                              @RequestParam(required = false) Integer maxRating) {
        return ResponseEntity.ok(movieService.findMoviesByGenreOrAverageRating(genre, minRating, maxRating));
    }

    /**
     * Servizio per la restituzione di film raccomandanti per l'utente, in base alle interazioni da lui effettuate
     * @param userId id dell'utente che effettua la ricerca (obbligatorio)
     * @return una lista di film che condividono almeno un genere con un film con cui l'utente ha avuto un'interazione
     * positiva (rating maggiore a 3 o, in assenza di rating, ha visto almeno il 60% del film). La lista è ordinata
     * in maniera decrescente in base al numero di interazioni che quel film ha avuto e, a parità di interazioni,
     * in maniera decrescente per rating medio
     */
    @GetMapping("/recommended")
    public ResponseEntity<List<SearchMovie>> findRecommendedMovies(@RequestParam int userId) {
        return ResponseEntity.ok(movieService.findRecommendedMovies(userId));
    }
}

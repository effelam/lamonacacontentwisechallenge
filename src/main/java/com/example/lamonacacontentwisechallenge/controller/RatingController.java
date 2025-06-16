package com.example.lamonacacontentwisechallenge.controller;


import com.example.lamonacacontentwisechallenge.model.RatingAndViewPercentageWithMovieTitle;
import com.example.lamonacacontentwisechallenge.model.dto.RatingMovieDto;
import com.example.lamonacacontentwisechallenge.service.RatingService;
import com.example.lamonacacontentwisechallenge.utils.UserHistoryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {
    @Autowired
    private RatingService ratingService;

    /**
     * Servizio che restituisce lo storico di iterazioni dell'utente
     * @param userId id dell'utente che effettua la richiesta
     * @param historyType può assumere tre valori: RATING, VIEW_PERCENTAGE o BOTH (default)
     * @return una lista di interazioni (rating e percentuale di visualizzazione) che l'utente ha effettuato. Se il
     * valore di historyType non è BOTH restituisce tutte le interazioni che hanno valorizzato il campo corrispondente
     * (quindi in caso di historyType = RATING restituisce tutte le interazioni che hanno un rating e viceversa)
     */
    @GetMapping("/history")
    public ResponseEntity<List<RatingAndViewPercentageWithMovieTitle>> getUserHistoryRatings(@RequestParam int userId, @RequestParam(defaultValue = "BOTH") UserHistoryType historyType) {
        return ResponseEntity.ok(ratingService.getUserHistoryRatings(userId, historyType));
    }

    /**
     * Aggiunge un'interazione di un utente ad un film
     * @param rating body della post di tipo RatingMovieDto. Ha all'interno l'id dell'utente, l'id del film ed
     *               opzionalmente il rating dell'utente e la sua percentuale di visualizzazione
     * @return ok
     */
    @PostMapping
    public ResponseEntity<Void> addRating(@RequestBody RatingMovieDto rating) {
        ratingService.addRating(rating);
        return ResponseEntity.ok().build();
    }
}

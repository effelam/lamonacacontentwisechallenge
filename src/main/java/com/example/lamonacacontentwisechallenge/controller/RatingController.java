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

    @GetMapping("/history")
    public ResponseEntity<List<RatingAndViewPercentageWithMovieTitle>> getUserHistoryRatings(@RequestParam int userId, @RequestParam(defaultValue = "BOTH") UserHistoryType historyType) {
        return ResponseEntity.ok(ratingService.getUserHistoryRatings(userId, historyType));
    }

    @PostMapping
    public ResponseEntity<Void> addRating(@RequestBody RatingMovieDto rating) {
        ratingService.addRating(rating);
        return ResponseEntity.ok().build();
    }
}

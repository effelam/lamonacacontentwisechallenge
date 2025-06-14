package com.example.lamonacacontentwisechallenge.service;

import com.example.lamonacacontentwisechallenge.model.RatingAndViewPercentageWithMovieTitle;
import com.example.lamonacacontentwisechallenge.model.dto.RatingMovieDto;
import com.example.lamonacacontentwisechallenge.repository.RatingRepository;
import com.example.lamonacacontentwisechallenge.utils.UserHistoryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    public List<RatingAndViewPercentageWithMovieTitle> getUserHistoryRatings(int userId, UserHistoryType historyType) {
        return ratingRepository.findUserRatings(userId, historyType);
    }

    public void addRating(RatingMovieDto ratingMovieDto) {
        ratingRepository.upsertDocument(ratingMovieDto);
    }
}

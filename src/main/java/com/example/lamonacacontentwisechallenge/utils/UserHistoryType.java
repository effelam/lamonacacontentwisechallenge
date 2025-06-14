package com.example.lamonacacontentwisechallenge.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum UserHistoryType {
    RATING(List.of("rating")),
    VIEW_PERCENTAGE(List.of("view_percentage")),
    BOTH(List.of("rating", "view_percentage"));

    private final List<String> listTypes;

}

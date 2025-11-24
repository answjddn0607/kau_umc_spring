package com.example.umc9th.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class ReviewRequestDto {

    @Getter
    public static class AddReviewDto {
        @NotNull
        private Float rating;
        @NotBlank
        private String content;
    }
}
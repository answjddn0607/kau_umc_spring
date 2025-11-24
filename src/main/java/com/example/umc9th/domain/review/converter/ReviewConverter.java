package com.example.umc9th.domain.review.converter;

import com.example.umc9th.domain.member.entitiy.User;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.store.entity.Store;
import com.example.umc9th.web.dto.ReviewRequestDto;
import com.example.umc9th.web.dto.ReviewResponseDto;

public class ReviewConverter {
    public static Review toReview(ReviewRequestDto.AddReviewDto request, User user, Store store) {
        return Review.builder()
                .user(user)
                .store(store)
                .rating(request.getRating())
                .content(request.getContent())
                .build();
    }

    public static ReviewResponseDto.AddResultDto toAddResultDto(Review review) {
        return ReviewResponseDto.AddResultDto.builder()
                .reviewId(review.getId())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
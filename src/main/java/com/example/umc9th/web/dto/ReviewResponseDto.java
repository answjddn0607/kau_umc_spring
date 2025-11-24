package com.example.umc9th.web.dto;

import com.example.umc9th.domain.review.entity.Review;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewResponseDto {

    // 내가 작성한 리뷰 목록 DTO
    @Builder
    @Getter
    public static class MyReviewDto {
        private Long reviewId;
        private String storeName;
        private Float rating;
        private String content;
        private LocalDateTime createdAt;

        public static MyReviewDto fromEntity(Review review) {
            return MyReviewDto.builder()
                    .reviewId(review.getId())
                    .storeName(review.getStore().getName())
                    .rating(review.getRating())
                    .content(review.getContent())
                    .createdAt(review.getCreatedAt())
                    .build();
        }
    }

    // Page 응답 DTO
    @Builder
    @Getter
    public static class MyReviewPageDto {
        private List<MyReviewDto> reviews;
        private Integer page;
        private Integer totalPages;
        private Long totalElements;
        private Boolean isLast;
    }

    @Builder
    @Getter
    public static class AddResultDto {
        private Long reviewId;
        private LocalDateTime createdAt;
    }
}
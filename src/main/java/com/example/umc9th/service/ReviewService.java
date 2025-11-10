package com.example.umc9th.service;

import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.review.repository.ReviewRepository;
import com.example.umc9th.web.dto.ReviewResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewResponseDto.MyReviewPageDto getMyReviews(Long userId, String storeName, Integer ratingFloor, Pageable pageable) {

        Page<Review> reviewPage = reviewRepository.findMyReviews(userId, storeName, ratingFloor, pageable);

        List<ReviewResponseDto.MyReviewDto> reviewDtos = reviewPage.getContent().stream()
                .map(ReviewResponseDto.MyReviewDto::fromEntity)
                .collect(Collectors.toList());

        return ReviewResponseDto.MyReviewPageDto.builder()
                .reviews(reviewDtos)
                .page(reviewPage.getNumber())
                .totalPages(reviewPage.getTotalPages())
                .totalElements(reviewPage.getTotalElements())
                .isLast(reviewPage.isLast())
                .build();
    }
}
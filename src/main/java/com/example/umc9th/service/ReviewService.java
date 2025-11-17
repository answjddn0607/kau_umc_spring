package com.example.umc9th.service;

import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.review.repository.ReviewRepository;
import com.example.umc9th.domain.member.repository.UserRepository;
import com.example.umc9th.global.exception.ErrorStatus;
import com.example.umc9th.global.exception.GeneralException;
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
    private final UserRepository userRepository; // 1. UserRepository 주입 받기

    public ReviewResponseDto.MyReviewPageDto getMyReviews(Long userId, String storeName, Integer ratingFloor, Pageable pageable) {

        // 2. 유저가 존재하는지 검증하고, 없으면 예외 발생!
        userRepository.findById(userId).orElseThrow(() ->
                new GeneralException(ErrorStatus.USER_NOT_FOUND));

        // 3. 유저가 존재하면 기존 로직 수행
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
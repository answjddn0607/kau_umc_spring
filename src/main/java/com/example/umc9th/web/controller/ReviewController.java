package com.example.umc9th.web.controller;

import com.example.umc9th.domain.review.converter.ReviewConverter;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.global.common.ApiResponse;
import com.example.umc9th.global.common.SuccessStatus;
import com.example.umc9th.service.ReviewCommandService;
import com.example.umc9th.service.ReviewService;
import com.example.umc9th.web.dto.ReviewRequestDto;
import com.example.umc9th.web.dto.ReviewResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
// import org.springframework.http.ResponseEntity; // 삭제
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewCommandService reviewCommandService;

    @GetMapping("/my")
    // 반환 타입을 ResponseEntity -> ApiResponse<T> 로 변경
    public ApiResponse<ReviewResponseDto.MyReviewPageDto> getMyReviews(
            @RequestParam("userId") Long userId,
            @RequestParam(value = "storeName", required = false) String storeName,
            @RequestParam(value = "ratingFloor", required = false) Integer ratingFloor,
            @PageableDefault(size = 10) Pageable pageable
    ) {

        ReviewResponseDto.MyReviewPageDto myReviews = reviewService.getMyReviews(userId, storeName, ratingFloor, pageable);

        // ResponseEntity.ok() 대신 ApiResponse.onSuccess() 사용
        return ApiResponse.onSuccess(SuccessStatus._OK, myReviews);
    }

    @PostMapping("/stores/{storeId}/reviews")
    public ApiResponse<ReviewResponseDto.AddResultDto> createReview(@PathVariable Long storeId,
                                                                    @RequestBody @Valid ReviewRequestDto.AddReviewDto request) {
        Review review = reviewCommandService.createReview(storeId, request);
        return ApiResponse.onSuccess(SuccessStatus._CREATED, ReviewConverter.toAddResultDto(review));
    }
}
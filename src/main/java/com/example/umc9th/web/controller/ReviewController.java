package com.example.umc9th.web.controller;

import com.example.umc9th.service.ReviewService;
import com.example.umc9th.web.dto.ReviewResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/my")
    public ResponseEntity<ReviewResponseDto.MyReviewPageDto> getMyReviews(
            @RequestParam("userId") Long userId,

            @RequestParam(value = "storeName", required = false) String storeName,

            @RequestParam(value = "ratingFloor", required = false) Integer ratingFloor,

            @PageableDefault(size = 10) Pageable pageable
    ) {
        ReviewResponseDto.MyReviewPageDto myReviews = reviewService.getMyReviews(userId, storeName, ratingFloor, pageable);
        return ResponseEntity.ok(myReviews);
    }
}
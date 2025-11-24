package com.example.umc9th.service;

import com.example.umc9th.domain.member.entitiy.User;
import com.example.umc9th.domain.member.repository.UserRepository;
import com.example.umc9th.domain.review.converter.ReviewConverter;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.review.repository.ReviewRepository;
import com.example.umc9th.domain.store.entity.Store;
import com.example.umc9th.domain.store.repository.StoreRepository;
import com.example.umc9th.global.exception.ErrorStatus;
import com.example.umc9th.global.exception.GeneralException;
import com.example.umc9th.web.dto.ReviewRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewCommandService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    public Review createReview(Long storeId, ReviewRequestDto.AddReviewDto request) {

        Long userId = 1L;

        // 1. 유저 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        // 2. 가게 확인
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.STORE_NOT_FOUND));

        // 3. 리뷰 생성 및 저장
        Review review = ReviewConverter.toReview(request, user, store);
        return reviewRepository.save(review);
    }
}

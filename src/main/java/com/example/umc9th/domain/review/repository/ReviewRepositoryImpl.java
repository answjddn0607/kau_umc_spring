package com.example.umc9th.domain.review.repository;

import com.example.umc9th.domain.member.entitiy.QUser;
import com.example.umc9th.domain.review.entity.QReview;
import com.example.umc9th.domain.review.entity.Review;
import com.example.umc9th.domain.store.entity.QStore;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    // Q-Type 클래스
    private final QReview review = QReview.review;
    private final QStore store = QStore.store;
    private final QUser user = QUser.user;

    @Override
    public Page<Review> findMyReviews(Long userId, String storeName, Integer ratingFloor, Pageable pageable) {

        JPAQuery<Review> query = queryFactory
                .selectFrom(review)
                .join(review.store, store).fetchJoin()
                .join(review.user, user)
                .where(
                        userIdEq(userId),
                        storeNameEq(storeName),
                        ratingFilter(ratingFloor)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(review.createdAt.desc()); // 최신순 정렬

        List<Review> results = query.fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(review.count())
                .from(review)
                .join(review.store, store)
                .join(review.user, user)
                .where(
                        userIdEq(userId),
                        storeNameEq(storeName),
                        ratingFilter(ratingFloor)
                );

        long total = countQuery.fetchOne();

        return new PageImpl<>(results, pageable, total);
    }

    // 사용자 ID 조건
    private BooleanExpression userIdEq(Long userId) {
        return user.id.eq(userId);
    }

    // 가게 이름 조건
    private BooleanExpression storeNameEq(String storeName) {
        return storeName != null ? store.name.eq(storeName) : null;
    }

    // 별점 필터 조건
    private BooleanExpression ratingFilter(Integer ratingFloor) {
        if (ratingFloor == null) {
            return null; // 필터 없음
        }

        if (ratingFloor == 5) {
            return review.rating.eq(5.0f);
        } else if (ratingFloor >= 1 && ratingFloor <= 4) {
            return review.rating.goe(ratingFloor.floatValue())
                    .and(review.rating.lt(ratingFloor.floatValue() + 1.0f));
        }
        return null;
    }
}
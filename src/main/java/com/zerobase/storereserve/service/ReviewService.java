package com.zerobase.storereserve.service;

import com.zerobase.storereserve.domain.Review;
import com.zerobase.storereserve.domain.Store;
import com.zerobase.storereserve.domain.User;
import com.zerobase.storereserve.dto.ReviewDto;
import com.zerobase.storereserve.exception.impl.*;
import com.zerobase.storereserve.repository.ReviewRepository;
import com.zerobase.storereserve.repository.StoreRepository;
import com.zerobase.storereserve.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    public List<ReviewDto> getUserReviewList(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException());

        List<Review> reviewList = reviewRepository.findAllByUser(user)
                .orElseThrow(() -> new UserReviewsNotFoundException());

        return reviewList.stream().map(review ->
                ReviewDto.builder()
                        .reviewId(review.getId())
                        .contents(review.getContents())
                        .rating(review.getRating())
                        .build()).collect(Collectors.toList());
    }

    public List<ReviewDto> getStoreReviewList(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoresNotFoundException());

        List<Review> reviewList = reviewRepository.findAllByStore(store)
                .orElseThrow(() -> new StoreReviewsNotFoundException());

        return reviewList.stream().map(review ->
                ReviewDto.builder()
                        .reviewId(review.getId())
                        .contents(review.getContents())
                        .rating(review.getRating())
                        .build()).collect(Collectors.toList());
    }

    public void registerReview(ReviewDto reviewDto) {
        Review review = reviewRepository.findById(reviewDto.getReviewId())
                .orElseThrow(() -> new ReviewNotFoundException());

        Store store = review.getStore();

        review.setContents(reviewDto.getContents());
        review.setRating(reviewDto.getRating());

        reviewRepository.save(review);

        List<Review> reviewList = reviewRepository.findAllByStore(store)
                .orElseThrow(() -> new ReviewNotFoundException());

        int reviewCount = 0;
        int ratingAmount = 0;

        for (Review r : reviewList) {
            int rating = r.getRating();
            if (rating != 0) {
                reviewCount++;
                ratingAmount += rating;
            }
        }
        if (reviewCount == 0) {
            throw new RuntimeException("리뷰가 존재하지 않습니다.");
        }

        store.setRating((double) ratingAmount / reviewCount);
        storeRepository.save(store);
    }
}
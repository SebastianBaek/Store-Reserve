package com.zerobase.storereserve.controller;

import com.zerobase.storereserve.dto.ReviewDto;
import com.zerobase.storereserve.security.TokenProvider;
import com.zerobase.storereserve.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;
    private final TokenProvider tokenProvider;

    // 유저가 쓸 수 있거나 썼던 모든 리뷰들을 가져오는 api
    @GetMapping("/user")
    @PreAuthorize("hasRole('REVIEWWRITE')")
    public ResponseEntity<?> getUserReviews(HttpServletRequest request) {
        String token = tokenProvider.resolveTokenFromRequest(request);

        return ResponseEntity.ok(reviewService.getUserReviewList(
                tokenProvider.getUsername(token)));
    }

    // 상점에 대한 모든 리뷰들을 가져오는 api
    @GetMapping("/store")
    @PreAuthorize("hasRole('REVIEWREAD')")
    public ResponseEntity<?> getStoreReviews(@RequestParam Long storeId) {
        return ResponseEntity.ok(reviewService.getStoreReviewList(storeId));
    }

    // 유저가 리뷰를 등록하는 api
    @PutMapping("/register")
    @PreAuthorize("hasRole('REVIEWWRITE')")
    public ResponseEntity<?> registerReview(@RequestBody ReviewDto reviewDto) {
        reviewService.registerReview(reviewDto);
        return ResponseEntity.ok("리뷰가 성공적으로 등록되었습니다.");
    }
}

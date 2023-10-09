package com.zerobase.storereserve.controller;

import com.zerobase.storereserve.dto.StoreRegisterDto;
import com.zerobase.storereserve.security.TokenProvider;
import com.zerobase.storereserve.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {

    private final StoreService storeService;
    private final TokenProvider tokenProvider;

    // 상점 등록 api
    @PostMapping("/register")
    @PreAuthorize("hasRole('STOREWRITE')")
    public ResponseEntity<StoreRegisterDto> storeRegister(
            @RequestBody StoreRegisterDto storeRegisterDto,
            HttpServletRequest request) {
        String token = tokenProvider.resolveTokenFromRequest(request);

        return ResponseEntity.ok(storeService.storeRegister(
                storeRegisterDto, tokenProvider.getUsername(token)));
    }

    // PathVariable의 문자열을 매장명에 포함한 매장들을 가져오는 api
    @GetMapping("/search/{storename}")
    @PreAuthorize("hasRole('STOREREAD')")
    public ResponseEntity<?> storeNameSearch(@PathVariable String storename) {
        return ResponseEntity.ok(storeService.getStoreWithName(storename));
    }

    // 가나다순으로 모든 매장을 가져오는 api
    @GetMapping("/search/abc")
    @PreAuthorize("hasRole('STOREREAD')")
    public ResponseEntity<?> storeABCSearch() {
        return ResponseEntity.ok(storeService.getAllStoreByABC());
    }

    // 거리순으로 모든 매장을 가져오는 api
    @GetMapping("/search/distance")
    @PreAuthorize("hasRole('STOREREAD')")
    public ResponseEntity<?> storeDistanceSearch(
            @RequestParam Double lat, @RequestParam Double lng) {
        return ResponseEntity.ok(storeService.getAllStoreByDistance(lat, lng));
    }

    // 별점순으로 모든 매장을 가져오는 api
    @GetMapping("/search/rating")
    @PreAuthorize("hasRole('STOREREAD')")
    public ResponseEntity<?> storeRatingSearch() {
        return ResponseEntity.ok(storeService.getAllStoreByRating());
    }
}

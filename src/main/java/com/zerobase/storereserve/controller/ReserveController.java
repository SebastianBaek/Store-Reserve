package com.zerobase.storereserve.controller;

import com.zerobase.storereserve.dto.ReservationRegisterDto;
import com.zerobase.storereserve.security.TokenProvider;
import com.zerobase.storereserve.service.ReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reserve")
public class ReserveController {

    private final ReserveService reserveService;
    private final TokenProvider tokenProvider;

    // username을 이용한 예약 시도 api
    @PostMapping("/register/{storeId}")
    @PreAuthorize("hasRole('RESERVEWRITE')")
    public ResponseEntity<?> registerReservation(
            @RequestBody @Valid ReservationRegisterDto reservationRegisterDto,
            @PathVariable Long storeId,
            HttpServletRequest request) {
        String token = tokenProvider.resolveTokenFromRequest(request);

        return ResponseEntity.ok(reserveService.reservationRegister(
                reservationRegisterDto,
                storeId,
                tokenProvider.getUsername(token)));
    }

    // username을 이용해 해당 유저에 대한 모든 예약을 가져오는 api (내 예약 목록)
    @GetMapping("/user")
    @PreAuthorize("hasRole('RESERVEREAD')")
    public ResponseEntity<?> getUserReservations(HttpServletRequest request) {
        String token = tokenProvider.resolveTokenFromRequest(request);

        return ResponseEntity.ok(reserveService.getUserReservationList(
                tokenProvider.getUsername(token)));
    }

    // username을 이용해 사용자와 매핑되어 있는 매장을 가져온 후,
    // 그 매장의 모든 예약을 가져오는 api (내 매장 예약 목록)
    @GetMapping("/store")
    @PreAuthorize("hasRole('RESERVEREAD')")
    public ResponseEntity<?> getStoreReservations(HttpServletRequest request) {
        String token = tokenProvider.resolveTokenFromRequest(request);

        return ResponseEntity.ok(reserveService.getStoreReservationList(
                tokenProvider.getUsername(token)));
    }

    // 유저의 에약 취소 api
    @DeleteMapping("/user/cancel/{reservationId}")
    @PreAuthorize("hasRole('RESERVEWRITE')")
    public ResponseEntity<?> cancelUserReservation(
            @PathVariable Long reservationId, HttpServletRequest request) {
        String token = tokenProvider.resolveTokenFromRequest(request);

        return ResponseEntity.ok(reserveService.deleteUserReservation(
                reservationId, tokenProvider.getUsername(token)));
    }

    // 매장의 에약 취소 api
    @DeleteMapping("/store/cancel/{reservationId}")
    @PreAuthorize("hasRole('RESERVEWRITE')")
    public ResponseEntity<?> cancelStoreReservation(
            @PathVariable Long reservationId, HttpServletRequest request) {
        String token = tokenProvider.resolveTokenFromRequest(request);

        return ResponseEntity.ok(reserveService.deleteStoreReservation(
                reservationId, tokenProvider.getUsername(token)));
    }

    // 유저가 예약 시간 10분전에 도착해서 체크인 하는 api
    @PostMapping("/user/checkIn/{reservationId}")
    @PreAuthorize("hasRole('RESERVEWRITE')")
    public ResponseEntity<?> checkInUser(
            @PathVariable Long reservationId, HttpServletRequest request) {
        String token = tokenProvider.resolveTokenFromRequest(request);

        reserveService.checkInUser(
                reservationId, tokenProvider.getUsername(token));

        return ResponseEntity.ok("체크인이 완료되었습니다.");
    }
}

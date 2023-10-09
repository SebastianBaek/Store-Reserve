package com.zerobase.storereserve.service;


import com.zerobase.storereserve.domain.Reservation;
import com.zerobase.storereserve.domain.Review;
import com.zerobase.storereserve.domain.Store;
import com.zerobase.storereserve.domain.User;
import com.zerobase.storereserve.dto.ReservationInfoDto;
import com.zerobase.storereserve.dto.ReservationRegisterDto;
import com.zerobase.storereserve.dto.StoreInfoDto;
import com.zerobase.storereserve.exception.impl.*;
import com.zerobase.storereserve.repository.ReserveRepository;
import com.zerobase.storereserve.repository.ReviewRepository;
import com.zerobase.storereserve.repository.StoreRepository;
import com.zerobase.storereserve.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReserveService {

    private final ReserveRepository reserveRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    public ReservationRegisterDto reservationRegister(
            ReservationRegisterDto reservationRegisterDto, Long storeId, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException());

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoresNotFoundException());


        return ReservationRegisterDto.fromEntity(reserveRepository.save(Reservation
                .builder()
                .reservationTime(reservationRegisterDto.getReservationTime())
                .guestPhone(reservationRegisterDto.getGuestPhone())
                .user(user)
                .store(store)
                .build()));
    }

    public List<ReservationInfoDto> getUserReservationList(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException());

        List<Reservation> userReservations = reserveRepository
                .findAllByUserOrderByReservationTimeAsc(user)
                .orElseThrow(() -> new ReservationsNotFoundException());

        return userReservations.stream()
                .map(reservation -> ReservationInfoDto.builder()
                        .storeInfoDto(StoreInfoDto.fromEntity(reservation.getStore()))
                        .reservationTime(reservation.getReservationTime())
                        .guestPhone(reservation.getGuestPhone())
                        .build()).collect(Collectors.toList());
    }

    public List<ReservationInfoDto> getStoreReservationList(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException());

        Store store = storeRepository.findByUser(user)
                .orElseThrow(() -> new StoresNotFoundException());

        List<Reservation> storeReservations = reserveRepository
                .findAllByStoreOrderByReservationTimeAsc(store)
                .orElseThrow(() -> new ReservationsNotFoundException());

        return storeReservations.stream()
                .map(reservation -> ReservationInfoDto.builder()
                        .username(reservation.getUser().getUsername())
                        .reservationTime(reservation.getReservationTime())
                        .guestPhone(reservation.getGuestPhone())
                        .build()).collect(Collectors.toList());
    }

    @Transactional
    public ReservationInfoDto deleteUserReservation(Long reservationId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException());

        Reservation reservation = reserveRepository.findByIdAndUser(reservationId, user)
                .orElseThrow(() -> new ReservationNotMatchUser());

        reserveRepository.delete(reservation);

        return ReservationInfoDto.builder()
                .storeInfoDto(StoreInfoDto.builder()
                        .storename(reservation.getStore().getStorename())
                        .build())
                .reservationTime(reservation.getReservationTime())
                .build();
    }


    @Transactional
    public ReservationInfoDto deleteStoreReservation(Long reservationId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException());

        Store store = storeRepository.findByUser(user)
                .orElseThrow(() -> new StoresNotFoundException());

        Reservation reservation = reserveRepository.findByIdAndStore(reservationId, store)
                .orElseThrow(() -> new ReservationNotMatchUser());

        reserveRepository.delete(reservation);

        return ReservationInfoDto.builder()
                .username(reservation.getUser().getUsername())
                .reservationTime(reservation.getReservationTime())
                .guestPhone(reservation.getGuestPhone())
                .build();
    }

    @Transactional
    public void checkInUser(Long reservationId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException());

        Reservation reservation = reserveRepository.findByIdAndUser(reservationId, user)
                .orElseThrow(() -> new ReservationNotMatchUser());

        if (!validateCheckInTime(reservation.getReservationTime())) {
            throw new ReservationTimeoutException();
        }

        reserveRepository.delete(reservation);

        reviewRepository.save(Review.builder()
                        .user(user)
                        .store(reservation.getStore())
                        .rating(0)
                        .build());

        log.info(username + "님이 체크인 하셨습니다.");
    }

    private boolean validateCheckInTime(LocalDateTime reservationTime) {
        return reservationTime.minusMinutes(10).isAfter(LocalDateTime.now());
    }
}

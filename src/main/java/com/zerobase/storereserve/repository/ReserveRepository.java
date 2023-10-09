package com.zerobase.storereserve.repository;

import com.zerobase.storereserve.domain.Reservation;
import com.zerobase.storereserve.domain.Store;
import com.zerobase.storereserve.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReserveRepository extends JpaRepository<Reservation, Long> {
    Optional<List<Reservation>> findAllByUserOrderByReservationTimeAsc(User user);

    Optional<List<Reservation>> findAllByStoreOrderByReservationTimeAsc(Store store);

    Optional<Reservation> findByIdAndUser(Long reservationId, User user);

    Optional<Reservation> findByIdAndStore(Long reservationId, Store store);
}

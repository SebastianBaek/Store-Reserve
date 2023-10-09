package com.zerobase.storereserve.dto;

import com.zerobase.storereserve.domain.Reservation;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
public class ReservationRegisterDto {

    @Future
    private LocalDateTime reservationTime;

    @NotBlank
    private String guestPhone;

    public static ReservationRegisterDto fromEntity(Reservation reservation) {
        return ReservationRegisterDto
                .builder()
                .reservationTime(reservation.getReservationTime())
                .guestPhone(reservation.getGuestPhone())
                .build();
    }
}

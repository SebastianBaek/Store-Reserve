package com.zerobase.storereserve.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReservationInfoDto {

    private String username;

    private StoreInfoDto storeInfoDto;

    private LocalDateTime reservationTime;

    private String guestPhone;
}

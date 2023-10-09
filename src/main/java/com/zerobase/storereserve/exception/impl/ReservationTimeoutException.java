package com.zerobase.storereserve.exception.impl;

import com.zerobase.storereserve.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class ReservationTimeoutException extends AbstractException {
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "예약 시간 10분 전까지 체크인해야합니다.";
    }
}

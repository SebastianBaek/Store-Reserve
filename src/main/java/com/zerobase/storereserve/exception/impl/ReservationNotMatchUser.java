package com.zerobase.storereserve.exception.impl;

import com.zerobase.storereserve.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class ReservationNotMatchUser extends AbstractException {
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "유저 정보와 예약이 일치하지 않습니다.";
    }
}
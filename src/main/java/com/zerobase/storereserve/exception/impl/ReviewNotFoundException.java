package com.zerobase.storereserve.exception.impl;

import com.zerobase.storereserve.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class ReviewNotFoundException extends AbstractException {
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "리뷰가 존재하지 않습니다.";
    }
}
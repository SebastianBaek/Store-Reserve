package com.zerobase.storereserve.exception.impl;

import com.zerobase.storereserve.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends AbstractException {
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "존재하지 않는 사용자명입니다.";
    }
}

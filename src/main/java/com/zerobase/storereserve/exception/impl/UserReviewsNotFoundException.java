package com.zerobase.storereserve.exception.impl;

import com.zerobase.storereserve.exception.AbstractException;
import org.springframework.http.HttpStatus;

public class UserReviewsNotFoundException extends AbstractException {
    @Override
    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "작성한 리뷰 혹은 작성 가능한 리뷰가 존재하지 않습니다.";
    }
}

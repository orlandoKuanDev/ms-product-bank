package com.example.msproduct.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public enum ErrorCode {
    PRODUCT_SERVICE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Product service error"),
    PRODUCT_INVALID_REQUEST(HttpStatus.BAD_REQUEST, "Product request invalid");
    private final HttpStatus status;
    private final String message;
    ErrorCode(HttpStatus status, String message) {

        this.status = status;
        this.message = message;
    }
}

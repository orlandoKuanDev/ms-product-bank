package com.example.msproduct.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;

@Getter
public class ProductException extends RuntimeException{
    private final HttpStatus status;

    public ProductException(HttpStatus status , String message) {
        super(message);
        this.status = status;
    }

    public static ProductException from(ClientResponse response) {
        return new ProductException(response.statusCode(),
                response.toEntity(ClientError.class).toString());
    }

    public boolean isClientError() {
        return status.is4xxClientError();
    }

    public boolean isServerError() {
        return status.is5xxServerError();
    }

    public static boolean isClientError(Throwable throwable) {
        return throwable instanceof ProductException
                && ((ProductException) throwable).isClientError();
    }

    public static boolean isServerError(Throwable throwable) {
        return throwable instanceof ProductException
                && ((ProductException) throwable).isServerError();
    }
}

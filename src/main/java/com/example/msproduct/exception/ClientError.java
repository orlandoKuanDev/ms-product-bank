package com.example.msproduct.exception;

import lombok.Data;

@Data
public class ClientError {
    private int code;
    private String message;
}

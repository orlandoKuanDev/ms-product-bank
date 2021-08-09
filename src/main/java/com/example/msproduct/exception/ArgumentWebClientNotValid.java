package com.example.msproduct.exception;

import com.example.msproduct.utils.I18AbleException;

public class ArgumentWebClientNotValid extends I18AbleException {
    public ArgumentWebClientNotValid(String key, Object... args) {
        super(key, args);
    }
}

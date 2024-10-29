package com.treizer.spring_sqlite.presentation.advice.custom;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }

}

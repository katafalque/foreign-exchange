package com.example.foreignexchange.exception;

public class MissingServiceException extends RuntimeException{
    public MissingServiceException(RuntimeException innerException) {
        super(innerException);
    }
}

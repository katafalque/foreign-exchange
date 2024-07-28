package com.example.foreignexchange.exception;

public class UnsupportedServiceTypeException extends RuntimeException{
    public UnsupportedServiceTypeException(String serviceProviderType) {
        super(serviceProviderType);
    }

    public UnsupportedServiceTypeException() {
        super();
    }
}

package com.ps.exception;

public class DomainException extends RuntimeException {

    public DomainException(String message, Throwable ex) {
        super(message, ex);
    }

    public DomainException(String message) {
        super(message);
    }
}

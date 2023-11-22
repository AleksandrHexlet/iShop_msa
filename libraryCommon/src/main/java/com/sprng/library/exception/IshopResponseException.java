package com.sprng.library.exception;

public class IshopResponseException  extends Exception{
    public IshopResponseException(String message) {
        super(message);
    }

    public IshopResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}

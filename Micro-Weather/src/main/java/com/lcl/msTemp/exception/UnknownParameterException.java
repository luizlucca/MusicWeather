package com.lcl.msTemp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UnknownParameterException extends RuntimeException{
    public UnknownParameterException() {
    }

    public UnknownParameterException(String message) {
        super(message);
    }

    public UnknownParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownParameterException(Throwable cause) {
        super(cause);
    }
}

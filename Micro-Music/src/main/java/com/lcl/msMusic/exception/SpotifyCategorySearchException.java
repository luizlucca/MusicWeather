package com.lcl.msMusic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SpotifyCategorySearchException extends RuntimeException {

    public SpotifyCategorySearchException() {
    }

    public SpotifyCategorySearchException(String message) {
        super(message);
    }

    public SpotifyCategorySearchException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpotifyCategorySearchException(Throwable cause) {
        super(cause);
    }
}

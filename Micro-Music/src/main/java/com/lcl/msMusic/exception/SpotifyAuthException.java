package com.lcl.msMusic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class SpotifyAuthException extends RuntimeException{
    public SpotifyAuthException() {
    }

    public SpotifyAuthException(String message) {
        super(message);
    }

    public SpotifyAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpotifyAuthException(Throwable cause) {
        super(cause);
    }
}

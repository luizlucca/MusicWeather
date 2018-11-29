package com.lcl.msMusic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author s2it_llucca
 * @version $Revision: $<br/>
 * $Id: $
 * @since 11/29/18 9:42 AM
 */
@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class SpotifyNotAvailableException extends RuntimeException{

    public SpotifyNotAvailableException() {
    }

    public SpotifyNotAvailableException(final String message) {
        super(message);
    }

    public SpotifyNotAvailableException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public SpotifyNotAvailableException(final Throwable cause) {
        super(cause);
    }
}

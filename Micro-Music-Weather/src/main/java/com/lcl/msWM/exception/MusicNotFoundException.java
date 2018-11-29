package com.lcl.msWM.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author s2it_llucca
 * @version $Revision: $<br/>
 * $Id: $
 * @since 11/29/18 12:17 PM
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MusicNotFoundException extends RuntimeException {

    public MusicNotFoundException() {
    }

    public MusicNotFoundException(final String message) {
        super(message);
    }

    public MusicNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MusicNotFoundException(final Throwable cause) {
        super(cause);
    }
}

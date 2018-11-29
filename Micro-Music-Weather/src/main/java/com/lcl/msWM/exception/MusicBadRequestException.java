package com.lcl.msWM.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author s2it_llucca
 * @version $Revision: $<br/>
 * $Id: $
 * @since 11/29/18 12:16 PM
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MusicBadRequestException extends RuntimeException {

    public MusicBadRequestException() {
    }

    public MusicBadRequestException(final String message) {
        super(message);
    }

    public MusicBadRequestException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MusicBadRequestException(final Throwable cause) {
        super(cause);
    }
}

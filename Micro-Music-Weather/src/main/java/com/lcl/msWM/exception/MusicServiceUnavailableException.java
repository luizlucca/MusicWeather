package com.lcl.msWM.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author s2it_llucca
 * @version $Revision: $<br/>
 * $Id: $
 * @since 11/29/18 12:25 PM
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class MusicServiceUnavailableException extends RuntimeException {

    public MusicServiceUnavailableException() {
    }

    public MusicServiceUnavailableException(final String message) {
        super(message);
    }

    public MusicServiceUnavailableException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MusicServiceUnavailableException(final Throwable cause) {
        super(cause);
    }
}

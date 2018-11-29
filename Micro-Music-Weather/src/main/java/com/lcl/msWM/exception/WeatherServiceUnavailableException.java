package com.lcl.msWM.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author s2it_llucca
 * @version $Revision: $<br/>
 * $Id: $
 * @since 11/29/18 1:05 PM
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class WeatherServiceUnavailableException extends RuntimeException {

    public WeatherServiceUnavailableException() {
    }

    public WeatherServiceUnavailableException(final String message) {
        super(message);
    }

    public WeatherServiceUnavailableException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public WeatherServiceUnavailableException(final Throwable cause) {
        super(cause);
    }
}

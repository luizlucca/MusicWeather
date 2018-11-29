package com.lcl.msWM.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author s2it_llucca
 * @version $Revision: $<br/>
 * $Id: $
 * @since 11/29/18 1:04 PM
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class WeatherBadRequestException extends RuntimeException {

    public WeatherBadRequestException() {
    }

    public WeatherBadRequestException(final String message) {
        super(message);
    }

    public WeatherBadRequestException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public WeatherBadRequestException(final Throwable cause) {
        super(cause);
    }
}

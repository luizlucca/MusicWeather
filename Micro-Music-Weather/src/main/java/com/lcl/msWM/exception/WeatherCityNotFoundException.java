package com.lcl.msWM.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author s2it_llucca
 * @version $Revision: $<br/>
 * $Id: $
 * @since 11/29/18 1:03 PM
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class WeatherCityNotFoundException extends RuntimeException{

    public WeatherCityNotFoundException() {
    }

    public WeatherCityNotFoundException(String message) {
        super(message);
    }

    public WeatherCityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public WeatherCityNotFoundException(Throwable cause) {
        super(cause);
    }
}


package com.lcl.msTemp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

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

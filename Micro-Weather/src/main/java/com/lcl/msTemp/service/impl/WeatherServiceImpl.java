package com.lcl.msTemp.service.impl;

import com.lcl.msTemp.exception.UnknownParameterException;
import com.lcl.msTemp.exception.WeatherCityNotFoundException;
import com.lcl.msTemp.model.DTO.WeatherDTO;
import com.lcl.msTemp.model.Weather;
import com.lcl.msTemp.model.WeatherSearchParameter;
import com.lcl.msTemp.service.intDef.WeatherService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service("OpenWeatherService")
public class WeatherServiceImpl implements WeatherService {

    private final Logger log = LoggerFactory.getLogger(WeatherServiceImpl.class);

    @Value("${openweathermap.id}")
    private String appId;

    @Value("${openweathermap.url}")
    private String baseUrl;

    @Value("${openweathermap.unity}")
    private String weatherUnity;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("HGWeatherService")
    private WeatherService hgWeatherService;

    @Override
    @HystrixCommand(fallbackMethod = "getWeatherFallback", ignoreExceptions = {WeatherCityNotFoundException.class})
    public WeatherDTO getWeather(WeatherSearchParameter weatherSearchParameter) {
        log.info("I=Search with parameter", weatherSearchParameter);

        String uri;
        if (weatherSearchParameter.getCity() != null && !weatherSearchParameter.getCity().isEmpty()) {
            uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "?q={city}&units={weatherUnity}&APPID={appId}")
                    .buildAndExpand(weatherSearchParameter.getCity(), weatherUnity, appId).toUriString().trim();
        } else if (weatherSearchParameter.getLat() != null && weatherSearchParameter.getLng() != null) {
            uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "?lat={lat}&lon={long}&units={weatherUnity}&APPID={appId}")
                    .buildAndExpand(weatherSearchParameter.getLat(), weatherSearchParameter.getLng(), weatherUnity, appId).toUriString().trim();
        } else {
            throw new UnknownParameterException();
        }

        try {

            HttpEntity<Weather> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    makeDefaultHttpAppJsonHeader(),
                    Weather.class);

            System.out.println(response);

            if (response.getBody() != null) {

                return convert(response.getBody());
            } else {
                log.error("E=City Not Found, weatherSearchParameter={}", weatherSearchParameter);
                throw new WeatherCityNotFoundException("City not found");
            }
        } catch (HttpClientErrorException e) {

            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                log.error("E=City Not Found, weatherSearchParameter={}", weatherSearchParameter, e);
                throw new WeatherCityNotFoundException("City not found", e);
            }

            if (e.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                log.error("E=City Not Found - Wrong parameter, weatherSearchParameter={}", weatherSearchParameter, e);
                throw new WeatherCityNotFoundException("City not found", e);
            }

            log.error("E=Unknow error, weatherSearchParameter={}", weatherSearchParameter, e);
            throw e;

        } catch (Exception e) {
            log.error("E=Unknow error, weatherSearchParameter={}", weatherSearchParameter, e);
            throw e;
        }
    }

    private WeatherDTO getWeatherFallback(WeatherSearchParameter weatherSearchParameter) {

        log.info("I=Try fallback, weatherSearchParameter={}", weatherSearchParameter);
        try {
            WeatherDTO weatherDTO = hgWeatherService.getWeather(weatherSearchParameter);
            log.info("I=Fallback OK, weatherDTO={}", weatherDTO);
            return weatherDTO;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                log.error("E=City Not Found, weatherSearchParameter={}", weatherSearchParameter, e);
                throw new WeatherCityNotFoundException("City not found", e);
            }

            if (e.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                log.error("E=City Not Found - Wrong parameter, weatherSearchParameter={}", weatherSearchParameter, e);
                throw new UnknownParameterException("City not found", e);
            }

            log.error("E=Unknow error, weatherSearchParameter={}", weatherSearchParameter, e);
            throw e;
        } catch (Exception e) {
            log.error("E=Unknow error, weatherSearchParameter={}", weatherSearchParameter, e);
            throw e;
        }
    }

    private WeatherDTO convert(Weather weather) {
        return new WeatherDTO(weather.getWeatherMain().getTemperature());
    }

    private HttpEntity<?> makeDefaultHttpAppJsonHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }

}

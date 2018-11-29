package com.lcl.msWM.service.impl;

import com.lcl.msWM.exception.WeatherBadRequestException;
import com.lcl.msWM.exception.WeatherCityNotFoundException;
import com.lcl.msWM.exception.WeatherServiceUnavailableException;
import com.lcl.msWM.model.DTO.WeatherDTO;
import com.lcl.msWM.service.intDef.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author s2it_llucca
 * @version $Revision: $<br/>
 * $Id: $
 * @since 11/29/18 10:28 AM
 */
@Service
public class WeatherServiceImpl implements WeatherService {

    private final Logger log = LoggerFactory.getLogger(WeatherServiceImpl.class);

    @Value("${weather.server.port}")
    private String weatherServerPort;

    @Value("${url.weather.base}")
    private String baseWeatherUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public WeatherDTO getWeather(final String city) {

        try {
            String uri = UriComponentsBuilder.fromHttpUrl(baseWeatherUrl + "?city={city}").port(weatherServerPort)
                    .buildAndExpand(city).toUriString().trim();

            HttpEntity<WeatherDTO> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    makeDefaultHttpAppJsonHeader(),
                    WeatherDTO.class);

            return response.getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                log.error("E=City Not Found,city={}", city, e);
                throw new WeatherCityNotFoundException("Category not found", e);
            }

            if (e.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                log.error("E=Weather error. Parameter are corret?,city={}", city, e);
                throw new WeatherBadRequestException("Music parameter error", e);
            }

            log.error("E=Unknow error, city={}", city, e);
            throw e;
        } catch (ResourceAccessException e) {
            log.error("E=Service unavailable, city={}", city, e);
            throw new WeatherServiceUnavailableException("Service unavailable", e);
        } catch (Exception e) {
            log.error("E=Unknow error, city={}", city, e);
            throw e;
        }

    }

    @Override
    public WeatherDTO getWeather(final Double lat, final Double lng) {
        try {
            String uri = "http://localhost:" + weatherServerPort + "/weather?lat=" + lat + "&lng="+lng;

            HttpEntity<WeatherDTO> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    makeDefaultHttpAppJsonHeader(),
                    WeatherDTO.class);

            return response.getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                log.error("E=City Not Found,lat={},lng={}", lat,lng, e);
                throw new WeatherCityNotFoundException("Category not found", e);
            }

            if (e.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                log.error("E=Weather error. Parameters are corret?,lat={},lng={}", lat, lng, e);
                throw new WeatherBadRequestException("Music parameter error", e);
            }

            log.error("E=Unknow error, lat={},lng={}", lat, lng, e);
            throw e;
        } catch (ResourceAccessException e) {
            log.error("E=Service unavailable, lat={},lng={}", lat, lng, e);
            throw new WeatherServiceUnavailableException("Service unavailable", e);
        } catch (Exception e) {
            log.error("E=Unknow error, lat={},lng={}", lat, lng, e);
            throw e;
        }
    }

    private HttpEntity<?> makeDefaultHttpAppJsonHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }
}

package com.lcl.msTemp.service.impl;

import com.lcl.msTemp.exception.UnknownParameterException;
import com.lcl.msTemp.exception.WeatherCityNotFoundException;
import com.lcl.msTemp.model.DTO.WeatherDTO;
import com.lcl.msTemp.model.HGWeather;
import com.lcl.msTemp.model.Weather;
import com.lcl.msTemp.model.WeatherSearchParameter;
import com.lcl.msTemp.service.intDef.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service("HGWeatherService")
public class HGWeatherServiceImpl implements WeatherService {

    private final Logger log = LoggerFactory.getLogger(WeatherServiceImpl.class);

    @Value("${hgbrasil.id}")
    private String appId;

    @Value("${hgbrasil.url}")
    private String baseUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public WeatherDTO getWeather(WeatherSearchParameter weatherSearchParameter) {

        log.info("I=Search HG with parameter", weatherSearchParameter);

        String uri;
        if(weatherSearchParameter.getCity() !=null && !weatherSearchParameter.getCity().isEmpty()){
            uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "?city_name={city}&key={appId}")
                    .buildAndExpand(weatherSearchParameter.getCity(), appId).toUriString().trim();
        }else if(weatherSearchParameter.getLat()!=null && weatherSearchParameter.getLng()!=null) {
            uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "?lat={lat}&lng={lng}&user_ip=1.1.1.1&key={appId}")
                    .buildAndExpand(weatherSearchParameter.getLat(),weatherSearchParameter.getLng(), appId).toUriString().trim();
        }else {
            throw new UnknownParameterException();
        }

        try {
            HttpEntity<HGWeather> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    makeDefaultHttpAppJsonHeader(),
                    HGWeather.class);

            System.out.println(response);

            if(response.getBody()==null){
                log.error("E=City Not Found, weatherSearchParameter={}",weatherSearchParameter);
                throw new WeatherCityNotFoundException("City not found");
            }

            return convert(response.getBody());

        }catch (HttpClientErrorException e){

            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                log.error("E=City Not Found, weatherSearchParameter={}", weatherSearchParameter, e);
                throw new WeatherCityNotFoundException("City not found", e);
            }

            if(e.getStatusCode().equals(HttpStatus.BAD_REQUEST)){
                log.error("E=City Not Found - Wrong parameter, weatherSearchParameter={}", weatherSearchParameter, e);
                throw new UnknownParameterException("City not found", e);
            }

            log.error("E=Unknow error, weatherSearchParameter={}",weatherSearchParameter, e);
            throw e;

        }catch (Exception e){
            log.error("E=Unknow error, weatherSearchParameter={}",weatherSearchParameter, e);
            throw e;
        }

    }

    private WeatherDTO convert(HGWeather weather){
        return new WeatherDTO( weather.getResults().getTemp());
    }


    private HttpEntity<?> makeDefaultHttpAppJsonHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        return new HttpEntity<>(headers);

    }




}

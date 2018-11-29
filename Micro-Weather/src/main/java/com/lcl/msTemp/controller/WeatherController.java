package com.lcl.msTemp.controller;

import com.lcl.msTemp.model.DTO.WeatherDTO;
import com.lcl.msTemp.model.WeatherSearchParameter;
import com.lcl.msTemp.service.intDef.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping(path = "/weather")
public class WeatherController {

    @Autowired
    @Qualifier("OpenWeatherService")
    private WeatherService weatherService;

    @GetMapping
    public ResponseEntity<WeatherDTO> getByCity(@RequestParam(value = "city", required = false) String city,
                                                @RequestParam(value = "lat", required = false) Double lat,
                                                @RequestParam(value = "lng", required = false) Double lng){

        WeatherDTO resp = weatherService.getWeather(new WeatherSearchParameter(city,lat,lng));
        return new ResponseEntity<>(resp, HttpStatus.OK);

    }

}

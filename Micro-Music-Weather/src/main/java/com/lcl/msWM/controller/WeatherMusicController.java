package com.lcl.msWM.controller;

import com.lcl.msWM.model.DTO.MusicDTO;
import com.lcl.msWM.model.DTO.WTracDTO;
import com.lcl.msWM.model.DTO.WeatherDTO;
import com.lcl.msWM.service.intDef.MusicService;
import com.lcl.msWM.service.intDef.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author s2it_llucca
 * @version $Revision: $<br/>
 * $Id: $
 * @since 11/29/18 10:12 AM
 */
@Controller
@RequestMapping(path = "/mw")
public class WeatherMusicController {

    private final Logger log = LoggerFactory.getLogger(WeatherMusicController.class);

    @Autowired
    private MusicService musicService;

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/byCity")
    public ResponseEntity<WTracDTO> getMusicsByCity(@RequestParam(value = "city") String city) {

        log.info("I=Search Weather by city={}", city);

        try {

            WeatherDTO weatherDTO = weatherService.getWeather(city);
            MusicDTO musicDTO = musicService.getTracksByTemperature(weatherDTO.getTemperature());
            WTracDTO wTracDTO = new WTracDTO();
            wTracDTO.setTemperature(weatherDTO.getTemperature());
            wTracDTO.setTracks(musicDTO.getTracks());
            return new ResponseEntity<>(wTracDTO, HttpStatus.OK);
        } catch (Exception e) {

            //poderia tratar o erro e ver onde deu problema e retornar um DTO mais apropriado para o usuario,
            //ex: Problema na aquisicao da temperatura -> retorna apenas uma lista de musicas (categoria rock)
            // e um atributo a mais dizendo que houve erro na pesquisa e o resultado retornado eh padrao
            throw e;
        }
    }


    @GetMapping("/byLatLng")
    public ResponseEntity<WTracDTO> getMusicsByLatLng(@RequestParam(value = "lat") Double lat, @RequestParam(value = "lng") Double lng) {

        try {
            log.info("I=Search Weather by lat={},lng={}",lat, lng);
            WeatherDTO weatherDTO = weatherService.getWeather(lat, lng);

            log.info("I=Search Musics by temperature={}",weatherDTO.getTemperature());
            MusicDTO musicDTO = musicService.getTracksByTemperature(weatherDTO.getTemperature());

            log.info("I=Building Response");
            WTracDTO wTracDTO = new WTracDTO();
            wTracDTO.setTemperature(weatherDTO.getTemperature());
            wTracDTO.setTracks(musicDTO.getTracks());
            return new ResponseEntity<>(wTracDTO, HttpStatus.OK);
        } catch (Exception e) {

            //poderia tratar o erro e ver onde deu problema e retornar um DTO mais apropriado para o usuario,
            //ex: Problema na aquisicao da temperatura -> retorna apenas uma lista de musicas (categoria rock)
            // e um atributo a mais dizendo que houve erro na pesquisa e o resultado retornado eh padrao
            throw e;
        }
    }

}

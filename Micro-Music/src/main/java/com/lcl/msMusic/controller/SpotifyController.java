package com.lcl.msMusic.controller;

import com.lcl.msMusic.model.DTO.TracksDTO;
import com.lcl.msMusic.service.SpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/music")
public class SpotifyController {

    @Autowired
    private SpotifyService spotifyService;

    @GetMapping("/byCategory")
    public ResponseEntity<TracksDTO> getMusicsByCategory(@RequestParam(value = "category") String category){
        TracksDTO trackEntityList = spotifyService.getTracksByCategory(category);

        return new ResponseEntity<>(trackEntityList, HttpStatus.OK);
    }
}

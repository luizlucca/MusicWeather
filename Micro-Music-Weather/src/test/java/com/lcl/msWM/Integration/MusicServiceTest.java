package com.lcl.msWM.Integration;

import com.lcl.msWM.exception.MusicNotFoundException;
import com.lcl.msWM.model.DTO.MusicDTO;
import com.lcl.msWM.service.impl.MusicServiceImpl;
import com.lcl.msWM.service.intDef.MusicService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MusicServiceTest {

    @InjectMocks
    private final MusicService musicService = new MusicServiceImpl();

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setup() {
        ReflectionTestUtils.setField(musicService, "musicServerPort", "8082");
        ReflectionTestUtils.setField(musicService, "musicBaseUrl", "http://localhost/music");
    }

    @Test
    public void getMusicByCategorySuccess(){
        String category="pop";

        MusicDTO musicDTO =  new MusicDTO();
        musicDTO.setTracks(new ArrayList<>());

        ResponseEntity<MusicDTO> responseEntity = new ResponseEntity<>(musicDTO, HttpStatus.ACCEPTED);
        when(restTemplate.exchange(anyString(),any(HttpMethod.class),any(), Mockito.<Class<MusicDTO>>any())).thenReturn(responseEntity);

        musicService.getTracksByCategory(category);

        verify(restTemplate, times(1)).exchange(anyString(),any(HttpMethod.class),any(),Mockito.<Class<MusicDTO>>any());
    }

    @Test
    public void getMusicByTemperatureSuccess(){
        Double temperature=32.0;

        MusicDTO musicDTO =  new MusicDTO();
        musicDTO.setTracks(new ArrayList<>());

        ResponseEntity<MusicDTO> responseEntity = new ResponseEntity<>(musicDTO, HttpStatus.ACCEPTED);
        when(restTemplate.exchange(anyString(),any(HttpMethod.class),any(), Mockito.<Class<MusicDTO>>any())).thenReturn(responseEntity);

        musicService.getTracksByTemperature(temperature);

        verify(restTemplate, times(1)).exchange(anyString(),any(HttpMethod.class),any(),Mockito.<Class<MusicDTO>>any());
    }

    @Test(expected = MusicNotFoundException.class)
    public void getMusicByTemperatureNotFound(){
        Double temperature=32.0;

        MusicDTO musicDTO =  new MusicDTO();
        musicDTO.setTracks(new ArrayList<>());

        when(restTemplate.exchange(anyString(),any(HttpMethod.class),any(), Mockito.<Class<MusicDTO>>any())).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        musicService.getTracksByTemperature(temperature);

    }

}

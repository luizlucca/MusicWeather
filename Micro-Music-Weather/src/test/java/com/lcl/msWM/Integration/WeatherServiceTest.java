package com.lcl.msWM.Integration;

import com.lcl.msWM.exception.WeatherBadRequestException;
import com.lcl.msWM.exception.WeatherCityNotFoundException;
import com.lcl.msWM.model.DTO.WeatherDTO;
import com.lcl.msWM.service.impl.WeatherServiceImpl;
import com.lcl.msWM.service.intDef.WeatherService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class WeatherServiceTest {

    @InjectMocks
    private final WeatherService weatherService = new WeatherServiceImpl();

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setup() {
        ReflectionTestUtils.setField(weatherService, "weatherServerPort", "8081");
        ReflectionTestUtils.setField(weatherService, "baseWeatherUrl", "http://localhost/weather");
    }

    @Test
    public void getWeatherByCitySuccess(){
        String city="Sao Carlos";

        WeatherDTO weatherDTO =  new WeatherDTO();
        weatherDTO.setTemperature(32.0);

        ResponseEntity<WeatherDTO> responseEntity = new ResponseEntity<>(weatherDTO, HttpStatus.ACCEPTED);
        when(restTemplate.exchange(anyString(),any(HttpMethod.class),any(),Mockito.<Class<WeatherDTO>>any())).thenReturn(responseEntity);

        weatherService.getWeather(city);

        verify(restTemplate, times(1)).exchange(anyString(),any(HttpMethod.class),any(),Mockito.<Class<WeatherDTO>>any());
    }

    @Test(expected = WeatherCityNotFoundException.class)
    public void getWeatherByCityNotFound(){

        String city="Sao Carlos";

        WeatherDTO weatherDTO =  new WeatherDTO();
        weatherDTO.setTemperature(32.0);

        when(restTemplate.exchange(anyString(),any(HttpMethod.class),any(),Mockito.<Class<WeatherDTO>>any())).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        weatherService.getWeather(city);
    }


    @Test(expected = WeatherBadRequestException.class)
    public void getWeatherByUnknowCity(){

        String city="asasadd$$dfs65Carlos";

        WeatherDTO weatherDTO =  new WeatherDTO();
        weatherDTO.setTemperature(32.0);

        when(restTemplate.exchange(anyString(),any(HttpMethod.class),any(),Mockito.<Class<WeatherDTO>>any())).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        weatherService.getWeather(city);
    }


}

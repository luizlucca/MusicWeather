package com.lcl.msTemp.service.intDef;

import com.lcl.msTemp.model.DTO.WeatherDTO;
import com.lcl.msTemp.model.WeatherSearchParameter;

public interface WeatherService {
    WeatherDTO getWeather(WeatherSearchParameter weatherSearchParameter);
}

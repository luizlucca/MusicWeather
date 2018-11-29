package com.lcl.msWM.service.intDef;

import com.lcl.msWM.model.DTO.WeatherDTO;

/**
 * @author s2it_llucca
 * @version $Revision: $<br/>
 * $Id: $
 * @since 11/29/18 10:26 AM
 */
public interface WeatherService {
    WeatherDTO getWeather(String city);
    WeatherDTO getWeather(Double lat, Double lng);
}

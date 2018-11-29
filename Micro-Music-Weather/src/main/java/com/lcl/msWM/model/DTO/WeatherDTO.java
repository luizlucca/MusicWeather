package com.lcl.msWM.model.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author s2it_llucca
 * @version $Revision: $<br/>
 * $Id: $
 * @since 11/29/18 10:19 AM
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDTO {
    private Double temperature;

    public WeatherDTO() {
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(final Double temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder()//
                .append("WeatherDTO [")//
                .append("temperature=")//
                .append(temperature)//
                .append("]");
        return builder.toString();
    }
}

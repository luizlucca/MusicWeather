package com.lcl.msTemp.model.DTO;

public class WeatherDTO {

    private Double temperature;

    public WeatherDTO(Double temperature) {
        this.temperature = temperature;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }


}

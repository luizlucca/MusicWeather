package com.lcl.msTemp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {

    @JsonProperty("main")
    private WeatherMain weatherMain;

    private  Coordinate coord;

    public Weather() {
    }

    public WeatherMain getWeatherMain() {
        return weatherMain;
    }

    public void setWeatherMain(WeatherMain weatherMain) {
        this.weatherMain = weatherMain;
    }

    public Coordinate getCoord() {
        return coord;
    }

    public void setCoord(Coordinate coord) {
        this.coord = coord;
    }

    //*****************************************
    //**** Inner class ************************
    //*****************************************
    public class Coordinate implements Serializable {
        private Double lat;
        private Double lon;

        public Coordinate() {
        }

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLon() {
            return lon;
        }

        public void setLon(Double lon) {
            this.lon = lon;
        }

        @Override
        public String toString() {
            return "Coordinate{" +
                    "lat=" + lat +
                    ", lon=" + lon +
                    '}';
        }
    }


    public class WeatherMain implements Serializable {
        @JsonProperty("temp")
        private Double temperature;

        @JsonProperty("temp_min")
        private Double tempMin;

        @JsonProperty("temp_max")
        private Double tempMax;

        private Double pressure;
        private Double humidity;

        public WeatherMain() {
        }

        public Double getTemperature() {
            return temperature;
        }

        public void setTemperature(Double temperature) {
            this.temperature = temperature;
        }

        public Double getTempMin() {
            return tempMin;
        }

        public void setTempMin(Double tempMin) {
            this.tempMin = tempMin;
        }

        public Double getTempMax() {
            return tempMax;
        }

        public void setTempMax(Double tempMax) {
            this.tempMax = tempMax;
        }

        public Double getPressure() {
            return pressure;
        }

        public void setPressure(Double pressure) {
            this.pressure = pressure;
        }

        public Double getHumidity() {
            return humidity;
        }

        public void setHumidity(Double humidity) {
            this.humidity = humidity;
        }

        @Override
        public String toString() {
            return "WeatherMain{" +
                    "temperature=" + temperature +
                    ", tempMin=" + tempMin +
                    ", tempMax=" + tempMax +
                    ", pressure=" + pressure +
                    ", humidity=" + humidity +
                    '}';
        }
    }

}

package com.lcl.msTemp.model;

public class WeatherSearchParameter {

    private String city;
    private Double lat;
    private Double lng;

    public WeatherSearchParameter(String city, Double lat, Double lng) {
        this.city = city;
        this.lat = lat;
        this.lng = lng;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "WeatherSearchParameter{" +
                "city='" + city + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}

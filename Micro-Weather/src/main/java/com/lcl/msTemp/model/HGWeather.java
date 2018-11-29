package com.lcl.msTemp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HGWeather {

    private Results results;

    public HGWeather() {
    }

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    //*****************************************
    //**** Inner class ************************
    //*****************************************
    public class Results implements Serializable {
        private Double temp;

        public Results() {
        }

        public Double getTemp() {
            return temp;
        }

        public void setTemp(Double temp) {
            this.temp = temp;
        }

        @Override
        public String toString() {
            return "Results{" +
                    "temp=" + temp +
                    '}';
        }
    }
}

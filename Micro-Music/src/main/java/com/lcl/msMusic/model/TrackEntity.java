package com.lcl.msMusic.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackEntity {
    private String name;

    public TrackEntity() {
    }

    public TrackEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TrackModel{" +
                "name='" + name + '\'' +
                '}';
    }
}

package com.lcl.msWM.model.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author s2it_llucca
 * @version $Revision: $<br/>
 * $Id: $
 * @since 11/29/18 12:54 PM
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WTracDTO {
    private List<MusicDTO.TrackEntity> tracks;
    private Double temperature;

    public WTracDTO() {
    }

    public List<MusicDTO.TrackEntity> getTracks() {
        return tracks;
    }

    public void setTracks(final List<MusicDTO.TrackEntity> tracks) {
        this.tracks = tracks;
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
                .append("WTracDTO [")//
                .append("tracks=")//
                .append(tracks)//
                .append(",temperature=")//
                .append(temperature)//
                .append("]");
        return builder.toString();
    }
}

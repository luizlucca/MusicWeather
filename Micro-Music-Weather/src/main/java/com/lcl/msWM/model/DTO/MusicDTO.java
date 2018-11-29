package com.lcl.msWM.model.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author s2it_llucca
 * @version $Revision: $<br/>
 * $Id: $
 * @since 11/29/18 10:17 AM
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MusicDTO implements Serializable {

    private List<TrackEntity> tracks;

    public MusicDTO() {
        tracks = new ArrayList<>();
    }

    public MusicDTO(final List<TrackEntity> tracks) {
        this.tracks = tracks;
    }

    public List<TrackEntity> getTracks() {
        return tracks;
    }

    public void setTracks(final List<TrackEntity> tracks) {
        this.tracks = tracks;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder()//
                .append("MusicDTO [")//
                .append("tracks=")//
                .append(tracks)//
                .append("]");
        return builder.toString();
    }

    //*******************************
    //**** InnerClass ***************
    //*******************************
    public static class TrackEntity implements Serializable {

        private String name;

        public TrackEntity() {
        }

        public TrackEntity(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder()//
                    .append("TrackEntity [")//
                    .append("name=\"")//
                    .append(name).append("\"")//
                    .append("]");
            return builder.toString();
        }
    }

}

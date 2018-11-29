package com.lcl.msMusic.model.DTO;

import java.util.List;

import com.lcl.msMusic.model.TrackEntity;

/**
 * @author s2it_llucca
 * @version $Revision: $<br/>
 * $Id: $
 * @since 11/29/18 11:34 AM
 */
public class TracksDTO {
       private List<TrackEntity> tracks;

    public TracksDTO() {
    }

    public TracksDTO(final List<TrackEntity> tracks) {
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
                .append("TracksDTO [")//
                .append("tracks=")//
                .append(tracks)//
                .append("]");
        return builder.toString();
    }
}

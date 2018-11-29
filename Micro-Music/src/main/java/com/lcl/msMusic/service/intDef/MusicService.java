package com.lcl.msMusic.service.intDef;

import com.lcl.msMusic.model.DTO.TracksDTO;

/**
 * @author s2it_llucca
 * @version $Revision: $<br/>
 * $Id: $
 * @since 11/29/18 9:37 AM
 */
public interface MusicService {
    TracksDTO getTracksByCategory(String category);
}

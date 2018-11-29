package com.lcl.msWM.service.intDef;

import com.lcl.msWM.model.DTO.MusicDTO;

/**
 * @author s2it_llucca
 * @version $Revision: $<br/>
 * $Id: $
 * @since 11/29/18 10:27 AM
 */
public interface MusicService {
    MusicDTO getTracksByCategory(String category);
    MusicDTO getTracksByTemperature(Double temperature);
}

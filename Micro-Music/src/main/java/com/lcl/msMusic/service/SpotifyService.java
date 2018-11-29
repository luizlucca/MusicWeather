package com.lcl.msMusic.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.lcl.msMusic.exception.SpotifyCategorySearchException;
import com.lcl.msMusic.exception.SpotifyNotAvailableException;
import com.lcl.msMusic.model.DTO.TracksDTO;
import com.lcl.msMusic.model.TrackEntity;
import com.lcl.msMusic.service.intDef.MusicService;
import com.neovisionaries.i18n.CountryCode;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.requests.data.browse.GetCategorysPlaylistsRequest;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpotifyService implements MusicService {

    private final Logger log = LoggerFactory.getLogger(SpotifyService.class);

    @Autowired
    private SpotifyApi spotifyApi;

    @HystrixCommand(fallbackMethod = "trackByCategoryCallback", ignoreExceptions = {
            SpotifyNotAvailableException.class })
    public TracksDTO getTracksByCategory(String category) {
        log.info("I=Iniciando consulta por categoria no spotify");

        final GetCategorysPlaylistsRequest getCategoryRequest = spotifyApi.getCategorysPlaylists(category)
                .country(CountryCode.BR)
                .limit(2)
                .offset(0)
                .build();

        try {
            final Paging<PlaylistSimplified> playlistSimplifiedPaging = getCategoryRequest.execute();
            PlaylistSimplified[] ret = playlistSimplifiedPaging.getItems();
            List<TrackEntity> trackList = new ArrayList<>();

            for (PlaylistSimplified playlistSimplified : ret) {
                String playlistId = playlistSimplified.getId();
                //isso poderia ser feito em paralelo!
                GetPlaylistRequest getPlaylistRequest = spotifyApi.getPlaylist(playlistId)
                        .market(CountryCode.BR)
                        .build();

                final Playlist playlist = getPlaylistRequest.execute();

                PlaylistTrack[] playlistTrack = playlist.getTracks().getItems();
                for (PlaylistTrack playlistTrack1 : playlistTrack) {
                    trackList.add(new TrackEntity(playlistTrack1.getTrack().getName()));
                }
            }
            log.info("I=Finalizada consulta por categoria no spotify com sucesso, total de musicas={}",
                    playlistSimplifiedPaging.getTotal());
            return new TracksDTO(trackList);
        } catch (IOException | SpotifyWebApiException e) {
            log.error("E=Falha ao conectar com spotify para buscar categoria");
            throw new SpotifyCategorySearchException("Falha ao conectar com spotify para buscar categoria", e);
        } catch (Exception e) {
            log.error("E=Falha ao conectar com spotify", e);
            throw e;
        }
    }

    public List<TrackEntity> trackByCategoryCallback(String category) {
        throw new SpotifyNotAvailableException("Unavailable Service! Try to read a book or enjoy talking to friends");
    }

}
